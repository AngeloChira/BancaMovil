package decorator;

import modelos.Notificacion;

// (Concrete Decorator) Agrega un aviso de Urgencia Extrema
public class NotificacionUrgente extends NotificacionDecorator {

    public NotificacionUrgente(Notificacion notificacion) {
        super(notificacion);
    }

    @Override
    public void enviar(String mensaje) {

        System.out.println("=== MENSAJE URGENTE ===");

        notificacion.enviar(mensaje);
    }
}

