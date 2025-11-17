package org.example.dao;

import org.example.model.MongoUserProfile;

public interface MongoUserProfileDAO {
    void saveUserProfile(MongoUserProfile profile);
}
