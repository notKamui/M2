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

//    public Pokemon findMatch(String firstname, String lastname) {
//        var hash = (firstname + lastname).hashCode();
//        return storage.all()
//            .stream()
//            .min(Comparator.comparingInt(pokemon -> Math.abs(pokemon.hashName() - hash)))
//            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon match for " + firstname + " " + lastname));
//    }

    public Pokemon findMatch(String firstname, String lastname) {
        var pokemons = storage.all();
        if (pokemons.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pokemon match for " + firstname + " " + lastname);
        }

        var hash = (lastname + firstname).hashCode();

        var min = 0;
        var max = pokemons.size() - 1;
        int mid = 0;
        while (min < max) {
            mid = (min + max) / 2;
            var pokemon = pokemons.get(mid);
            if (pokemon.hashName() < hash) {
                min = mid + 1;
            } else {
                max = mid;
            }
        }

        var minP = pokemons.get(min);
        var midP = pokemons.get(mid);

        return Math.abs(minP.hashName() - hash) < Math.abs(midP.hashName() - hash) ? minP : midP;
    }

    public Collection<Pokemon> all() {
        return storage.all();
    }
}
