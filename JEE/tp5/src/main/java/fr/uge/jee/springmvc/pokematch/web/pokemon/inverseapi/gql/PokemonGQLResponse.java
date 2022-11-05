package fr.uge.jee.springmvc.pokematch.web.pokemon.inverseapi.gql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.uge.jee.springmvc.pokematch.web.pokemon.model.Pokemon;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class PokemonGQLResponse {

    private PokemonData data;
    private String sprites;

    public PokemonGQLResponse() {
    }

    public PokemonGQLResponse(PokemonData data, String sprites) {
        this.data = requireNonNull(data);
        this.sprites = requireNonNull(sprites);
    }

    public PokemonData getData() {
        return data;
    }

    public void setData(PokemonData data) {
        requireNonNull(data);
        this.data = data;
    }

    public String getSprites() {
        return sprites;
    }

    public void setSprites(String sprites) {
        requireNonNull(sprites);
        this.sprites = sprites;
    }

    public Pokemon toPokemon(ObjectMapper objectMapper) {
        requireNonNull(objectMapper);
        try {
            var frontDefaultUrl = objectMapper.readValue(sprites, Map.class).get("front_default");
            return new Pokemon(
                data.getId(),
                data.getName(),
                frontDefaultUrl == null ? null : frontDefaultUrl.toString()
            );
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    static class PokemonData {
        private long id;
        private String name;

        public PokemonData() {
        }

        public PokemonData(Long id, String name) {
            this.id = requireNonNull(id);
            this.name = requireNonNull(name);
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
            requireNonNull(name);
            this.name = name;
        }
    }
}
