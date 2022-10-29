package fr.uge.jee.springmvc.pokematch.web.pokemon;

import java.util.Collection;
import java.util.Comparator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

@Service
public class PokemonService {

    private final PokemonStorage storage;

    public PokemonService(PokemonStorage storage) {
        this.storage = storage;
    }

    public Pokemon findByName(String name) {
        return storage.all()
            .stream()
            .filter(pokemon -> pokemon.getName().equals(name))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon with name " + name));
    }

    public Pokemon findById(long id) {
        return storage.all()
            .stream()
            .filter(pokemon -> pokemon.getId() == id)
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon with id " + id));
    }

    public Pokemon findMatch(String firstname, String lastname) {
        var hash = (lastname + firstname).hashCode();
        return storage.all()
            .stream()
            .min(Comparator.comparingInt(pokemon -> Math.abs(pokemon.hashName() - hash)))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon found"));
    }

    public Collection<Pokemon> all() {
        return storage.all();
    }
}
