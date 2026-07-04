package state;

public class EstadoActivo implements EstadoSistema {

    @Override
    public void operar() {
        System.out.println("\n=== ESTADO: ACTIVO ===");
        System.out.println("Sistema operativo.");
    }
}