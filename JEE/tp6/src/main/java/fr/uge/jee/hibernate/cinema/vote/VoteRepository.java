package fr.uge.jee.hibernate.cinema.vote;

import fr.uge.jee.hibernate.core.CrudRepository;

import java.util.UUID;

public class VoteRepository implements CrudRepository<Vote, UUID> {

    private final static VoteRepository INSTANCE = new VoteRepository();

    private VoteRepository() {
    }

    public static VoteRepository instance() {
        return INSTANCE;
    }

    @Override
    public Class<Vote> getEntityClass() {
        return Vote.class;
    }

}
