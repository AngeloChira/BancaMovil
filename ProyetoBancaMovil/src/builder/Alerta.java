package builder;

// (Product) El objeto complejo que queremos construir paso a paso
public class Alerta {

    private String titulo;
    private String contenido;
    private String prioridad;
    private String destinatario;

    public Alerta(String titulo,
                  String contenido,
                  String prioridad,
                  String destinatario) {

        this.titulo = titulo;
        this.contenido = contenido;
        this.prioridad = prioridad;
        this.destinatario = destinatario;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public String getDestinatario() {
        return destinatario;
    }

    // Metodo para formatear la salida del objeto
    @Override
    public String toString() {
        return "\nTitulo: " + titulo +
                "\nContenido: " + contenido +
                "\nPrioridad: " + prioridad +
                "\nDestinatario: " + destinatario;
    }
}
