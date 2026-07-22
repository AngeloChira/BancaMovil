package com.gastulochiracoronadoluciano.bancamovil.domain.modelos;

import com.gastulochiracoronadoluciano.bancamovil.domain.singleton.Logger;

// Implementacion moderna nativa para Email
public class EmailNotificacion implements Notificacion {

    @Override
    public void enviar(String mensaje) {
        System.out.println("---[Sistema Email]: Conectando a servidor SMTP---");
        System.out.println("Enviando correo: " + mensaje);

        try {
            Logger.getInstance().registrar("Email enviado: " + mensaje);
        } catch (Exception ignored) {}
    }
}
