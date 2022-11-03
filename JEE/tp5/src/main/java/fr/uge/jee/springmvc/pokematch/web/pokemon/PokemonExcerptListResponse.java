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

    static class PokemonExcerptResponse {

        @JsonProperty("name")
        private String name;

        @JsonProperty("url")
        private String url;

        public PokemonExcerptResponse() {
        }

        public PokemonExcerptResponse(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "PokemonExcerptResponse{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
        }
    }
}
