package com.gastulochiracoronadoluciano.bancamovil.domain.adapter;



import com.gastulochiracoronadoluciano.bancamovil.domain.modelos.Notificacion;

// (Adapter) El puente que disfraza el sistema antiguo como uno moderno
public class SMSAdapter implements Notificacion {

    // Contiene una instancia del sistema que queremos adaptar
    private SistemaSMSAntiguo sistemaAntiguo;

    public SMSAdapter() {
        sistemaAntiguo = new SistemaSMSAntiguo();
    }

    // Cumple con la interfaz moderna, pero por dentro usa el metodo viejo
    @Override
    public void enviar(String mensaje) {
        sistemaAntiguo.mandarTexto(mensaje);
    }
}

