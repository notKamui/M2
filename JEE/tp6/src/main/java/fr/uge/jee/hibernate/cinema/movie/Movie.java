package fr.uge.jee.hibernate.cinema.movie;

import fr.uge.jee.hibernate.cinema.vote.Vote;
import fr.uge.jee.hibernate.core.IdEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "Movies")
public class Movie implements IdEntity<UUID> {

    @Id
    @GeneratedValue
    private UUID id;

    @Column
    @OneToMany(mappedBy = "movie")
    private Set<Vote> votes;

    public Movie() {
    }

    public Movie(Set<Vote> votes) {
        requireNonNull(votes);

        this.votes = new HashSet<>();
        for (Vote vote : votes) {
            addVote(vote);
        }
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        requireNonNull(id);

        this.id = id;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        requireNonNull(votes);

        this.votes = new HashSet<>();
        for (Vote vote : votes) {
            addVote(vote);
        }
    }

    public void addVote(Vote vote) {
        requireNonNull(vote);

        votes.add(vote);
        vote.setMovie(this);
    }
}
