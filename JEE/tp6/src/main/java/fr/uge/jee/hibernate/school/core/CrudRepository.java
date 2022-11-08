package fr.uge.jee.hibernate.school.core;

import fr.uge.jee.hibernate.util.DatabaseUtils;

import java.util.Objects;

import static fr.uge.jee.hibernate.util.DatabaseUtils.transaction;
import static java.util.Objects.requireNonNull;

public abstract class CrudRepository<E extends IdEntity<Id>, Id> {

    protected abstract Class<E> getEntityClass();

    public Id create(E entity) {
        requireNonNull(entity);

        try {
            return transaction(em -> {
                em.persist(entity);
                return entity.getId();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(Id id) {
        requireNonNull(id);

        try {
            return transaction(em -> {
                var query = em
                    .createQuery("DELETE FROM " + getEntityClass().getName() + " e WHERE e.id = :id")
                    .setParameter("id", id);
                return query.executeUpdate() > 0;
            });
        } catch (Exception e) {
            return false;
        }
    }
}