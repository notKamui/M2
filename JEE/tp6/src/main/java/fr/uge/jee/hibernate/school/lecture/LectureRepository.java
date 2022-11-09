package fr.uge.jee.hibernate.school.lecture;

import fr.uge.jee.hibernate.school.core.CrudRepository;
import java.util.UUID;

public class LectureRepository extends CrudRepository<Lecture, UUID> {

    private final static LectureRepository INSTANCE = new LectureRepository();

    private LectureRepository() {
    }

    public static LectureRepository instance() {
        return INSTANCE;
    }

    @Override
    protected Class<Lecture> getEntityClass() {
        return Lecture.class;
    }
}
