package fr.uge.jee.springmvc.reststudents.service;

import fr.uge.jee.springmvc.reststudents.model.Student;
import fr.uge.jee.springmvc.reststudents.model.StudentStorage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@Service
public class StudentService {

    private final StudentStorage storage;

    public StudentService(StudentStorage storage) {
        this.storage = storage;
    }

    public Student getById(long id) {
        return storage
                .getById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }

    public List<Student> all() {
        return storage.all();
    }
}
