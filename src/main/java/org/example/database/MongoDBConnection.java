package org.example.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    private static final String CONNECTION_URI = "mongodb://localhost:27017";
    private static MongoClient client = null;

    private MongoDBConnection() {}

    public static MongoDatabase getDatabase() {
        if (client == null) {
            client = MongoClients.create(CONNECTION_URI);
        }
        return client.getDatabase("media_app");
    }

    public static MongoClient getClient() {
        if (client == null) {
            client = MongoClients.create(CONNECTION_URI);
        }
        return client;
    }
}
