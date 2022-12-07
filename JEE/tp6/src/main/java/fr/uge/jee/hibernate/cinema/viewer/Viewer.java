package fr.uge.jee.hibernate.cinema.viewer;

import fr.uge.jee.hibernate.core.IdEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "Viewers")
public class Viewer implements IdEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private String nickname;

    public Viewer() {
    }

    public Viewer(String nickname) {
        requireNonNull(nickname);

        this.nickname = nickname;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        requireNonNull(nickname);

        this.nickname = nickname;
    }

}
