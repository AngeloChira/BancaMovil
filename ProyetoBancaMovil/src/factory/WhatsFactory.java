package factory;

import modelos.Notificacion;
import modelos.WhatsNotificacion;

// (Concrete Creator) Fabrica especifica para crear notificaciones por WhatsApp
public class WhatsFactory extends NotificacionFactory{
    @Override
    public Notificacion crearNotificacion() {
        return new WhatsNotificacion();
    }
}
