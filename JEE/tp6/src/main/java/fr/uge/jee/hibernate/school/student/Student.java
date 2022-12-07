package fr.uge.jee.hibernate.school.student;

import fr.uge.jee.hibernate.core.IdEntity;
import fr.uge.jee.hibernate.school.comment.Comment;
import fr.uge.jee.hibernate.school.lecture.Lecture;
import fr.uge.jee.hibernate.school.university.University;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "Students")
public class Student implements IdEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Embedded
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    private University university;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private List<Comment> comments;

    @ManyToMany
    private Set<Lecture> lectures;

    public Student() {
    }

    public Student(String firstName, String lastName, Address address, University university, List<Comment> comments, Set<Lecture> lectures) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.university = university;
        this.comments = comments;
        setLectures(lectures);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
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
        requireNonNull(lectures);

        this.lectures = new HashSet<>();
        for (Lecture lecture : lectures) {
            addLecture(lecture);
        }
    }

    public void addLecture(Lecture lecture) {
        requireNonNull(lecture);

        if (lectures.contains(lecture)) {
            return;
        }

        lectures.add(lecture);
    }

    public void removeLecture(Lecture lecture) {
        requireNonNull(lecture);

        lectures.remove(lecture);
    }
}
