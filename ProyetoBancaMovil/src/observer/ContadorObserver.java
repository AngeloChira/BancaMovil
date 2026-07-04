package observer;

public class ContadorObserver implements Observer {

    private int contador = 0;

    @Override
    public void actualizar(String mensaje) {

        contador++;

        System.out.println(
                "Cantidad de eventos recibidos: "
                        + contador
        );

    }

}