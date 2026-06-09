package singleton;

import java.util.ArrayList;
import java.util.List;

// Garantiza que solo exista una instancia del Logger en todo el programa

public class Logger {

    // Instancia unica estatica
    private static Logger instancia;

    // Constructor privado para evitar que se creen mas instancias con "new"
    private List<String> historial;

    // Metodo global para obtener la unica instancia
    private Logger() {
        historial = new ArrayList<>();
    }

    public static Logger getInstance() {

        if (instancia == null) {
            instancia = new Logger();
        }

        return instancia;
    }

    public void registrar(String mensaje) {
        historial.add(mensaje);
    }

    public void mostrarHistorial() {

        System.out.println("\n=== HISTORIAL ===");

        for (String evento : historial) {
            System.out.println(evento);
        }
    }
}

