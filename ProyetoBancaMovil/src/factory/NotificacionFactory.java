package factory;

import modelos.Notificacion;

// (Creator) Clase abstracta que define el metodo de creacion
public abstract class NotificacionFactory {

    // Metodo que las subclases deben implementar para crear el objeto
    public abstract Notificacion crearNotificacion();
}
