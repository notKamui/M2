package fr.uge.jee.hibernate.school.comment;

import fr.uge.jee.hibernate.school.core.CrudRepository;
import java.util.UUID;

public class CommentRepository extends CrudRepository<Comment, UUID> {

    private final static CommentRepository INSTANCE = new CommentRepository();

    private CommentRepository() {
    }

    public static CommentRepository instance() {
        return INSTANCE;
    }

    @Override
    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }
}
