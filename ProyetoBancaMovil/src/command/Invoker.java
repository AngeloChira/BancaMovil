package command;

import java.util.Stack;

public class Invoker {

    private Stack<Command> undo;

    private Stack<Command> redo;

    public Invoker() {

        undo = new Stack<>();

        redo = new Stack<>();

    }

    public void ejecutar(Command command) {

        command.ejecutar();

        undo.push(command);

        redo.clear();

    }

    public void undo() {

        if (!undo.isEmpty()) {

            Command command = undo.pop();

            command.deshacer();

            redo.push(command);

        }
        else {

            System.out.println("Nada que deshacer.");

        }

    }

    public void redo() {

        if (!redo.isEmpty()) {

            Command command = redo.pop();

            command.ejecutar();

            undo.push(command);

        }
        else {

            System.out.println("Nada que rehacer.");

        }

    }

}