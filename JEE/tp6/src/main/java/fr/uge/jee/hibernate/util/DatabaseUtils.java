package fr.uge.jee.hibernate.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Objects;

public final class DatabaseUtils {

    private DatabaseUtils() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("main-persistence-unit");

    @FunctionalInterface
    public interface Transaction<T> {
        T execute(EntityManager em) throws Exception;
    }

    @FunctionalInterface
    public interface TransactionVoid {
        void execute(EntityManager em) throws Exception;
    }

    public static <T> T transaction(Transaction<T> transactionBody) throws Exception {
        Objects.requireNonNull(transactionBody);

        var entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            var result = transactionBody.execute(entityManager);
            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    public static void transaction(TransactionVoid transactionBody) throws Exception {
        Objects.requireNonNull(transactionBody);

        var entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        var transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            transactionBody.execute(entityManager);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
