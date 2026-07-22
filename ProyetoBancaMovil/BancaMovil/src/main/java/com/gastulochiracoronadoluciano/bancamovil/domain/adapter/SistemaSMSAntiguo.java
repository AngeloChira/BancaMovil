package com.gastulochiracoronadoluciano.bancamovil.domain.adapter;

// (Adaptee) La clase antigua que NO implementa la interfaz Notificacion
public class SistemaSMSAntiguo {

    // Tiene su propio metodo con un nombre distinto
    public void mandarTexto(String texto) {
        System.out.println("[Sistema Antiguo SMS]");
        System.out.println("Mensaje enviado: " + texto);
    }
}
