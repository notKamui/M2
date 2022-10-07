package fr.uge.jee.annotation.onlineshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class StandardDelivery implements Delivery {

    private final int delay;

    public StandardDelivery(@Value("${onlineshop.standarddelivery.delay}") int delay) {
        this.delay = delay;
    }

    @Override
    public String getDescription() {
        return "Standard Delivery with a delay of " + delay + " days";
    }
}
