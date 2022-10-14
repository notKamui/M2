package fr.uge.jee.servlet.hello;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html ; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        var writer = resp.getWriter();
        writer.println("<!DOCTYPE html><html><h1>Hello world!</h1></html>");
        writer.flush();
    }
}
