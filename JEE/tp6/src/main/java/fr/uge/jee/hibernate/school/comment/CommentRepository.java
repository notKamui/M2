package fr.uge.jee.hibernate.school.comment;

import fr.uge.jee.hibernate.core.CrudRepository;

public class CommentRepository implements CrudRepository<Comment, Long> {

    private final static CommentRepository INSTANCE = new CommentRepository();

    private CommentRepository() {
    }

    public static CommentRepository instance() {
        return INSTANCE;
    }

    @Override
    public Class<Comment> getEntityClass() {
        return Comment.class;
    }
}
