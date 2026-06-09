package modelos;

import singleton.Logger;

public class SMSnotificacion implements Notificacion{

    @Override

    public void enviar(String mensaje) {

        System.out.println("---[Sistema SMS]: Conectando a telefonica---");
        System.out.println("Enviando mensaje de texto: "+mensaje);

        Logger.getInstance().registrar(
                "SMS enviado: " + mensaje
        );

    }
}
