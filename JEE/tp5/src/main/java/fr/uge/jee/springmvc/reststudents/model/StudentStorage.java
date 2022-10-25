package fr.uge.jee.springmvc.reststudents.model;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class StudentStorage {

    private final Map<Long, Student> students = Map.of(
            1L, new Student(1L, "John", "Doe"),
            2L, new Student(2L, "Jane", "Doe"),
            3L, new Student(3L, "John", "Smith"),
            4L, new Student(4L, "Jane", "Smith")
    );

    public Optional<Student> getById(long id) {
        return Optional.ofNullable(students.get(id));
    }

    public List<Student> all() {
        return List.copyOf(students.values());
    }
}
