package fr.uge.jee.springmvc.pokematch.web.pokemon.inverseapi.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

import static java.util.Objects.*;
import static java.util.Objects.requireNonNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonExcerptListResponse {

    @JsonProperty("results")
    private List<PokemonExcerptResponse> results;

    public PokemonExcerptListResponse() {
    }

    public PokemonExcerptListResponse(List<PokemonExcerptResponse> results) {
        this.results = requireNonNull(results);
    }

    public List<PokemonExcerptResponse> getResults() {
        return results;
    }

    public void setResults(List<PokemonExcerptResponse> results) {
        this.results = requireNonNull(results);
    }

    public static class PokemonExcerptResponse {

        @JsonProperty("name")
        private String name;

        @JsonProperty("url")
        private String url;

        public PokemonExcerptResponse() {
        }

        public PokemonExcerptResponse(String name, String url) {
            this.name = requireNonNull(name);
            this.url = requireNonNull(url);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = requireNonNull(name);
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = requireNonNull(url);
        }
    }
}
