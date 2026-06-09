package modelos;

import singleton.Logger;

// Implementacion moderna nativa para WhatsApp
public class WhatsNotificacion implements Notificacion{

    @Override

    public void enviar(String mensaje) {

        System.out.println("---[Sistema WhatsApp]: Conectando al servidor WhatsApp---");
        System.out.println("Enviando mensaje WhatsApp: "+mensaje);

        Logger.getInstance().registrar(
                "WhatsApp enviado: " + mensaje
        );
    }
}
