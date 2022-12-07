package fr.uge.jee.hibernate.cinema.viewer;

import fr.uge.jee.hibernate.core.CrudRepository;

public class ViewerRepository implements CrudRepository<Viewer, Long> {

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