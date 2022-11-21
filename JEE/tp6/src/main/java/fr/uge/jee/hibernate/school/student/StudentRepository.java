package fr.uge.jee.hibernate.school.student;

import fr.uge.jee.hibernate.school.comment.Comment;
import fr.uge.jee.hibernate.school.comment.CommentRepository;
import fr.uge.jee.hibernate.school.core.CrudRepository;
import fr.uge.jee.hibernate.school.lecture.Lecture;
import fr.uge.jee.hibernate.school.university.UniversityRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class StudentRepository extends CrudRepository<Student, UUID> {

    private final static StudentRepository INSTANCE = new StudentRepository();

    private StudentRepository() {
    }

    public static StudentRepository instance() {
        return INSTANCE;
    }

    private final CommentRepository commentRepository = CommentRepository.instance();

    @Override
    protected Class<Student> getEntityClass() {
        return Student.class;
    }

    public boolean updateUniversity(UUID studentId, UUID universityId) {
        var university = UniversityRepository.instance().findById(universityId);
        if (university.isEmpty()) {
            return false;
        }
        return update(studentId, student -> student.setUniversity(university.get()));
    }

    public boolean updateAddress(UUID studentId, Address address) {
        return update(studentId, student -> student.setAddress(address));
    }

    public UUID addLecture(UUID userId, Lecture lecture) {
        update(userId, student -> student.getLectures().add(lecture));
        return lecture.getId();
    }

    public boolean removeLecture(UUID userId, UUID lectureId) {
        return update(userId, student -> student.getLectures().removeIf(lecture -> lecture.getId().equals(lectureId)));
    }

    public Optional<Set<Lecture>> getLectures(UUID userId) {
        return findById(userId).map(Student::getLectures);
    }

    public UUID addComment(UUID userId, String content) {
        var comment = new Comment(content);
        commentRepository.create(comment);
        update(userId, student -> student.getComments().add(comment));
        return comment.getId();
    }

    public boolean removeComment(UUID userId, UUID commentId) {
        var comment = commentRepository.findById(commentId);
        if (comment.isEmpty()) return false;
        return update(userId, student -> student.getComments().remove(comment.get()));
    }
}
