package fr.uge.jee.hibernate.school.university;

import fr.uge.jee.hibernate.school.core.CrudRepository;

import java.util.UUID;

public class UniversityRepository extends CrudRepository<University, UUID> {

    private final static UniversityRepository INSTANCE = new UniversityRepository();

    private UniversityRepository() {
    }

    public static UniversityRepository instance() {
        return INSTANCE;
    }

    @Override
    protected Class<University> getEntityClass() {
        return University.class;
    }
}
