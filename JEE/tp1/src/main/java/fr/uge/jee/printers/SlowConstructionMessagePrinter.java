package fr.uge.jee.printers;

public class SlowConstructionMessagePrinter implements MessagePrinter {

    private final String message;

    public SlowConstructionMessagePrinter() throws InterruptedException {
        Thread.sleep(1000);
        message = "Slow Hello World";
    }

    @Override
    public void printMessage() {
        System.out.println(message);
    }
}
