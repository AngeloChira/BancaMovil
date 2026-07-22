package com.gastulochiracoronadoluciano.bancamovil.domain.command;

/**
 * Interfaz Command: define la operación ejecutar y deshacer
 */
public interface Command {
    void ejecutar();
    void deshacer();
}
