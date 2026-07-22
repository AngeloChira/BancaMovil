package com.gastulochiracoronadoluciano.bancamovil.domain.singleton;

import com.gastulochiracoronadoluciano.bancamovil.domain.observer.Observer;
import com.gastulochiracoronadoluciano.bancamovil.domain.observer.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Logger como Singleton y Subject (observer pattern)
 */
public class Logger implements Subject {

    private static Logger instancia;

    private final List<String> historial;
    private final List<Observer> observers;

    private Logger() {
        historial = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static synchronized Logger getInstance() {
        if (instancia == null) {
            instancia = new Logger();
        }
        return instancia;
    }

    public void registrar(String mensaje) {
        historial.add(mensaje);
        notificarObservers(mensaje);
    }

    public void mostrarHistorial() {
        System.out.println("\n=== HISTORIAL ===");
        for (String evento : historial) {
            System.out.println(evento);
        }
    }

    // Método para exponer el historial (útil para el endpoint /api/v1/logs)
    public List<String> getHistorial() {
        return Collections.unmodifiableList(historial);
    }

    @Override
    public void agregarObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void eliminarObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notificarObservers(String mensaje) {
        for (Observer observer : observers) {
            observer.actualizar(mensaje);
        }
    }
}
