package fr.uge.jee.servlet.hellosession;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/hellosession")
public class HelloSession extends HttpServlet {

    private SessionManager sessionManager;

    @Override
    public void init() throws ServletException {
        sessionManager = new SessionManager();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = sessionManager.getSessionId(req);
        if (session == null) {
            session = UUID.randomUUID();
            resp.addCookie(new Cookie("session-token", session.toString()));
        }
        var counter = sessionManager.incrementCounter(session);
        resp.setContentType("text/html ; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        var writer = resp.getWriter();
        writer.println("<!DOCTYPE html><html><h1>Hello for the " + counter + "st/nd/rd/th time</p></html>");
        writer.flush();
    }
}
