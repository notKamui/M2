package fr.uge.jee.pokevote;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Pokemons")
public class Pokemon {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private long score;

    @Version
    private long version;

    public Pokemon() {
    }

    public Pokemon(String name) {
        Objects.requireNonNull(name);

        this.name = name;
        this.score = 0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        Objects.requireNonNull(name);

        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        if (score < 0) {
            throw new IllegalArgumentException("Score must be positive");
        }

        this.score = score;
    }
}
