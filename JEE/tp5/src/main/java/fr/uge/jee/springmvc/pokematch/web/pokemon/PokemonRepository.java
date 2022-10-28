package fr.uge.jee.springmvc.pokematch.web.pokemon;

import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashSet;
import java.util.Set;

public class PokemonRepository {

    private final static String POKEMON_API_URL = "https://pokeapi.co/api/v2/pokemon";

    private final Set<Pokemon> pokemons;

    public PokemonRepository(Set<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public static PokemonRepository create(WebClient client) {
        var pokemons = new HashSet<Pokemon>();
        client.get()
            .uri(POKEMON_API_URL + "?limit=40")
            .retrieve()
            .bodyToMono(PokemonExcerptListResponse.class)
            .subscribe(list -> list.getResults()
                .forEach(excerpt -> client.get()
                    .uri(excerpt.getUrl())
                    .retrieve()
                    .bodyToMono(PokemonResponse.class)
                    .map(PokemonResponse::toPokemon)
                    .subscribe(pokemons::add)
                ));
        return new PokemonRepository(pokemons);
    }

    public Set<Pokemon> all() {
        return Set.copyOf(pokemons);
    }
}
