package app;

import builder.Alerta;
import builder.AlertaConcreteBuilder;
import builder.Director;
import command.EnviarNotificacionCommand;
import command.Invoker;
import composite.Agencia;
import composite.Banco;
import composite.Cajero;
import decorator.NotificacionUrgente;
import factory.EmailFactory;
import factory.NotificacionFactory;
import factory.SMSfactory;
import factory.WhatsFactory;
import modelos.Notificacion;
import observer.ContadorObserver;
import observer.PanelObserver;
import singleton.Logger;
import state.EstadoActivo;
import state.EstadoBloqueado;
import state.EstadoMantenimiento;
import state.SistemaContexto;
import strategy.ContextoEnvio;
import strategy.EnvioNormal;
import strategy.EnvioPrioritario;
import strategy.EnvioSeguro;

import java.util.Scanner;

public class AppMain {

    public static void main(String[] args) {

        Scanner tc = new Scanner(System.in);

        // ===========================
        // OBSERVER
        // ===========================
        PanelObserver panel = new PanelObserver();
        ContadorObserver contador = new ContadorObserver();

        Logger.getInstance().agregarObserver(panel);
        Logger.getInstance().agregarObserver(contador);

        // ===========================
        // MENÚ CANAL (FACTORY)
        // ===========================
        System.out.println("--- Sistema de Notificación Multicanal ---");
        System.out.println("1) Email");
        System.out.println("2) SMS");
        System.out.println("3) WhatsApp");

        String opcion = tc.nextLine();

        NotificacionFactory factory;

        switch (opcion) {

            case "1":
                factory = new EmailFactory();
                break;

            case "2":
                factory = new SMSfactory();
                break;

            case "3":
                factory = new WhatsFactory();
                break;

            default:
                System.out.println("Opción inválida");
                return;
        }

        // ===========================
        // STRATEGY
        // ===========================
        System.out.println("\nModo de envío:");
        System.out.println("1) Normal");
        System.out.println("2) Seguro");
        System.out.println("3) Prioritario");

        String opStr = tc.nextLine();

        ContextoEnvio contexto;

        switch (opStr) {

            case "1":
                contexto = new ContextoEnvio(new EnvioNormal());
                break;

            case "2":
                contexto = new ContextoEnvio(new EnvioSeguro());
                break;

            case "3":
                contexto = new ContextoEnvio(new EnvioPrioritario());
                break;

            default:
                contexto = new ContextoEnvio(new EnvioNormal());
        }

        // ===========================
        // STATE
        // ===========================
        System.out.println("\nEstado del sistema:");
        System.out.println("1) Activo");
        System.out.println("2) Mantenimiento");
        System.out.println("3) Bloqueado");

        String opState = tc.nextLine();

        SistemaContexto sistema;

        switch (opState) {

            case "1":
                sistema = new SistemaContexto(new EstadoActivo());
                break;

            case "2":
                sistema = new SistemaContexto(new EstadoMantenimiento());
                break;

            case "3":
                sistema = new SistemaContexto(new EstadoBloqueado());
                break;

            default:
                sistema = new SistemaContexto(new EstadoActivo());
        }

        sistema.ejecutar();

        // ===========================
        // BUILDER
        // ===========================
        Director director = new Director();

        Alerta alerta = director.construirAlertaSeguridad(
                new AlertaConcreteBuilder()
        );

        // ===========================
        // FACTORY
        // ===========================
        Notificacion mensaje = factory.crearNotificacion();

        // ===========================
        // DECORATOR
        // ===========================
        mensaje = new NotificacionUrgente(mensaje);

        // ===========================
        // STRATEGY
        // ===========================
        contexto.ejecutar(alerta.toString());

        // ===========================
        // COMMAND + STATE CONTROL
        // ===========================
        Invoker invoker = new Invoker();

        EnviarNotificacionCommand comando =
                new EnviarNotificacionCommand(
                        mensaje,
                        alerta
                );

        if (sistema.puedeOperar()) {

            invoker.ejecutar(comando);

            invoker.undo();
            invoker.redo();

        } else {

            System.out.println("\n=== ENVÍO BLOQUEADO POR ESTADO DEL SISTEMA ===");

            Logger.getInstance().registrar(
                    "Intento de envío bloqueado por estado del sistema."
            );
        }

        // ===========================
        // SINGLETON (LOG)
        // ===========================
        Logger.getInstance().mostrarHistorial();

        // ===========================
        // COMPOSITE
        // ===========================
        Banco banco = new Banco("Banco Movil");

        Agencia chiclayo = new Agencia("Chiclayo");
        Agencia lambayeque = new Agencia("Lambayeque");

        chiclayo.agregar(new Cajero("Cajero 1"));
        chiclayo.agregar(new Cajero("Cajero 2"));

        lambayeque.agregar(new Cajero("Cajero 3"));
        lambayeque.agregar(new Cajero("Cajero 4"));

        banco.agregar(chiclayo);
        banco.agregar(lambayeque);

        banco.recibirAlerta(alerta.toString());

        tc.close();
    }
}