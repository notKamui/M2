package fr.uge;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class DrugRepository {

    private final JedisPool pool;
    private final Connection db;

    public DrugRepository(JedisPool pool, Connection db) {
        Objects.requireNonNull(pool);
        Objects.requireNonNull(db);
        this.pool = pool;
        this.db = db;
    }

    private static final int CACHE_MAX_SIZE = 1000 * 2;

    private static final String CIP7 = "cip7";
    private static final String CIS = "cis";
    private static final String NAME = "name";

    private static final String GET_ALL = "SELECT cip7, m2.bdpm_ciscip2.cis, denom " +
        "FROM m2.bdpm_ciscip2 INNER JOIN m2.bdpm_cis " +
        "ON m2.bdpm_ciscip2.cis = m2.bdpm_cis.cis ";

    private static final String GET_1000 = GET_ALL + "LIMIT 1000";
    private static final String GET_BY_CIP7 = GET_ALL + "WHERE cip7 = ?";

    public void finish() {
        try {
            pool.close();
            db.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cacheFlush() {
        try (var jedis = pool.getResource()) {
            jedis.flushAll();
        }
    }

    public void add1000EntriesFromBDPMCISCIP2() {
        try (var jedis = pool.getResource()) {
            var stmt = db.createStatement();
            var rs = stmt.executeQuery(GET_1000);
            while (rs.next()) {
                var id = rs.getInt("cip7");
                var cis = rs.getString("cis");
                var name = rs.getString("denom");
                jedis.hmset("drug:" + id, Map.of(
                    "cis", cis,
                    "name", name
                ));
                incrementResourceGet(jedis, id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Drug> get(int cip7) {
        try (var jedis = pool.getResource()) {
            var map = jedis.hgetAll(key(cip7));
            if (map.isEmpty()) {
//                System.out.println("Not found in cache");
                return Optional.ofNullable(onCacheMiss(jedis, cip7));
            }

            incrementResourceGet(jedis, cip7);
            return Optional.of(new Drug(
                cip7,
                Integer.parseInt(map.get(CIS)),
                map.get(NAME)
            ));
        }
    }

    private Drug onCacheMiss(Jedis jedis, int cip7) {
        try {
            var stmt = db.prepareStatement(GET_BY_CIP7);
            stmt.setInt(1, cip7);
            var rs = stmt.executeQuery();
            if (rs.next()) {
                int cis = rs.getInt("cis");
                var name = rs.getString("denom");
                var drug = new Drug(cip7, cis, name);
                cache(jedis, drug);
                return drug;
            } else {
//                System.out.println("Not found in database");
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void cache(Jedis jedis, Drug drug) {
        jedis.hmset(key(drug.cip7()), Map.of(
            CIS, String.valueOf(drug.cis()),
            NAME, drug.getName()
        ));
        incrementResourceGet(jedis, drug.cip7());
    }

    private void incrementResourceGet(Jedis jedis, int cip7) {
        purgeLeastGet(jedis);
        jedis.zadd("drug_get", 1.0, key(cip7));
    }

    private void purgeLeastGet(Jedis jedis) {
        if (jedis.dbSize() < CACHE_MAX_SIZE) return;

        var leastUsed = jedis.zrange("drug_get", 0, 0);
        if (leastUsed.isEmpty()) throw new IllegalStateException("No least used resource");
        var key = leastUsed.stream().findFirst().get();
        jedis.del(key);
        jedis.zrem("drug_get", key);
    }

    private static String key(int cip7) {
        return "drug:" + cip7;
    }
}
