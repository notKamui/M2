package fr.uge.jee.springmvc.pokematch.web.pokemon.inverseapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.uge.jee.springmvc.pokematch.web.pokemon.inverseapi.gql.PokemonGQLResponse;
import fr.uge.jee.springmvc.pokematch.web.pokemon.inverseapi.rest.PokemonExcerptListResponse;
import fr.uge.jee.springmvc.pokematch.web.pokemon.inverseapi.rest.PokemonResponse;
import fr.uge.jee.springmvc.pokematch.web.pokemon.model.Pokemon;
import graphql.kickstart.spring.webclient.boot.GraphQLRequest;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PokemonStorage {

    private final static String POKEMON_API_URL = "https://pokeapi.co/api/v2/pokemon";
    private final static String POKEMON_GQL_QUERY = "query pokemonQuery {\n" +
        "  results: pokemon_v2_pokemonsprites(limit: 40) {\n" +
        "    data: pokemon_v2_pokemon {\n" +
        "      id\n" +
        "      name\n" +
        "    }\n" +
        "    sprites\n" +
        "  }\n" +
        "}\n";

    private final List<Pokemon> pokemons;

    public PokemonStorage(List<Pokemon> pokemons) {
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

        return new PokemonStorage(pokemons);
    }

    public static PokemonStorage create(GraphQLWebClient graphQLWebClient, ObjectMapper objectMapper) {
        var query = GraphQLRequest.builder().query(POKEMON_GQL_QUERY).build();
        var response = graphQLWebClient.post(query).block();
        if (response == null) {
            throw new IllegalArgumentException("Could not build query");
        }
        var results = response.getList("results", PokemonGQLResponse.class);
        if (results == null) {
            throw new IllegalStateException("Could not retrieve pokemon list");
        }

        var pokemons = results.stream()
            .map(data -> data.toPokemon(objectMapper))
            .filter(Objects::nonNull)
            .sorted(Comparator.comparingInt(Pokemon::hashName))
            .collect(Collectors.toList());

        return new PokemonStorage(pokemons);
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
