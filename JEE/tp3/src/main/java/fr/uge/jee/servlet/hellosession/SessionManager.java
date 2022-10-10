package fr.uge.jee.servlet.hellosession;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {

    private final Object lock = new Object();
    private final Map<UUID, Integer> sessions = new HashMap<>();

    public int incrementCounter(UUID sessionId) {
        synchronized (lock) {
            return sessions.merge(sessionId, 1, Integer::sum);
        }
    }

    public UUID getSessionId(HttpServletRequest req) {
        synchronized (lock) {
            var cookie = req.getCookies();
            if (cookie == null) {
                return null;
            }
            for (var c : cookie) {
                if (c.getName().equals("session-token")) {
                    return UUID.fromString(c.getValue());
                }
            }
            return null;
        }
    }
}
