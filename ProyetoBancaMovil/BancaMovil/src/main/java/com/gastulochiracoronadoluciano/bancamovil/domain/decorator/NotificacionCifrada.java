package com.gastulochiracoronadoluciano.bancamovil.domain.decorator;

import com.gastulochiracoronadoluciano.bancamovil.domain.modelos.Notificacion;

// (Concrete Decorator) Agrega el comportamiento de cifrado al mensaje
public class NotificacionCifrada extends NotificacionDecorator {

    public NotificacionCifrada(Notificacion notificacion) {
        super(notificacion);
    }

    @Override
    public void enviar(String mensaje) {
        String mensajeCifrado = "[CIFRADO] " + mensaje;
        notificacion.enviar(mensajeCifrado);
    }
}
