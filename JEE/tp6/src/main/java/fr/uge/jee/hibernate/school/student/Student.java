package fr.uge.jee.hibernate.school.student;

import fr.uge.jee.hibernate.school.comment.Comment;
import fr.uge.jee.hibernate.school.core.IdEntity;
import fr.uge.jee.hibernate.school.lecture.Lecture;
import fr.uge.jee.hibernate.school.university.University;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "Students")
public class Student implements IdEntity<UUID> {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Embedded
    private Address address;

    @ManyToOne
    private University university;

    @OneToMany
    private List<Comment> comments;

    @OneToMany
    private Set<Lecture> lectures;

    public Student() {
    }

    public Student(String firstName, String lastName, Address address, University university, List<Comment> comments, Set<Lecture> lectures) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.university = university;
        this.comments = comments;
        this.lectures = lectures;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }
}
