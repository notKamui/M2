package fr.uge;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class Application {

    private final DrugRepository repository;

    private Application(JedisPool pool, Connection db) {
        this.repository = new DrugRepository(pool, db);
    }

    public static Application create() {
        try {
            var pool = new JedisPool(new JedisPoolConfig(), "localhost");
            var db = DriverManager.getConnection(
                "jdbc:postgresql://sqletud.u-pem.fr/jimmy.teillard_db",
                "jimmy.teillard",
                "potato"
            );
            return new Application(pool, db);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(String[] args) {
        repository.cacheFlush();
        repository.add1000EntriesFromBDPMCISCIP2();

        var begin = System.currentTimeMillis();
        repository.get(4949729) // in cache
            .ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Drug with id 4949729 not found")
            );
        var end = System.currentTimeMillis();
        System.out.println("Time to get 4949729: " + (end - begin) + "ms");

        begin = System.currentTimeMillis();
        repository.get(3617161) // not in cache
            .ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Drug with id 3617161 not found")
            );
        end = System.currentTimeMillis();
        System.out.println("Time to get 3617161: " + (end - begin) + "ms");

        begin = System.currentTimeMillis();
        repository.get(0) // not found
            .ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Drug with id 0000000 not found")
            );
        end = System.currentTimeMillis();
        System.out.println("Time to get 0000000: " + (end - begin) + "ms");

        repository.finish();
    }


    public static void main(String[] args) {
        Application.create().run(args);
    }
}
