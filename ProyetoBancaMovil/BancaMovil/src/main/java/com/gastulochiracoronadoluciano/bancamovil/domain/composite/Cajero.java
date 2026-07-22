package com.gastulochiracoronadoluciano.bancamovil.domain.composite;

// (Leaf) El nodo final que no tiene hijos, realiza la accion directamente
public class Cajero implements ComponenteBanco {

    private String nombre;

    public Cajero(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void recibirAlerta(String mensaje) {
        System.out.println("\n[" + nombre + "] recibió alerta:");
        System.out.println(mensaje);
    }
}
