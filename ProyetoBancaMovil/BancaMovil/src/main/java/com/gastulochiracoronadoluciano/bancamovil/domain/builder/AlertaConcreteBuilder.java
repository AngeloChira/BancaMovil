package com.gastulochiracoronadoluciano.bancamovil.domain.builder;

// (Concrete Builder) Implementacion especifica de los pasos de construccion
public class AlertaConcreteBuilder implements AlertaBuilder {

    private String titulo;
    private String contenido;
    private String prioridad;
    private String destinatario;

    @Override
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    @Override
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    @Override
    public Alerta build() {
        return new Alerta(
                titulo,
                contenido,
                prioridad,
                destinatario
        );
    }
}
