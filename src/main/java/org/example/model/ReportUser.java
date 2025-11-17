package org.example.model;

import java.sql.Timestamp;

public class ReportUser {

    private int reportId;
    private int reporterId;
    private int reportedId;
    private String reason;
    private Timestamp reportTime;

    public ReportUser() {}

    public ReportUser(int reportId, int reporterId, int reportedId, String reason, Timestamp reportTime) {
        this.reportId = reportId;
        this.reporterId = reporterId;
        this.reportedId = reportedId;
        this.reason = reason;
        this.reportTime = reportTime;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }

    public int getReportedId() {
        return reportedId;
    }

    public void setReportedId(int reportedId) {
        this.reportedId = reportedId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Timestamp getReportTime() {
        return reportTime;
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }

    @Override
    public String toString() {
        return "ReportUser{" +
                "reportId=" + reportId +
                ", reporterId=" + reporterId +
                ", reportedId=" + reportedId +
                ", reason='" + reason + '\'' +
                ", reportTime=" + reportTime +
                '}';
    }
}
