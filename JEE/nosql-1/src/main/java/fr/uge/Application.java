package fr.uge;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class Application {

    private JedisPool pool;
    private Connection db;

    public Application() throws SQLException {
        pool = new JedisPool(new JedisPoolConfig(), "localhost");
        db = DriverManager.getConnection(
            "jdbc:postgresql://sqletud.u-pem.fr/jimmy.teillard_db",
            "jimmy.teillard",
            "potato"
        );
    }

    public void add1000EntriesFromBDPMCISCIP2(Jedis jedis) throws SQLException {
        var sql = "SELECT cip7, m2.bdpm_ciscip2.cis, denom " +
            "FROM m2.bdpm_ciscip2 INNER JOIN m2.bdpm_cis " +
            "ON m2.bdpm_ciscip2.cis = m2.bdpm_cis.cis " +
            "LIMIT 1000";
        var stmt = db.createStatement();
        var rs = stmt.executeQuery(sql);
        while (rs.next()) {
            var id = rs.getInt("cip7");
            var cis = rs.getString("cis");
            var name = rs.getString("denom");
            jedis.hmset("drug:" + id, Map.of(
                "cis", cis,
                "name", name
            ));
        }
    }

    public Map<String, String> getDrug(int cip7) throws SQLException {
        var sql = "SELECT cip7, m2.bdpm_ciscip2.cis, denom " +
            "FROM m2.bdpm_ciscip2 INNER JOIN m2.bdpm_cis " +
            "ON m2.bdpm_ciscip2.cis = m2.bdpm_cis.cis " +
            "WHERE cip7 = ?";
        var stmt = db.prepareStatement(sql);
        stmt.setInt(1, cip7);
        var rs = stmt.executeQuery();
        if (rs.next()) {
            var cis = rs.getString("cis");
            var name = rs.getString("denom");
            return Map.of(
                "cis", cis,
                "name", name
            );
        } else {
            return null;
        }
    }

    public void run(String[] args) throws SQLException {
        var begin = System.currentTimeMillis();
        var jedis = pool.getResource();
//        jedis.hmset("user:1", Map.of(
//            "id", "1",
//            "firstname", "John",
//            "lastname", "Doe"
//        ));
//        jedis.hmset("user:2", Map.of(
//            "id", "2",
//            "firstname", "Jane",
//            "lastname", "Doe"
//        ));
//        jedis.hmset("user:3", Map.of(
//            "id", "3",
//            "firstname", "Jack",
//            "lastname", "Doe"
//        ));
//
//        var users = jedis.keys("user:*");
//        for (var user : users) System.out.println(jedis.hgetAll(user));

//        add1000EntriesFromBDPMCISCIP2(jedis);
//        var drugs = jedis.keys("drug:*");
//        for (var drug : drugs) System.out.println(jedis.hgetAll(drug));

        var drug = jedis.hgetAll("drug:4949729");
        System.out.println(drug);

        if (jedis.exists("drug:0000000")) {
            System.out.println("Found in cache");
            System.out.println(jedis.hgetAll("drug:0000000"));
        } else {
            System.out.println("Not found in cache");
            var drugFromDB = getDrug(0);
            if (drugFromDB != null) {
                jedis.hmset("drug:0000000", drugFromDB);
                System.out.println(drugFromDB);
            } else {
                System.out.println("Not found in DB");
            }
        }

        db.close();
        pool.close();

        var end = System.currentTimeMillis();
        System.out.println("Time: " + (end - begin) + "ms");
    }


    public static void main(String[] args) throws SQLException {
        new Application().run(args);
    }
}
