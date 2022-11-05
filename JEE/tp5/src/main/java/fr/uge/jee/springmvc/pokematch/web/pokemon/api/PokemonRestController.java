package fr.uge.jee.springmvc.pokematch.web.pokemon.api;

import fr.uge.jee.springmvc.pokematch.web.pokemon.model.Pokemon;
import java.util.Collection;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pokemon/api")
public class PokemonRestController {

    private final PokemonService service;

    public PokemonRestController(PokemonService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Collection<Pokemon>> all() {
        return ResponseEntity.ok(service.all());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Pokemon> findById(@PathVariable long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Pokemon> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @GetMapping("/sprite/{id}")
    public ResponseEntity<byte[]> findSpriteById(@PathVariable long id) {
        var sprite = service.getSprite(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(sprite);
    }
}
