package factory;

import adapter.SMSAdapter;
import modelos.Notificacion;

// (Concrete Creator) Fabrica especifica para SMS.
// Aqui integramos el Adapter para devolver el sistema antiguo disfrazado.
public class SMSfactory extends NotificacionFactory {

    @Override
    public Notificacion crearNotificacion() {
        // Devolvemos el Adapter en lugar de una clase SMS moderna
        return new SMSAdapter();
    }
}