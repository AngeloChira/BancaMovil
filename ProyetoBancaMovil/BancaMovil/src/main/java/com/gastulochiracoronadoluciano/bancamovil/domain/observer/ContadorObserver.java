package com.gastulochiracoronadoluciano.bancamovil.domain.observer;

public class ContadorObserver implements Observer {

    private int contador = 0;

    @Override
    public void actualizar(String mensaje) {
        contador++;
        System.out.println("\n[ContadorObserver] Total de notificaciones: " + contador);
    }

    public int getContador() {
        return contador;
    }
}
