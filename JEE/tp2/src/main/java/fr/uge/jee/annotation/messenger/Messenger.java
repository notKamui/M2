package fr.uge.jee.annotation.messenger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class Messenger {

    @Value("${MESSENGER_TOKEN}")
    private String token;

    public void send(String message) {
        System.out.println("Using the super secret token " + token + " to send the message : " + message);
    }
}