package fr.uge.jee.hibernate.school.lecture;

import fr.uge.jee.hibernate.core.IdEntity;
import fr.uge.jee.hibernate.school.student.Student;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "Lectures")
public class Lecture implements IdEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column
    @ManyToMany(mappedBy = "lectures")
    private List<Student> students;

    public Lecture() {
    }

    public Lecture(String name, List<Student> students) {
        this.name = name;
        setStudents(students);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        requireNonNull(student);

        if (students.contains(student)) {
            return;
        }

        students.add(student);
        student.addLecture(this);
    }

    public void removeStudent(Student student) {
        requireNonNull(student);

        if (!students.contains(student)) {
            return;
        }

        students.remove(student);
        student.removeLecture(this);
    }
}
