package fr.uge.jee.annotation.onlineshop;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class ReturnInsurance implements Insurance {

    private final boolean membersOnly;

    public ReturnInsurance(@Value("${onlineshop.returninsurance.membersonly}") boolean membersOnly) {
        this.membersOnly = membersOnly;
    }

    @Override
    public String getDescription() {
        return "Return insurance" + (membersOnly ? " for members only" : "");
    }
}
