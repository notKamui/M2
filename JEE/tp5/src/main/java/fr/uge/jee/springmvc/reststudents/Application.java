package fr.uge.jee.springmvc.reststudents;

import fr.uge.jee.springmvc.reststudents.model.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo() {
        return (args) -> {
            var client = WebClient.create();
            var student = client
                    .get()
                    .uri("http://localhost:8080/students/1")
                    .retrieve()
                    .bodyToMono(Student.class)
                    .block();

            System.out.println(student);

            var students = client
                    .get()
                    .uri("http://localhost:8080/students")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Student>>() {})
                    .block();

            System.out.println(students);
        };
    }
}
