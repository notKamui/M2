package fr.uge.jee.hibernate.employees;

import fr.uge.jee.hibernate.employees.EmployeeRepository;

public final class Application {

    public static void main(String[] args) {
        var repo = new EmployeeRepository();

        var bobMoranId = repo.create("Bob", "Moran", 500);
        var bobDylanId = repo.create("Bob", "Dylan", 600);
        var lisaSimpsonId = repo.create("Lisa", "Simpson", 100);
        var margeSimpsonId = repo.create("Marge", "Simpson", 1000);
        var homerSimpsonId = repo.create("Homer", "Simpson", 450);

        repo.delete(lisaSimpsonId);

        repo.get(homerSimpsonId)
            .ifPresentOrElse(
                homer -> repo.update(homerSimpsonId, homer.getSalary() + 100),
                () -> System.out.println("Employee Homer not found")
            );

        repo.getAll().forEach(System.out::println);

        repo.getAll()
            .stream()
            .filter(e -> e.getSalary() <= 550)
            .forEach(e -> {
                repo.updateSalary(e.getId(), oldSalary -> oldSalary + 100);
                System.out.println(e);
            });

        repo.getAllByFirstName("Bob").forEach(System.out::println);
    }
}
