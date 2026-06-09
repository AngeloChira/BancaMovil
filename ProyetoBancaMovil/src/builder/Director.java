package builder;

// (Director) Controla el orden de construccion usando el Builder
public class Director {

    // Construye una alerta con datos predefinidos de seguridad
    public Alerta construirAlertaSeguridad(
            AlertaBuilder builder) {

        builder.setTitulo("Codigo de Seguridad");
        builder.setContenido("Su codigo es 123456");
        builder.setPrioridad("Alta");
        builder.setDestinatario("Cliente");

        return builder.build(); // Devuelve el producto final
    }
}

