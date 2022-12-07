package fr.uge.jee.hibernate.school.university;

import fr.uge.jee.hibernate.core.CrudRepository;

public class UniversityRepository implements CrudRepository<University, Long> {

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
