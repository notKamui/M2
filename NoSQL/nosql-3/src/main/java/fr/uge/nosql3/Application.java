package fr.uge.nosql3;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.conversions.Bson;
import org.bson.json.JsonObject;

import static com.mongodb.client.model.Filters.eq;

public class Application {

    private final MongoClient client;

    private Application(MongoClient client) {
        this.client = client;
    }

    public void run() {
        try {
            var moviesdb = client.getDatabase("movies");
            var movies = moviesdb.getCollection("movies");
            System.out.println(movies.find().first());
            System.out.println(movies.countDocuments());
            movies.find(new JsonObject("{release_year: 1995}")).forEach(System.out::println);
        } finally {
            client.close();
        }
    }

    public static void main(String[] args) {
        var client = MongoClients.create("mongodb://root:root@localhost:27017/");
        var app = new Application(client);
        app.run();
    }
}
