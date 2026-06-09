package modelos;

import singleton.Logger;

// Implementacion moderna nativa para Email
public class EmailNotificacion implements Notificacion {

    @Override
    public void enviar(String mensaje) {

        System.out.println("---[Sistema Email]: Conectando a servidor SMTP---");
        System.out.println("Enviando correo: "+mensaje);

        Logger.getInstance().registrar(
                "Email enviado: " + mensaje
        );

    }

}


