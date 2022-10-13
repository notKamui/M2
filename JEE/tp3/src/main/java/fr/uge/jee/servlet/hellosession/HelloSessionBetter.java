package fr.uge.jee.servlet.hellosession;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/hellosessionbetter")
public class HelloSessionBetter extends HttpServlet {

    private final Object lock = new Object();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(true);
        int counter;
        synchronized (lock) {
            if (session.isNew()) {
                counter = 1;
            } else {
                counter = (Integer) session.getAttribute("counter");
                counter++;
            }
            session.setAttribute("counter", counter);
        }
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        var writer = resp.getWriter();
        writer.println("<!DOCTYPE html><html><h1>Hello for the " + counter + "st/nd/rd/th time</p></html>");
        writer.flush();
    }
}
