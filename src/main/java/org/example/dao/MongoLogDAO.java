package org.example.dao;

public interface MongoLogDAO {
    void saveLog(String level, String message, String username);

}
