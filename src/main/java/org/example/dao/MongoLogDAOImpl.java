package org.example.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.database.MongoDBConnection;
import java.time.LocalDateTime;

public class MongoLogDAOImpl implements MongoLogDAO {

    private final MongoCollection<Document> collection;

    public MongoLogDAOImpl() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        collection = database.getCollection("activity_logs");
    }

    @Override
    public void saveLog(String level, String message, String username) {
        Document log = new Document()
                .append("level", level)
                .append("message", message)
                .append("username", username)
                .append("timestamp", LocalDateTime.now().toString());
        collection.insertOne(log);
    }
}
