package fr.uge.jee.hibernate.school.core;

import java.util.Optional;
import java.util.function.Consumer;

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

    public Optional<E> findById(Id id) {
        requireNonNull(id);

        try {
            return transaction(em -> {
                return Optional.ofNullable(em.find(getEntityClass(), id));
            });
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean update(Id id, E entity) {
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

    public boolean update(Id id, Consumer<E> apply) {
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