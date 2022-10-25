package fr.uge.jee.springmvc.pokematch.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

class PokemonExcerptResponse {

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
