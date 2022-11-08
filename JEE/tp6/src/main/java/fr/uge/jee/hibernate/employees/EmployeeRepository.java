package fr.uge.jee.hibernate.employees;

import fr.uge.jee.hibernate.util.DatabaseUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static fr.uge.jee.hibernate.util.DatabaseUtils.transaction;
import static java.util.Objects.requireNonNull;

public class EmployeeRepository {

    /**
     * Create an employee with the given information
     *
     * @param firstName the first name of the employee
     * @param lastName  the last name of the employee
     * @param salary    the salary of the employee
     * @return the id of the newly created employee
     */
    public long create(String firstName, String lastName, int salary) {
        requireNonNull(firstName);
        requireNonNull(lastName);
        if (salary < 0) throw new IllegalArgumentException("Salary must be positive");

        var employee = new Employee(firstName, lastName, salary);
        try {
            transaction(em -> {
                em.persist(employee);
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return employee.getId();
    }

    /**
     * Remove the employee with the given id from the DB
     *
     * @param id the id of the employee to remove
     * @return true if the removal was successful
     */
    public boolean delete(long id) {
        try {
            return transaction(em -> {
                var query = em
                    .createQuery("DELETE FROM Employee e WHERE e.id = :id")
                    .setParameter("id", id);
                return query.executeUpdate() > 0;
            });
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Update the salary of the employee with the given id
     *
     * @param id     the id of the employee to update
     * @param salary the new salary of the employee
     * @return true if the update was successful
     */
    public boolean update(long id, int salary) {
        if (salary < 0) throw new IllegalArgumentException("Salary must be positive");

        try {
            return transaction(em -> {
                var query = em
                    .createQuery("UPDATE Employee e SET e.salary = :salary WHERE e.id = :id")
                    .setParameter("id", id)
                    .setParameter("salary", salary);
                return query.executeUpdate() > 0;
            });
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Updates the salary of the employee with the given id
     *
     * @param id    the id of the employee to update
     * @param apply the function to apply to the salary
     * @return true if the update was successful
     */
    public boolean updateSalary(long id, Function<Integer, Integer> apply) {
        requireNonNull(apply);

        try {
            return transaction(em -> {
                var employee = em.find(Employee.class, id);
                if (employee == null) return false;
                var newSalary = apply.apply(employee.getSalary());
                if (newSalary < 0) throw new IllegalArgumentException("Salary must be positive");
                employee.setSalary(newSalary);
                return true;
            });
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Retrieve the employee with the given id
     *
     * @param id the id of the employee to retrieve
     * @return the employee wrapped in an {@link Optional}
     */
    public Optional<Employee> get(long id) {
        try {
            return transaction(em -> {
                return Optional.ofNullable(em.find(Employee.class, id));
            });
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Return the list of the employee in the DB
     */
    public List<Employee> getAll() {
        try {
            return transaction(em -> {
                var query = em.createQuery("SELECT e FROM Employee e", Employee.class);
                return query.getResultList();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the list of employees with a given first name
     *
     * @param firstName the first name of the employees to retrieve
     * @return the list of employees with the given first name
     */
    public List<Employee> getAllByFirstName(String firstName) {
        requireNonNull(firstName);

        try {
            return transaction(em -> {
                var query = em
                    .createQuery("SELECT e FROM Employee e WHERE e.firstName = :firstName", Employee.class)
                    .setParameter("firstName", firstName);
                return query.getResultList();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
