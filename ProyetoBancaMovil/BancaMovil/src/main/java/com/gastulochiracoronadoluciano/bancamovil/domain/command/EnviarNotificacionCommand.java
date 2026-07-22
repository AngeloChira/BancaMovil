package com.gastulochiracoronadoluciano.bancamovil.domain.command;

import com.gastulochiracoronadoluciano.bancamovil.domain.builder.Alerta;
import com.gastulochiracoronadoluciano.bancamovil.domain.modelos.Notificacion;
import com.gastulochiracoronadoluciano.bancamovil.domain.singleton.Logger;

/**
 * Command concreto que encapsula el envío de una notificación
 */
public class EnviarNotificacionCommand implements Command {

    private final Notificacion notificacion;
    private final Alerta alerta;

    public EnviarNotificacionCommand(Notificacion notificacion, Alerta alerta) {
        this.notificacion = notificacion;
        this.alerta = alerta;
    }

    @Override
    public void ejecutar() {
        // Ejecuta el envío usando la interfaz Notificacion
        notificacion.enviar(alerta.toString());

        // Registrar en el logger singleton
        try {
            Logger.getInstance().registrar("Envío ejecutado: " + alerta.getTitulo());
        } catch (Exception ignored) {}
    }

    @Override
    public void deshacer() {
        System.out.println("\n=== DESHACIENDO ÚLTIMO ENVÍO ===");
        try {
            Logger.getInstance().registrar("Último envío cancelado: " + alerta.getTitulo());
        } catch (Exception ignored) {}
    }
}
