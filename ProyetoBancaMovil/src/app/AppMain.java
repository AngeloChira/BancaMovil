package app;

import builder.Alerta;
import builder.AlertaConcreteBuilder;
import builder.Director;
import composite.Agencia;
import composite.Banco;
import composite.Cajero;
import decorator.NotificacionUrgente;
import factory.EmailFactory;
import factory.NotificacionFactory;
import factory.SMSfactory;
import factory.WhatsFactory;
import modelos.Notificacion;
import singleton.Logger;

import java.util.Scanner;

public class AppMain {

    public static void main(String[] args) {

        Scanner tc = new Scanner(System.in);

        // Menu de interaccion con el usuario
        System.out.println("--- Sistema de Notificacion Multicanal ---");
        System.out.println("Seleccione un canal de mensaje:");
        System.out.println("1) Email");
        System.out.println("2) SMS");
        System.out.println("3) WhatsApp");

        String opcion = tc.nextLine();

        // Variable para almacenar la fabrica elegida (Patron Factory)
        NotificacionFactory factory = null;

        switch (opcion) {
            case "1":
                factory = new EmailFactory();
                break;
            case "2":
                factory = new SMSfactory(); // Aqui el Factory usa internamente el SMSAdapter
                break;
            case "3":
                factory = new WhatsFactory();
                break;
            default:
                System.out.println("Opcion no valida");
                return;
        }

        // BUILDER
        // Se usa un Director para ensamblar la alerta paso a paso
        Director director = new Director();

        Alerta alerta = director.construirAlertaSeguridad(
                new AlertaConcreteBuilder()
        );

        // FACTORY METHOD
        // Se crea la notificacion usando la fabrica seleccionada por el usuario
        Notificacion mensaje = factory.crearNotificacion();

        // DECORATOR
        // Envolvemos la notificacion base agregandole el comportamiento "Urgente"
        mensaje = new NotificacionUrgente(mensaje);

        // Enviamos el mensaje (La alerta convertida a String)
        mensaje.enviar(alerta.toString());

        // SINGLETON
        // Obtenemos la unica instancia del Logger para registrar el evento
        Logger.getInstance().mostrarHistorial();

        // COMPOSITE
        // Construimos un arbol jerarquico: Banco -> Agencias -> Cajeros
        Banco banco = new Banco("Banco Movil");

        Agencia chiclayo =
                new Agencia("Chiclayo");

        Agencia lambayeque =
                new Agencia("Lambayeque");

        chiclayo.agregar(
                new Cajero("Cajero 1")
        );

        chiclayo.agregar(
                new Cajero("Cajero 2")
        );

        lambayeque.agregar(
                new Cajero("Cajero 3")
        );

        lambayeque.agregar(
                new Cajero("Cajero 4")
        );

        banco.agregar(chiclayo);
        banco.agregar(lambayeque);

        // Disparamos la alerta a la raiz (Banco), y se propagara a todos los hijos (Agencias y Cajeros)
        banco.recibirAlerta(alerta.toString()
        );
    }
}