package fr.uge.jee.springmvc.rectangle;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/rectangle")
public class RectangleController {

    @GetMapping
    public String rectangle(@ModelAttribute("rectangle") RectangleForm form) {
        return "rectangle-form";
    }

    @PostMapping
    public String rectangle(@Valid @ModelAttribute("rectangle") RectangleForm form, BindingResult binding, Model model) {
        if (binding.hasErrors()) {
            model.addAttribute("invalid", true);
            return "rectangle-form";
        }
        model.addAttribute("area", form.getArea());
        return "rectangle-result";
    }
}
