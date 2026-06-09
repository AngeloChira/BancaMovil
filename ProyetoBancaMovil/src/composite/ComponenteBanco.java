package composite;

// (Component) Interfaz comun tanto para nodos individuales (Cajero) como grupos (Agencia/Banco)
public interface ComponenteBanco {

    void recibirAlerta(String mensaje);
}
