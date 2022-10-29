package fr.uge.jee.springmvc.pokematch.web.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pokemon {

    @JsonProperty("id")
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sprite_url")
    private String spriteUrl;

    @JsonIgnore
    private Integer hashName = null;

    @JsonIgnore
    private String idString = null;

    public Pokemon() {
    }

    public Pokemon(long id, String name, String spriteUrl) {
        this.id = id;
        this.name = name;
        this.spriteUrl = spriteUrl;
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
        this.name = name;
    }

    public String getSpriteUrl() {
        return spriteUrl;
    }

    public void setSpriteUrl(String spriteUrl) {
        this.spriteUrl = spriteUrl;
    }

    public int hashName() {
        if (hashName == null) {
            hashName = name.hashCode();
        }
        return hashName;
    }

    public String idString() {
        if (idString == null) {
            idString = String.format("%04d", id);
        }
        return idString;
    }
}
