package com.gastulochiracoronadoluciano.bancamovil.domain.factory;

import com.gastulochiracoronadoluciano.bancamovil.domain.modelos.Notificacion;
import com.gastulochiracoronadoluciano.bancamovil.domain.modelos.WhatsNotificacion;

// (Concrete Creator) Fabrica especifica para crear notificaciones por WhatsApp
public class WhatsFactory extends NotificacionFactory {
    @Override
    public Notificacion crearNotificacion() {
        return new WhatsNotificacion();
    }
}
