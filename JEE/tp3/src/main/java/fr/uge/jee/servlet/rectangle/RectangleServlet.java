package fr.uge.jee.servlet.rectangle;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@WebServlet("/rectangle")
public class RectangleServlet extends HttpServlet {

    private String rectangleFormTemplate;
    private String rectangleResponseTemplate;

    @Override
    public void init() throws ServletException {
        try {
            rectangleFormTemplate = readFromInputStream(getServletContext().getResourceAsStream("/WEB-INF/templates/rectangle-form.html"));
            rectangleResponseTemplate = readFromInputStream(getServletContext().getResourceAsStream("/WEB-INF/templates/rectangle-response.html"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html ; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        var writer = resp.getWriter();
        writer.println(rectangleFormTemplate
                .replaceAll("\\{height}", "")
                .replaceAll("\\{width}", "")
                .replaceAll("\\{invalidParams}", "")
        );
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html ; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        var writer = resp.getWriter();
        var height = req.getParameter("height");
        var width = req.getParameter("width");

        try {
            var heightAsInt = Integer.parseInt(height);
            var widthAsInt = Integer.parseInt(width);

            writer.println(rectangleResponseTemplate
                    .replaceAll("\\{area}", String.valueOf(heightAsInt * widthAsInt))
            );
        } catch (NumberFormatException e) {
            writer.println(rectangleFormTemplate
                    .replaceAll("\\{height}", height)
                    .replaceAll("\\{width}", width)
                    .replaceAll("\\{invalidParams}", "<div>Invalid input</div>")
            );
        }
        writer.flush();
    }

    public static String readFromInputStream(InputStream inputStream) throws IOException {
        var lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines();
        return lines.collect(Collectors.joining("\n"));
    }
}
