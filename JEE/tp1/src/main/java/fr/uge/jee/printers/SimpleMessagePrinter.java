package fr.uge.jee.printers;

public class SimpleMessagePrinter implements MessagePrinter {

    @Override
    public void printMessage() {
        System.out.println("Hello World!");
    }
}
