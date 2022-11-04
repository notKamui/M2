package fr.uge.jee.springmvc.pokematch.web.pokemon.inverseapi.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import fr.uge.jee.springmvc.pokematch.web.pokemon.model.Pokemon;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.*;
import static java.util.Objects.requireNonNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PokemonResponse {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sprites")
    private Map<String, Object> sprites;

    public PokemonResponse() {
    }

    public PokemonResponse(long id, String name, Map<String, Object> sprites) {
        this.id = id;
        this.name = requireNonNull(name);
        this.sprites = requireNonNull(sprites);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = requireNonNull(name);
    }

    public Map<String, Object> getSprites() {
        return sprites;
    }

    public void setSprites(Map<String, Object> sprites) {
        this.sprites = requireNonNull(sprites);
    }

    public Pokemon toPokemon() {
        var sprite = sprites.get("front_default");
        return new Pokemon(
            id,
            name,
            sprite == null ? null : sprite.toString()
        );
    }
}
