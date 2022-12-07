package fr.uge.jee.hibernate.school.student;

import fr.uge.jee.hibernate.core.CrudRepository;
import fr.uge.jee.hibernate.school.comment.Comment;
import fr.uge.jee.hibernate.school.comment.CommentRepository;
import fr.uge.jee.hibernate.school.lecture.Lecture;
import fr.uge.jee.hibernate.school.university.UniversityRepository;
import java.util.Optional;
import java.util.Set;

public class StudentRepository implements CrudRepository<Student, Long> {

    private final static StudentRepository INSTANCE = new StudentRepository();

    private StudentRepository() {
    }

    public static StudentRepository instance() {
        return INSTANCE;
    }

    private final CommentRepository commentRepository = CommentRepository.instance();

    @Override
    public Class<Student> getEntityClass() {
        return Student.class;
    }

    public boolean updateUniversity(Long studentId, Long universityId) {
        var university = UniversityRepository.instance().findById(universityId);
        return university.filter(value -> update(studentId, student -> student.setUniversity(value))).isPresent();
    }

    public boolean updateAddress(Long studentId, Address address) {
        return update(studentId, student -> student.setAddress(address));
    }

    public Long addLecture(Long userId, Lecture lecture) {
        update(userId, student -> student.getLectures().add(lecture));
        return lecture.getId();
    }

    public boolean removeLecture(Long userId, Long lectureId) {
        return update(userId, student -> student.getLectures().removeIf(lecture -> lecture.getId().equals(lectureId)));
    }

    public Optional<Set<Lecture>> getLectures(Long userId) {
        return findById(userId).map(Student::getLectures);
    }

    public Long addComment(Long userId, String content) {
        var commentWrapper = new Object() { Comment comment; };
        update(userId, student -> {
            commentWrapper.comment = new Comment(content, student);
            commentRepository.create(commentWrapper.comment);
            student.getComments().add(commentWrapper.comment);
        });
        return commentWrapper.comment.getId();
    }

    public boolean removeComment(Long userId, Long commentId) {
        var comment = commentRepository.findById(commentId);
        return comment.filter(value -> update(userId, student -> student.getComments().remove(value))).isPresent();
    }
}
