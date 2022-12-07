package fr.uge.jee.hibernate.cinema.vote;

import fr.uge.jee.hibernate.cinema.movie.Video;
import fr.uge.jee.hibernate.cinema.viewer.Viewer;
import fr.uge.jee.hibernate.core.IdEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static java.util.Objects.requireNonNull;


@Entity
@Table(name = "Votes")
public class Vote implements IdEntity<Long> {

    public static final int UPVOTE = 1;
    public static final int DOWNVOTE = -1;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int type;

    @ManyToOne
    private Video video;

    @ManyToOne
    private Viewer viewer;

    public Vote() {
    }

    public Vote(int type, Video video, Viewer viewer) {
        requireNonNull(video);
        requireNonNull(viewer);
        if (type != -1 && type != 1) {
            throw new IllegalArgumentException("type must be -1 or 1");
        }

        this.type = type;
        this.video = video;
        this.viewer = viewer;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        requireNonNull(id);

        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        if (type != -1 && type != 1) {
            throw new IllegalArgumentException("type must be -1 or 1");
        }

        this.type = type;
    }

    public Video getMovie() {
        return video;
    }

    public void setMovie(Video video) {
        requireNonNull(video);

        this.video = video;
    }

    public Viewer getViewer() {
        return viewer;
    }

    public void setViewer(Viewer viewer) {
        requireNonNull(viewer);

        this.viewer = viewer;
    }

}