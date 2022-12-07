package fr.uge.jee.hibernate.cinema.video;

import fr.uge.jee.hibernate.cinema.vote.Vote;
import fr.uge.jee.hibernate.core.IdEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "Videos")
public class Video implements IdEntity<Long> {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String title;

    @Column
    @OneToMany(mappedBy = "video")
    private Set<Vote> votes;

    public Video() {
    }

    public Video(String title, Set<Vote> votes) {
        requireNonNull(title);
        requireNonNull(votes);

        this.title = title;
        this.votes = new HashSet<>();
        for (Vote vote : votes) {
            addVote(vote);
        }
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        requireNonNull(title);
        this.title = title;
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

        var oldVote = votes.stream().filter(v -> vote.getViewer().getNickname().equals(v.getViewer().getNickname())).findFirst();
        oldVote.ifPresent(votes::remove);
        votes.add(vote);
        vote.setMovie(this);
    }
}
