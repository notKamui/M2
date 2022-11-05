package fr.uge.jee.springmvc.pokematch.web.pokemon.api;

import fr.uge.jee.springmvc.pokematch.web.pokemon.model.IdentityForm;
import fr.uge.jee.springmvc.pokematch.web.pokemon.model.PokemonLeaderboard;
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
    private final PokemonLeaderboard leaderboard;

    public PokematchController(PokemonService service, PokemonLeaderboard leaderboard) {
        this.service = service;
        this.leaderboard = leaderboard;
    }

    @GetMapping
    public String form(@ModelAttribute("identityForm") IdentityForm identityForm, Model model) {
        model.addAttribute("leaderboard", leaderboard);
        return "pokemon/index";
    }

    @PostMapping
    public String result(@Valid @ModelAttribute("identityForm") IdentityForm identityForm, BindingResult binding, Model model) {
        if (binding.hasErrors()) {
            model.addAttribute("leaderboard", leaderboard);
            return "pokemon/index";
        }

        var match = service.findMatch(identityForm.getFirstname(), identityForm.getLastname());
        leaderboard.scorePokemon(match);
        model.addAttribute("match", match);
        model.addAttribute("leaderboard", leaderboard);
        return "pokemon/index";
    }
}
