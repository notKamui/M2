package fr.uge.jee.springmvc.pokematch.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.uge.jee.springmvc.pokematch.web.pokemon.model.PokemonLeaderboard;
import fr.uge.jee.springmvc.pokematch.web.pokemon.inverseapi.PokemonStorage;
import graphql.kickstart.spring.webclient.boot.GraphQLWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootConfiguration
public class ApplicationConfiguration {

    @Value("${leaderboard.maxsize}")
    private int leaderboardMaxSize;

    @Value("${graphql.client.url}")
    private String graphqlClientUrl;

    @Bean
    public WebClient webClient(WebClient.Builder defaultBuilder) {
        return defaultBuilder.exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024)).build()).build();
    }

    @Bean
    public GraphQLWebClient graphQLWebClient(WebClient.Builder defaultBuilder, ObjectMapper objectMapper) {
        var client = defaultBuilder.baseUrl(graphqlClientUrl) // different from the default because of the base URL
            .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024)).build()).build();
        return GraphQLWebClient.newInstance(client, objectMapper);
    }

    @Bean
    public PokemonStorage pokemonRepository(GraphQLWebClient graphQLWebClient, ObjectMapper objectMapper) {
        return PokemonStorage.create(graphQLWebClient, objectMapper);
    }

    @Bean
    public PokemonLeaderboard pokemonLeaderboard() {
        return PokemonLeaderboard.create(leaderboardMaxSize);
    }
}
