package fr.uge.jee.springmvc.pokematch.web.pokemon;

import java.util.Collection;
import java.util.Comparator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PokemonService {

    private final PokemonStorage storage;
    private final WebClient client;

    public PokemonService(PokemonStorage storage, WebClient client) {
        this.storage = storage;
        this.client = client;
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

        var hash = (firstname + lastname).hashCode();

        // Find the Pokémon with the smallest hash difference by dichotomy
        var min = 0;
        var max = pokemons.size() - 1;
        var middle = (min + max) / 2;
        var pokemon = pokemons.get(middle);
        while (min < max) {
            if (pokemon.hashName() < hash) {
                min = middle + 1;
            } else {
                max = middle;
            }
            middle = (min + max) / 2;
            pokemon = pokemons.get(middle);
        }

        // check if the next Pokémon is closer
        var next = middle + 1;
        if (next < pokemons.size()) {
            var nextPokemon = pokemons.get(next);
            if (Math.abs(nextPokemon.hashName() - hash) <= Math.abs(pokemon.hashName() - hash)) {
                pokemon = nextPokemon;
            }
        }

        return pokemon;
    }

    public Collection<Pokemon> all() {
        return storage.all();
    }

    public byte[] getSprite(long id) {
        var pokemon = findById(id);
        var sprite = pokemon.getSprite();
        if (sprite == null) {
            sprite = getImage(pokemon.getSpriteUrl());
            pokemon.setSprite(sprite);
        }
        return sprite;
    }

    private byte[] getImage(String url) {
        return client.get()
            .uri(url)
            .retrieve()
            .bodyToMono(byte[].class)
            .block();
    }
}
