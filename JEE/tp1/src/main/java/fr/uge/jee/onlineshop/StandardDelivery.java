package fr.uge.jee.onlineshop;

public class StandardDelivery implements Delivery {

    private int delay;

    @Override
    public String getDescription() {
        return "Standard Delivery with a delay of " + delay + " days";
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
