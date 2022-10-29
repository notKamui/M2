package fr.uge.jee.springmvc.pokematch.web.pokemon;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pokemon")
public class PokematchController {

    private final PokemonService service;

    public PokematchController(PokemonService service) {
        this.service = service;
    }

    @GetMapping
    public String form(@ModelAttribute("identityForm") IdentityForm identityForm) {
        return "pokemon/index";
    }

    @PostMapping
    public String result(@Valid @ModelAttribute("identityForm") IdentityForm identityForm, BindingResult binding, Model model) {
        if (binding.hasErrors()) {
            return "pokemon/index";
        }
        var match = service.findMatch(identityForm.getFirstname(), identityForm.getLastname());
        model.addAttribute("match", match);
        return "pokemon/index";
    }
}
