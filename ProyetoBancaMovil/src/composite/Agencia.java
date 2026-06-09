package composite;

import java.util.ArrayList;
import java.util.List;

// (Composite) Un nodo que contiene hijos (Cajeros u otros componentes)
public class Agencia implements ComponenteBanco {

    private String nombre;

    private List<ComponenteBanco> componentes;

    public Agencia(String nombre) {

        this.nombre = nombre;
        componentes = new ArrayList<>();
    }

    // Metodo para agregar hijos a la agencia
    public void agregar(
            ComponenteBanco componente) {

        componentes.add(componente);
    }

    @Override
    public void recibirAlerta(String mensaje) {

        System.out.println(
                "\n=== Agencia " + nombre + " ==="
        );

        for (ComponenteBanco c : componentes) {
            c.recibirAlerta(mensaje);
        }
    }
}
