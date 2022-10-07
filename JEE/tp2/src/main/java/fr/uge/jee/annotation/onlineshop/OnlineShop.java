package fr.uge.jee.annotation.onlineshop;

import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class OnlineShop {

    private final String name;
    private final Set<Delivery> deliveryOptions;
    private final Set<Insurance> insurances;

    public OnlineShop(@Value("${onlineshop.name}") String name, Set<Delivery> deliveryOptions, Set<Insurance> insurances) {
        this.name = name;
        this.deliveryOptions = deliveryOptions;
        this.insurances = insurances;
    }

    public void printDescription() {
        System.out.println(name);
        System.out.println("Delivery options");
        deliveryOptions.forEach(opt -> System.out.println("\t" + opt.getDescription()));
        System.out.println("Insurance options");
        insurances.forEach(ins -> System.out.println("\t" + ins.getDescription()));
    }
}
