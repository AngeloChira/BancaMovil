package decorator;

import modelos.Notificacion;

public class NotificacionCifrada extends NotificacionDecorator {

    // (Concrete Decorator) Agrega el comportamiento de cifrado al mensaje
    public NotificacionCifrada(Notificacion notificacion) {
        super(notificacion);
    }

    @Override
    public void enviar(String mensaje) {

        String mensajeCifrado = "[CIFRADO] " + mensaje;

        notificacion.enviar(mensajeCifrado);
    }
}

