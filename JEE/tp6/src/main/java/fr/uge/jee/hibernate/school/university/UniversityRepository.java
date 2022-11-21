package fr.uge.jee.hibernate.school.university;

import fr.uge.jee.hibernate.core.CrudRepository;

import java.util.UUID;

public class UniversityRepository implements CrudRepository<University, UUID> {

    private final static UniversityRepository INSTANCE = new UniversityRepository();

    private UniversityRepository() {
    }

    public static UniversityRepository instance() {
        return INSTANCE;
    }

    @Override
    public Class<University> getEntityClass() {
        return University.class;
    }
}
