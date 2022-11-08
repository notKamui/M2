package fr.uge.jee.hibernate.school.university;

import fr.uge.jee.hibernate.school.core.IdEntity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Universities")
public class University implements IdEntity<UUID> {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    public University() {
    }

    public University(String name) {
        this.name = name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
