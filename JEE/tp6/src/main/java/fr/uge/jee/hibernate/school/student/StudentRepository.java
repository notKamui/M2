package fr.uge.jee.hibernate.school.student;

import fr.uge.jee.hibernate.school.comment.Comment;
import fr.uge.jee.hibernate.school.comment.CommentRepository;
import fr.uge.jee.hibernate.school.core.CrudRepository;
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
