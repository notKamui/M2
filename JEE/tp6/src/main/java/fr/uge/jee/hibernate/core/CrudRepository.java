package fr.uge.jee.hibernate.core;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

import static fr.uge.jee.hibernate.util.DatabaseUtils.transaction;
import static java.util.Objects.requireNonNull;

public interface CrudRepository<E extends IdEntity<Id>, Id extends Serializable> {

    Class<E> getEntityClass();

    default Id create(E entity) {
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

    default Optional<E> findById(Id id) {
        requireNonNull(id);

        try {
            return transaction(em -> {
                return Optional.ofNullable(em.find(getEntityClass(), id));
            });
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    default Set<E> findAll() {
        try {
            return transaction(em -> {
                var query = em
                    .createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e", getEntityClass());

                return Set.copyOf(query.getResultList());
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    default boolean update(Id id, E entity) {
        requireNonNull(id);
        requireNonNull(entity);

        try {
            return transaction(em -> {
                var query = em
                    .createQuery("UPDATE " + getEntityClass().getSimpleName() + " e SET e = :entity WHERE e.id = :id")
                    .setParameter("entity", entity)
                    .setParameter("id", id);

                return query.executeUpdate() > 0;
            });
        } catch (Exception e) {
            return false;
        }
    }

    default boolean update(Id id, Consumer<E> apply) {
        requireNonNull(id);
        requireNonNull(apply);

        try {
            return transaction(em -> {
                var entity = em.find(getEntityClass(), id);
                if (entity == null) {
                    return false;
                }

                apply.accept(entity);
                return true;
            });
        } catch (Exception e) {
            return false;
        }
    }

    default boolean delete(Id id) {
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