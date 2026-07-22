package com.gastulochiracoronadoluciano.bancamovil.domain.strategy;

public class ContextoEnvio {

    private EstrategiaEnvio estrategia;

    public ContextoEnvio(EstrategiaEnvio estrategia) {
        this.estrategia = estrategia;
    }

    public void setEstrategia(EstrategiaEnvio estrategia) {
        this.estrategia = estrategia;
    }

    public void ejecutar(String mensaje) {
        estrategia.ejecutar(mensaje);
    }
}
