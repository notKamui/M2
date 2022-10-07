package fr.uge.jee.annotation.onlineshop;

import org.springframework.stereotype.Component;

@Component
public final class TheftInsurance implements Insurance {

    @Override
    public String getDescription() {
        return "Theft insurance";
    }
}
