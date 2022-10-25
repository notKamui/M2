package fr.uge.jee.springmvc.pokematch.rest;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PokemonService {

    private final PokemonRepository repository;

    public PokemonService(PokemonRepository repository) {
        this.repository = repository;
    }

    public Optional<Pokemon> findByName(String name) {
        return repository.all()
            .stream()
            .filter(pokemon -> pokemon.getName().equals(name))
            .findFirst();
    }

    public Optional<Pokemon> findById(long id) {
        return repository.all()
            .stream()
            .filter(pokemon -> pokemon.getId() == id)
            .findFirst();
    }

    public Set<Pokemon> all() {
        return repository.all();
    }
}
