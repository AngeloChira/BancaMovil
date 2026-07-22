package com.gastulochiracoronadoluciano.bancamovil.domain.observer;

public class PanelObserver implements Observer {

    @Override
    public void actualizar(String mensaje) {
        System.out.println("\n========== PANEL ==========");
        System.out.println("Nueva notificación:");
        System.out.println(mensaje);
    }
}
