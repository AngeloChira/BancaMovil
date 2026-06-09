package factory;

import modelos.EmailNotificacion;
import modelos.Notificacion;

// (Concrete Creator) Fabrica especifica para crear notificaciones por Email
public class EmailFactory extends NotificacionFactory{
    @Override
    public Notificacion crearNotificacion() {
        return new EmailNotificacion();
    }
}
