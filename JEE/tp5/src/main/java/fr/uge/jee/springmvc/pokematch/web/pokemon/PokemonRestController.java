package fr.uge.jee.springmvc.pokematch.web.pokemon;

import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon/api")
public class PokemonRestController {

    private final PokemonService service;

    public PokemonRestController(PokemonService service) {
        this.service = service;
    }

    @GetMapping
    public Set<Pokemon> all() {
        return service.all();
    }

    @GetMapping("/id/{id}")
    public Pokemon findById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping("/name/{name}")
    public Pokemon findByName(@PathVariable String name) {
        return service.findByName(name);
    }
}
