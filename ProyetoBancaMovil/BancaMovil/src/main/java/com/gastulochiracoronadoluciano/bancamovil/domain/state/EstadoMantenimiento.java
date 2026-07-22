package com.gastulochiracoronadoluciano.bancamovil.domain.state;

public class EstadoMantenimiento implements EstadoSistema {

    @Override
    public void operar() {
        System.out.println("\n=== ESTADO: MANTENIMIENTO ===");
        System.out.println("Sistema en mantenimiento. Operaciones limitadas.");
    }
}