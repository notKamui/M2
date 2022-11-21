package fr.uge.jee.hibernate.school.lecture;

import fr.uge.jee.hibernate.core.CrudRepository;
import fr.uge.jee.hibernate.school.student.Student;

import java.util.Set;
import java.util.UUID;

import static fr.uge.jee.hibernate.util.DatabaseUtils.transaction;

public class LectureRepository implements CrudRepository<Lecture, UUID> {

    private final static LectureRepository INSTANCE = new LectureRepository();

    private LectureRepository() {
    }

    public static LectureRepository instance() {
        return INSTANCE;
    }

    @Override
    public Class<Lecture> getEntityClass() {
        return Lecture.class;
    }

    public Set<Student> getAttendees(UUID lectureId) {
        try {
            return transaction(em -> {
                var query = em
                    .createQuery("SELECT s FROM Student s JOIN s.lectures l WHERE l.id = :lectureId", Student.class)
                    .setParameter("lectureId", lectureId);

                return Set.copyOf(query.getResultList());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
