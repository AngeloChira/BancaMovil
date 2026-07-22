package com.gastulochiracoronadoluciano.bancamovil.domain.factory;

import com.gastulochiracoronadoluciano.bancamovil.domain.modelos.EmailNotificacion;
import com.gastulochiracoronadoluciano.bancamovil.domain.modelos.Notificacion;

// (Concrete Creator) Fabrica especifica para crear notificaciones por Email
public class EmailFactory extends NotificacionFactory {
    @Override
    public Notificacion crearNotificacion() {
        return new EmailNotificacion();
    }
}
