package fr.uge.jee.springmvc.pokematch.util;

import fr.uge.jee.springmvc.pokematch.web.pokemon.PokemonLeaderboard;
import fr.uge.jee.springmvc.pokematch.web.pokemon.PokemonStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootConfiguration
@PropertySource("classpath:application.pokemon.properties")
public class ApplicationConfiguration {

    @Value("${leaderboard.maxsize}")
    private int leaderboardMaxSize;

    @Bean
    public WebClient webClient(WebClient.Builder defaultBuilder) {
        return defaultBuilder.exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024)).build()).build();
    }

    @Bean
    public PokemonStorage pokemonRepository(WebClient webClient) {
        return PokemonStorage.create(webClient);
    }

    @Bean
    public PokemonLeaderboard pokemonLeaderboard() {
        return PokemonLeaderboard.create(leaderboardMaxSize);
    }
}
