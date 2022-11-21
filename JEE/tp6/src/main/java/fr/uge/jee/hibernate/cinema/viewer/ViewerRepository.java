package fr.uge.jee.hibernate.cinema.viewer;

import fr.uge.jee.hibernate.cinema.vote.Vote;
import fr.uge.jee.hibernate.core.CrudRepository;

import java.util.UUID;

public class ViewerRepository implements CrudRepository<Viewer, UUID> {

    private final static ViewerRepository INSTANCE = new ViewerRepository();

    private ViewerRepository() {
    }

    public static ViewerRepository instance() {
        return INSTANCE;
    }

    @Override
    public Class<Viewer> getEntityClass() {
        return Viewer.class;
    }

}