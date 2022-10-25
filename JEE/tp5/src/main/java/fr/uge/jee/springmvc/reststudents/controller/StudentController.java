package fr.uge.jee.springmvc.reststudents.controller;

import fr.uge.jee.springmvc.reststudents.model.Student;
import fr.uge.jee.springmvc.reststudents.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Student getById(@PathVariable("id") long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Student> all() {
        return service.all();
    }
}
