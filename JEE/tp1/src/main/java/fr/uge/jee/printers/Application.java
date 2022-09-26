package fr.uge.jee.printers;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("config-printers.xml");

        var printer = context.getBean("printer", MessagePrinter.class);
        var frenchPrinter = context.getBean("frenchPrinter", MessagePrinter.class);
        var customPrinter = context.getBean("customPrinter", MessagePrinter.class);

        printer.printMessage();
        frenchPrinter.printMessage();
        customPrinter.printMessage();

        var countPrinter = context.getBean("countPrinter", MessagePrinter.class);
        countPrinter.printMessage();
        countPrinter.printMessage();
        countPrinter.printMessage();
        var counterPrinter2 = context.getBean("countPrinter", MessagePrinter.class);
        counterPrinter2.printMessage();
        // countPrinter and counterPrinter2 are the same object by default
        // The IoC container implements the singleton pattern
        // To change this behavior, we can use the scope attribute in the bean definition
        // To create a new instance each time, we can use the prototype scope

        var slowPrinter = context.getBean("slowPrinter", MessagePrinter.class);
        slowPrinter.printMessage();
        // By default, singleton bean instances are created eagerly on startup
        // To change this behavior, we can use the lazy-init attribute in the bean definition
        // The possible issue with that is that it may be slow during the application, blocking the thread.
    }
}
