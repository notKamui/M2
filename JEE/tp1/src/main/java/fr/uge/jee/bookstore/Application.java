package fr.uge.jee.bookstore;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("config-bookstore.xml");

        var library = context.getBean(Library.class);
        System.out.println(library);
    }
}
