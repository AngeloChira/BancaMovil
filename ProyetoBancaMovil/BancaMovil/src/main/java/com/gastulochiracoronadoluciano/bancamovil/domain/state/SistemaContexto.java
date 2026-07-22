package com.gastulochiracoronadoluciano.bancamovil.domain.state;

public class SistemaContexto {

    private EstadoSistema estado;

    public SistemaContexto(EstadoSistema estado) {
        this.estado = estado;
    }

    public void cambiarEstado(EstadoSistema estado) {
        this.estado = estado;
    }

    public boolean puedeOperar() {
        return estado instanceof EstadoActivo;
    }

    public void ejecutar() {
        estado.operar();
    }
}
