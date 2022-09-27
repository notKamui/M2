package fr.uge.jee.onlineshop;

public class DroneDelivery implements Delivery {

    @Override
    public String getDescription() {
        return "Delivery by drone";
    }
}
