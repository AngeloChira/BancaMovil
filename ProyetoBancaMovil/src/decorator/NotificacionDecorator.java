package decorator;

import modelos.Notificacion;

// (Base Decorator) Clase abstracta que envuelve una notificacion existente
public abstract class NotificacionDecorator implements Notificacion {

    protected Notificacion notificacion;

    // Recibe el objeto que va a decorar
    public NotificacionDecorator(Notificacion notificacion) {
        this.notificacion = notificacion;
    }
}
