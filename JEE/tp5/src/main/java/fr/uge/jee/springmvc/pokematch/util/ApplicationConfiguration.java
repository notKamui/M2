package fr.uge.jee.springmvc.pokematch.util;

import fr.uge.jee.springmvc.pokematch.web.pokemon.PokemonRepository;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootConfiguration
public class ApplicationConfiguration {

    @Bean
    public WebClient webClient(WebClient.Builder defaultBuilder) {
        return defaultBuilder.exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> configurer
                .defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024)).build()).build();
    }

    @Bean
    public PokemonRepository pokemonRepository(WebClient webClient) {
        return PokemonRepository.create(webClient);
    }
}
