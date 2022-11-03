package fr.uge.jee.springmvc.pokematch.web.pokemon;

import java.util.Map;
import java.util.Objects;

public class PokemonGQLResponse {

    private PokemonData data;
    private Map<String, Object> sprites;

    public PokemonGQLResponse() {
    }

    public PokemonGQLResponse(PokemonData data, Map<String, Object> sprites) {
        this.data = data;
        this.sprites = sprites;
    }

    public PokemonData getData() {
        return data;
    }

    public void setData(PokemonData data) {
        Objects.requireNonNull(data);
        this.data = data;
    }

    public Map<String, Object> getSprites() {
        return sprites;
    }

    public void setSprites(Map<String, Object> sprites) {
        Objects.requireNonNull(sprites);
        this.sprites = sprites;
    }

    public Pokemon toPokemon() {
        var sprite = sprites.get("front_default");
        return new Pokemon(
            data.getId(),
            data.getName(),
            sprite == null ? null : sprite.toString()
        );
    }

    static class PokemonData {
        private Long id;
        private String name;

        public PokemonData() {
        }

        public PokemonData(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            Objects.requireNonNull(id);
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            Objects.requireNonNull(name);
            this.name = name;
        }
    }
}
