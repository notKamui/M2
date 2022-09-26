package fr.uge.jee.printers;

public class CountMessagePrinter implements MessagePrinter {

    private int count = 0;

    @Override
    public void printMessage() {
        System.out.println("This is message number " + count++);
    }
}
