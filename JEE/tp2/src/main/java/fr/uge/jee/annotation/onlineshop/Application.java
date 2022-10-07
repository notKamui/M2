package fr.uge.jee.annotation.onlineshop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class Application {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(OnlineShopConfig.class);

        var amazon = context.getBean(OnlineShop.class);
        amazon.printDescription();
    }
}
