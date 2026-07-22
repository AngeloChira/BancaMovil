package com.gastulochiracoronadoluciano.bancamovil.domain.strategy;

public class EnvioNormal implements EstrategiaEnvio {

    @Override
    public void ejecutar(String mensaje) {
        System.out.println("\n=== ENVÍO NORMAL ===");
        System.out.println("Mensaje preparado.");
    }
}
