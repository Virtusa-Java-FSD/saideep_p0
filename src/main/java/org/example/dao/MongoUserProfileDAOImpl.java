package org.example.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.database.MongoDBConnection;
import org.example.model.MongoUserProfile;
import org.example.util.LoggerConfig;

public class MongoUserProfileDAOImpl implements MongoUserProfileDAO {

    private final MongoCollection<Document> collection;

    public MongoUserProfileDAOImpl() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        this.collection = db.getCollection("user_profiles");
    }

    @Override
    public void saveUserProfile(MongoUserProfile profile) {
        Document doc = new Document()
                .append("userId", profile.getUserId())
                .append("username", profile.getUsername())
                .append("email", profile.getEmail())
                .append("emailVerified", profile.isEmailVerified())
                .append("backupTime", System.currentTimeMillis());

        collection.insertOne(doc);
        LoggerConfig.logger.info("📌 Profile synced to MongoDB for {}", profile.getUsername());
    }
}
