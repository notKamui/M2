package fr.uge.jee.onlineshop;

import java.util.Set;

public class OnlineShop {

    private String name;
    private Set<Delivery> deliveryOptions;
    private Set<Insurance> insurances;

    public void printDescription() {
        System.out.println(name);
        System.out.println("Delivery options");
        deliveryOptions.forEach(opt -> System.out.println("\t" + opt.getDescription()));
        System.out.println("Insurance options");
        insurances.forEach(ins -> System.out.println("\t" + ins.getDescription()));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDeliveryOptions(Set<Delivery> deliveryOptions) {
        this.deliveryOptions = Set.copyOf(deliveryOptions);
    }

    public void setInsurances(Set<Insurance> insurances) {
        this.insurances = Set.copyOf(insurances);
    }
}
