package singleton;

import observer.Observer;
import observer.Subject;

import java.util.ArrayList;
import java.util.List;

// Ahora Logger también es un Subject
public class Logger implements Subject {

    // Instancia única (Singleton)
    private static Logger instancia;

    // Historial de eventos
    private List<String> historial;

    // Lista de observadores
    private List<Observer> observers;

    // Constructor privado
    private Logger() {

        historial = new ArrayList<>();
        observers = new ArrayList<>();

    }

    // Devuelve la única instancia
    public static Logger getInstance() {

        if (instancia == null) {
            instancia = new Logger();
        }

        return instancia;
    }

    // Registra un evento y notifica a todos los observadores
    public void registrar(String mensaje) {

        historial.add(mensaje);

        notificarObservers(mensaje);

    }

    // Muestra el historial completo
    public void mostrarHistorial() {

        System.out.println("\n=== HISTORIAL ===");

        for (String evento : historial) {
            System.out.println(evento);
        }

    }

    // ==========================
    // MÉTODOS DEL OBSERVER
    // ==========================

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