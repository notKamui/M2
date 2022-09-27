package fr.uge.jee.onlineshop;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    public static void main(String[] args) {
        var context = new ClassPathXmlApplicationContext("config-onlineshopv3.xml");

        var amazon = context.getBean(OnlineShop.class);
        amazon.printDescription();
    }
}
