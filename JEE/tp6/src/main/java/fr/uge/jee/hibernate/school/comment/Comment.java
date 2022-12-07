package fr.uge.jee.hibernate.school.comment;

import fr.uge.jee.hibernate.core.IdEntity;
import fr.uge.jee.hibernate.school.student.Student;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "Comments")
public class Comment implements IdEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    public Comment() {
    }

    public Comment(
        String content,
        Student student
    ) {
        this.content = content;
        this.student = student;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        requireNonNull(content);
        this.content = content;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        requireNonNull(student);
        this.student = student;
    }
}
