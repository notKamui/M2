package fr.uge.jee.hibernate.school.comment;

import fr.uge.jee.hibernate.school.core.IdEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Comments")
public class Comment implements IdEntity<UUID> {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "content")
    private String content;

    public Comment() {
    }

    public Comment(String content) {
        this.content = content;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }
}
