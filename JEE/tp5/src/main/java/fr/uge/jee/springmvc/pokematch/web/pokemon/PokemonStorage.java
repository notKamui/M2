package fr.uge.jee.springmvc.pokematch.web.pokemon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PokemonStorage {

    private final static String POKEMON_API_URL = "https://pokeapi.co/api/v2/pokemon";

    private final WebClient client;
    private final List<Pokemon> pokemons;

    public PokemonStorage(WebClient client, List<Pokemon> pokemons) {
        this.client = client;
        this.pokemons = pokemons;
    }

    public static PokemonStorage create(WebClient client) {
        var excerptList = client.get()
            .uri(POKEMON_API_URL + "?limit=40")
            .retrieve()
            .bodyToMono(PokemonExcerptListResponse.class)
            .block();

        if (excerptList == null) {
            throw new IllegalStateException("Could not retrieve pokemon list");
        }

        var monos = new ArrayList<Mono<Pokemon>>();
        for (var excerpt : excerptList.getResults()) {
            monos.add(client.get()
                .uri(excerpt.getUrl())
                .retrieve()
                .bodyToMono(PokemonResponse.class)
                .map(PokemonResponse::toPokemon));
        }

        var pokemons = Flux.merge(monos)
            .sort(Comparator.comparingInt(Pokemon::hashName))
            .collectList()
            .block();

        return new PokemonStorage(client, pokemons);
    }

    public byte[] getImage(String url) {
        return client.get()
            .uri(url)
            .retrieve()
            .bodyToMono(byte[].class)
            .block();
    }

    /**
     * All the Pokémon sorted by their hash name.
     *
     * @return all the Pokémon sorted by their hash name
     */
    public List<Pokemon> all() {
        return List.copyOf(pokemons);
    }
}
