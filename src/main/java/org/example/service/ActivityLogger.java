package org.example.service;

import org.example.dao.MongoLogDAO;
import org.example.dao.MongoLogDAOImpl;

public class ActivityLogger {

    private final MongoLogDAO logDAO = new MongoLogDAOImpl();

    public void info(String message, String username) {
        logDAO.saveLog("INFO", message, username);
    }

    public void warn(String message, String username) {
        logDAO.saveLog("WARN", message, username);
    }

    public void error(String message, String username) {
        logDAO.saveLog("ERROR", message, username);
    }
}
