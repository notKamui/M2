package fr.uge.jee.hibernate.cinema.movie;

import fr.uge.jee.hibernate.cinema.viewer.Viewer;
import fr.uge.jee.hibernate.cinema.vote.Vote;
import fr.uge.jee.hibernate.cinema.vote.VoteRepository;
import fr.uge.jee.hibernate.core.CrudRepository;

import static fr.uge.jee.hibernate.util.DatabaseUtils.transaction;
import static java.util.Objects.requireNonNull;

public class VideoRepository implements CrudRepository<Video, Long> {

    private final static VideoRepository INSTANCE = new VideoRepository();

    private VideoRepository() {
    }

    public static VideoRepository instance() {
        return INSTANCE;
    }

    @Override
    public Class<Video> getEntityClass() {
        return Video.class;
    }

    public boolean addUpvote(Video video, Viewer viewer) {
        requireNonNull(video);
        requireNonNull(viewer);

        var vote = new Vote(Vote.UPVOTE, video, viewer);
        VoteRepository.instance().create(vote);
        video.addVote(vote);
        return true;
    }

    public boolean addDownvote(Video video, Viewer viewer) {
        requireNonNull(video);
        requireNonNull(viewer);

        var vote = new Vote(Vote.DOWNVOTE, video, viewer);
        VoteRepository.instance().create(vote);
        video.addVote(vote);
        return true;
    }

    public long getScore(Video video) {
        requireNonNull(video);

        try {
            return transaction(em -> {
                var query = em
                    .createQuery("SELECT SUM(v.type) FROM Vote v WHERE v.video = :video", Long.class)
                    .setParameter("video", video);
                return query.getSingleResult();

            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}