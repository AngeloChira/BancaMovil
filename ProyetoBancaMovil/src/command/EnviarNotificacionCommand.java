package command;

import builder.Alerta;
import modelos.Notificacion;
import singleton.Logger;

public class EnviarNotificacionCommand implements Command {

    private Notificacion notificacion;

    private Alerta alerta;

    public EnviarNotificacionCommand(
            Notificacion notificacion,
            Alerta alerta) {

        this.notificacion = notificacion;
        this.alerta = alerta;

    }

    @Override
    public void ejecutar() {

        notificacion.enviar(alerta.toString());

    }

    @Override
    public void deshacer() {

        System.out.println("\n=== DESHACIENDO ÚLTIMO ENVÍO ===");

        Logger.getInstance().registrar(
                "Último envío cancelado."
        );

    }

}