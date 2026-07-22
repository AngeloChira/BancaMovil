package com.gastulochiracoronadoluciano.bancamovil.service;


// Añade estos imports en la parte superior de AlertService.java
import com.gastulochiracoronadoluciano.bancamovil.domain.builder.Alerta;
import com.gastulochiracoronadoluciano.bancamovil.domain.builder.AlertaConcreteBuilder;
import com.gastulochiracoronadoluciano.bancamovil.domain.builder.Director;
import com.gastulochiracoronadoluciano.bancamovil.domain.factory.EmailFactory;
import com.gastulochiracoronadoluciano.bancamovil.domain.factory.NotificacionFactory;
import com.gastulochiracoronadoluciano.bancamovil.domain.factory.SMSfactory;
import com.gastulochiracoronadoluciano.bancamovil.domain.factory.WhatsFactory;
import com.gastulochiracoronadoluciano.bancamovil.domain.decorator.NotificacionCifrada;
import com.gastulochiracoronadoluciano.bancamovil.domain.decorator.NotificacionPrioritaria;
import com.gastulochiracoronadoluciano.bancamovil.domain.decorator.NotificacionUrgente;
import com.gastulochiracoronadoluciano.bancamovil.domain.modelos.Notificacion;
import com.gastulochiracoronadoluciano.bancamovil.domain.command.EnviarNotificacionCommand;
import com.gastulochiracoronadoluciano.bancamovil.domain.command.Invoker;
import com.gastulochiracoronadoluciano.bancamovil.domain.singleton.Logger;
import com.gastulochiracoronadoluciano.bancamovil.domain.state.SistemaContexto;
import com.gastulochiracoronadoluciano.bancamovil.domain.state.EstadoActivo;
import com.gastulochiracoronadoluciano.bancamovil.domain.strategy.ContextoEnvio;
import com.gastulochiracoronadoluciano.bancamovil.domain.strategy.EnvioNormal;
import com.gastulochiracoronadoluciano.bancamovil.domain.strategy.EnvioPrioritario;
import com.gastulochiracoronadoluciano.bancamovil.domain.strategy.EnvioSeguro;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;



import com.gastulochiracoronadoluciano.bancamovil.dto.AlertaDTO;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AlertService {

    private final Map<Integer, AlertaDTO> store = new ConcurrentHashMap<>();
    private final AtomicInteger seq = new AtomicInteger(1);

    public List<AlertaDTO> listar() {
        List<AlertaDTO> list = new ArrayList<>(store.values());
        list.sort(Comparator.comparing(a -> a.id));
        return list;
    }

    public AlertaDTO crear(AlertaDTO dto) {
        int id = seq.getAndIncrement();
        dto.id = id;
        dto.fecha = Instant.now().toString();
        dto.estadoEnvio = "PENDING";
        store.put(id, dto);
        return dto;
    }

    public AlertaDTO obtener(Integer id) {
        AlertaDTO dto = store.get(id);
        if (dto == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerta no encontrada");
        return dto;
    }

    public void eliminar(Integer id) {
        store.remove(id);
    }

    public String enviar(Integer id, Map<String,Object> payload) {
        AlertaDTO dto = obtener(id);

        // 1. Verificar estado del sistema (State)
        // Puedes mantener un SistemaContexto global como bean si lo prefieres.
        SistemaContexto contexto = new SistemaContexto(new EstadoActivo()); // por defecto activo
        if (payload.containsKey("estadoSistema")) {
            String estadoReq = String.valueOf(payload.get("estadoSistema"));
            switch (estadoReq.toLowerCase()) {
                case "mantenimiento":
                    contexto.cambiarEstado(new com.gastulochiracoronadoluciano.bancamovil.domain.state.EstadoMantenimiento());
                    break;
                case "bloqueado":
                    contexto.cambiarEstado(new com.gastulochiracoronadoluciano.bancamovil.domain.state.EstadoBloqueado());
                    break;
                default:
                    contexto.cambiarEstado(new EstadoActivo());
            }
        }

        if (!contexto.puedeOperar()) {
            // No permitir envíos si el sistema no está activo
            Logger.getInstance().registrar("Intento de envío bloqueado por estado del sistema. Alerta id=" + id);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Sistema no disponible para envíos");
        }

        try {
            // 2. Construir Alerta con Builder (si quieres usar datos del DTO)
            AlertaConcreteBuilder builder = new AlertaConcreteBuilder();
            Director director = new Director();
            // Si payload indica "usarPlantillaSeguridad" o similar, usar director
            boolean usarPlantilla = payload.getOrDefault("usarPlantillaSeguridad", false).equals(Boolean.TRUE);
            Alerta alertaDomain;
            if (usarPlantilla) {
                alertaDomain = director.construirAlertaSeguridad(builder);
            } else {
                // Construir manualmente desde DTO
                builder.setTitulo(dto.titulo);
                builder.setContenido(dto.contenido);
                builder.setPrioridad(dto.prioridad);
                builder.setDestinatario(dto.destinatario);
                alertaDomain = builder.build();
            }

            // 3. Seleccionar canal con Factory (Factory + Adapter)
            String canal = String.valueOf(payload.getOrDefault("canal", "email")).toLowerCase();
            NotificacionFactory factory;
            switch (canal) {
                case "sms":
                    factory = new SMSfactory();
                    break;
                case "whats":
                case "whatsapp":
                    factory = new WhatsFactory();
                    break;
                default:
                    factory = new EmailFactory();
            }
            Notificacion notificacion = factory.crearNotificacion();

            // 4. Aplicar decoradores si vienen en payload (Decorator)
            Object decObj = payload.get("decorators");
            if (decObj instanceof List) {
                @SuppressWarnings("unchecked")
                List<String> decorators = (List<String>) decObj;
                for (String d : decorators) {
                    String key = d.toLowerCase();
                    if (key.equals("urgente")) {
                        notificacion = new NotificacionUrgente(notificacion);
                    } else if (key.equals("prioritaria") || key.equals("prioridad")) {
                        notificacion = new NotificacionPrioritaria(notificacion);
                    } else if (key.equals("cifrado")) {
                        notificacion = new NotificacionCifrada(notificacion);
                    }
                }
            }

            // 5. Ejecutar estrategia previa si se solicita (Strategy)
            String estrategia = String.valueOf(payload.getOrDefault("estrategia", "normal")).toLowerCase();
            ContextoEnvio contextoEnvio;
            switch (estrategia) {
                case "seguro":
                    contextoEnvio = new ContextoEnvio(new EnvioSeguro());
                    break;
                case "prioritario":
                    contextoEnvio = new ContextoEnvio(new EnvioPrioritario());
                    break;
                default:
                    contextoEnvio = new ContextoEnvio(new EnvioNormal());
            }
            // Ejecuta la estrategia (puede realizar verificaciones o logs)
            contextoEnvio.ejecutar(alertaDomain.toString());

            // 6. Ejecutar envío con Command + Invoker
            EnviarNotificacionCommand comando = new EnviarNotificacionCommand(notificacion, alertaDomain);
            Invoker invoker = new Invoker();
            invoker.ejecutar(comando);

            // 7. Registrar en Logger (Singleton + Observer)
            Logger.getInstance().registrar("Alerta enviada id=" + id + " via " + canal.toUpperCase());

            // 8. Actualizar DTO y devolver OK
            dto.estadoEnvio = "SENT";
            return "OK";

        } catch (Exception ex) {
            // Registrar fallo y marcar DTO
            try {
                Logger.getInstance().registrar("Error enviando alerta id=" + id + " : " + ex.getMessage());
            } catch (Exception ignored) {}
            dto.estadoEnvio = "FAILED";
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al enviar alerta");
        }
    }
}

