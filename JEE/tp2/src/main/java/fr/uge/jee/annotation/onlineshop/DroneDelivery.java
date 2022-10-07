package fr.uge.jee.annotation.onlineshop;

public final class DroneDelivery implements Delivery {

    @Override
    public String getDescription() {
        return "Delivery by drone";
    }
}
