package fr.uge.jee.printers;

public class CustomizableMessagePrinter implements MessagePrinter {

    private String message;

    public CustomizableMessagePrinter() {
        this.message = "Custom Hello World";
    }

    public CustomizableMessagePrinter(String message) {
        this.message = message;
    }

    @Override
    public void printMessage() {
        System.out.println(message);
    }
}