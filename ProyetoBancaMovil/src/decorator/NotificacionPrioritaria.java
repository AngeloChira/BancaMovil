package decorator;

import modelos.Notificacion;

// (Concrete Decorator) Agrega un aviso de prioridad alta antes de enviar
public class NotificacionPrioritaria extends NotificacionDecorator {

    public NotificacionPrioritaria(Notificacion notificacion) {
        super(notificacion);
    }

    @Override
    public void enviar(String mensaje) {

        System.out.println("=== PRIORIDAD ALTA ===");

        notificacion.enviar(mensaje);
    }
}

