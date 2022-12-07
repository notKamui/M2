package fr.uge.jee.hibernate.school.university;

import fr.uge.jee.hibernate.core.IdEntity;

import fr.uge.jee.hibernate.school.student.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "Universities")
public class University implements IdEntity<UUID> {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column
    @OneToMany(mappedBy = "university")
    private List<Student> students;

    public University() {
    }

    public University(String name, List<Student> students) {
        this.name = name;
        setStudents(students);
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        requireNonNull(students);

        this.students = new ArrayList<>();
        for (Student student : students) {
            addStudent(student);
        }
    }

    public void addStudent(Student student) {
        requireNonNull(student);

        if (students.contains(student)) {
            return;
        }

        students.add(student);
        student.setUniversity(this);
    }

    public void removeStudent(Student student) {
        requireNonNull(student);

        students.remove(student);
        student.setUniversity(null);
    }
}
