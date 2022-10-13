package fr.uge.jee.springmvc.rectanglesession;

import fr.uge.jee.springmvc.rectangle.RectangleForm;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rectanglesession")
public class RectangleSessionController {

    @GetMapping
    public String rectangle(Model model, HttpSession session) {
        var rectangles = getRectangles(session);
        model.addAttribute("rectangle", new RectangleForm());
        model.addAttribute("rectangles", rectangles);
        return "rectangle-session-form";
    }

    @PostMapping
    public String rectangle(
        @Valid @ModelAttribute("rectangle") RectangleForm form,
        BindingResult binding,
        Model model,
        HttpSession session
    ) {
        var rectangles = getRectangles(session);
        if (binding.hasErrors()) {
            model.addAttribute("invalid", true);
            model.addAttribute("rectangles", rectangles);
            return "rectangle-session-form";
        }
        rectangles.add(form);
        model.addAttribute("area", form.getArea());
        return "rectangle-result";
    }

    @SuppressWarnings("unchecked")
    private List<RectangleForm> getRectangles(HttpSession session) {
        var rectangles = (List<RectangleForm>) session.getAttribute("rectangles");
        if (rectangles == null) {
            rectangles = new ArrayList<>();
            session.setAttribute("rectangles", rectangles);
        }
        return rectangles;
    }
}
