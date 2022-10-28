package fr.uge.jee.springmvc.pokematch.web.pokemon;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PokemonService {

    private final PokemonRepository repository;

    public PokemonService(PokemonRepository repository) {
        this.repository = repository;
    }

    public Pokemon findByName(String name) {
        return repository.all()
            .stream()
            .filter(pokemon -> pokemon.getName().equals(name))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon with name " + name));
    }

    public Pokemon findById(long id) {
        return repository.all()
            .stream()
            .filter(pokemon -> pokemon.getId() == id)
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon with id " + id));
    }

    public Set<Pokemon> all() {
        return repository.all();
    }
}
