package fr.uge.jee.hibernate.cinema.movie;

import fr.uge.jee.hibernate.cinema.viewer.Viewer;
import fr.uge.jee.hibernate.cinema.vote.Vote;
import fr.uge.jee.hibernate.cinema.vote.VoteRepository;
import fr.uge.jee.hibernate.core.CrudRepository;
import fr.uge.jee.hibernate.util.DatabaseUtils;

import java.util.Objects;
import java.util.UUID;

import static fr.uge.jee.hibernate.util.DatabaseUtils.transaction;
import static java.util.Objects.requireNonNull;

public class MovieRepository implements CrudRepository<Movie, UUID> {

    private final static MovieRepository INSTANCE = new MovieRepository();

    private MovieRepository() {
    }

    public static MovieRepository instance() {
        return INSTANCE;
    }

    @Override
    public Class<Movie> getEntityClass() {
        return Movie.class;
    }

    public boolean addUpvote(Movie movie, Viewer viewer) {
        requireNonNull(movie);
        requireNonNull(viewer);

        var vote = new Vote(Vote.Type.UPVOTE, movie, viewer);
        VoteRepository.instance().create(vote);
        movie.addVote(vote);
        return true;
    }

    public boolean addDownvote(Movie movie, Viewer viewer) {
        requireNonNull(movie);
        requireNonNull(viewer);

        var vote = new Vote(Vote.Type.DOWNVOTE, movie, viewer);
        VoteRepository.instance().create(vote);
        movie.addVote(vote);
        return true;
    }

    public long getScore(Movie movie) {
        requireNonNull(movie);

        try {
            return transaction(em -> {
                var query = em
                    .createQuery("SELECT SUM(v.type.value) FROM Vote v WHERE v.movie = :movie", Long.class)
                    .setParameter("movie", movie);
                return query.getSingleResult();

            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}