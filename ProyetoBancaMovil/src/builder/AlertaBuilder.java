package builder;

// (Builder) Interfaz que define los pasos para construir la Alerta
public interface AlertaBuilder {

    void setTitulo(String titulo);

    void setContenido(String contenido);

    void setPrioridad(String prioridad);

    void setDestinatario(String destinatario);

    Alerta build(); // Metodo final que devuelve el objeto construido
}
