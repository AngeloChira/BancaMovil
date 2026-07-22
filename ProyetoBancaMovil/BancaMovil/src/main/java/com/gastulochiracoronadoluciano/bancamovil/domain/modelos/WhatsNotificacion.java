package com.gastulochiracoronadoluciano.bancamovil.domain.modelos;

import com.gastulochiracoronadoluciano.bancamovil.domain.singleton.Logger;

// Implementacion moderna nativa para WhatsApp
public class WhatsNotificacion implements Notificacion {

    @Override
    public void enviar(String mensaje) {
        System.out.println("---[Sistema WhatsApp]: Conectando al servidor WhatsApp---");
        System.out.println("Enviando mensaje WhatsApp: " + mensaje);

        try {
            Logger.getInstance().registrar("WhatsApp enviado: " + mensaje);
        } catch (Exception ignored) {}
    }
}
