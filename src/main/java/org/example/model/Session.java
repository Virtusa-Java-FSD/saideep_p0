package org.example.model;

import java.sql.Timestamp;

public class Session {

    private int sessionId;
    private int userId;
    private String sessionToken;
    private Timestamp loginTime;
    private Timestamp logoutTime;

    public Session() {}

    public Session(int sessionId, int userId, String sessionToken, Timestamp loginTime, Timestamp logoutTime) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.sessionToken = sessionToken;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public Timestamp getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Timestamp logoutTime) {
        this.logoutTime = logoutTime;
    }

    @Override
    public String toString() {
        return "Session{" +
                "sessionId=" + sessionId +
                ", userId=" + userId +
                ", sessionToken='" + sessionToken + '\'' +
                ", loginTime=" + loginTime +
                ", logoutTime=" + logoutTime +
                '}';
    }
}
