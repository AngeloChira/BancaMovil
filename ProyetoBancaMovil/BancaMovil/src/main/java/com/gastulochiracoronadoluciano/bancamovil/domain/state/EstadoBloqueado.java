package com.gastulochiracoronadoluciano.bancamovil.domain.state;

public class EstadoBloqueado implements EstadoSistema {

    @Override
    public void operar() {
        System.out.println("\n=== ESTADO: BLOQUEADO ===");
        System.out.println("Sistema completamente bloqueado.");
    }
}
