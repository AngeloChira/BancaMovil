package composite;

import java.util.ArrayList;
import java.util.List;

// (Composite Nivel Superior) Nodo raiz que contiene Agencias
public class Banco implements ComponenteBanco {

    private String nombre;

    private List<ComponenteBanco> agencias;

    public Banco(String nombre) {

        this.nombre = nombre;
        agencias = new ArrayList<>();
    }

    public void agregar(
            ComponenteBanco componente) {

        agencias.add(componente);
    }

    @Override
    public void recibirAlerta(String mensaje) {

        System.out.println(
                "\n=== BANCO " + nombre + " ==="
        );
        // Propaga la alerta a todas las agencias
        for (ComponenteBanco c : agencias) {
            c.recibirAlerta(mensaje);
        }
    }
}

