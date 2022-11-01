package fr.uge.jee.springmvc.pokematch.web.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonExcerptListResponse {

    @JsonProperty("results")
    private List<PokemonExcerptResponse> results;

    public PokemonExcerptListResponse() {
    }

    public PokemonExcerptListResponse(List<PokemonExcerptResponse> results) {
        this.results = results;
    }

    public List<PokemonExcerptResponse> getResults() {
        return results;
    }

    public void setResults(List<PokemonExcerptResponse> results) {
        this.results = results;
    }
}
