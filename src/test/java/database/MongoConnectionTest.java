package database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.example.database.MongoDBConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MongoConnectionTest {

    @Test
    void testMongoConnectionNotNull() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        Assertions.assertNotNull(db, "MongoDB database should not be null");
    }

    @Test
    void testDatabaseName() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        Assertions.assertEquals("media_app", db.getName());  // ✅ FIXED

    }

    @Test
    void testMongoClientSingleton() {
        MongoClient c1 = MongoDBConnection.getClient();
        MongoClient c2 = MongoDBConnection.getClient();

        Assertions.assertSame(c1, c2, "MongoClient must be singleton");
    }

    @Test
    void testListCollections() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        var collections = db.listCollectionNames().into(new java.util.ArrayList<>());

        Assertions.assertNotNull(collections, "Collections list must not be null");
    }
}
