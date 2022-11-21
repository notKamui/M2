package fr.uge.jee.hibernate.cinema.vote;

import fr.uge.jee.hibernate.cinema.movie.Movie;
import fr.uge.jee.hibernate.cinema.viewer.Viewer;
import fr.uge.jee.hibernate.core.IdEntity;

import javax.persistence.*;
import java.util.UUID;

import static java.util.Objects.requireNonNull;


@Entity
@Table(name = "Votes")
public class Vote implements IdEntity<UUID> {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    private Movie movie;

    @ManyToOne
    private Viewer viewer;

    public Vote() {
    }

    public Vote(Type type, Movie movie, Viewer viewer) {
        requireNonNull(type);
        requireNonNull(movie);
        requireNonNull(viewer);

        this.type = type;
        this.movie = movie;
        this.viewer = viewer;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID uuid) {
        requireNonNull(uuid);

        this.id = uuid;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        requireNonNull(type);

        this.type = type;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        requireNonNull(movie);

        this.movie = movie;
    }

    public Viewer getViewer() {
        return viewer;
    }

    public void setViewer(Viewer viewer) {
        requireNonNull(viewer);

        this.viewer = viewer;
    }

    public enum Type {
        UPVOTE(1),
        DOWNVOTE(-1) ;

        private int value;

        Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

}