package com.gastulochiracoronadoluciano.bancamovil.domain.strategy;

public class EnvioPrioritario implements EstrategiaEnvio {

    @Override
    public void ejecutar(String mensaje) {
        System.out.println("\n=== ENVÍO PRIORITARIO ===");
        System.out.println("Asignando máxima prioridad.");
    }
}
