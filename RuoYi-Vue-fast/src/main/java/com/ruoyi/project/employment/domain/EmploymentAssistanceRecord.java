package com.ruoyi.project.employment.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.web.domain.BaseEntity;

public class EmploymentAssistanceRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long recordId;
    private Long studentUserId;
    private Long helperUserId;
    private String helperRole;
    private String activityTitle;
    private String activityType;
    private String activityContent;
    private String resultSummary;
    private String nextPlan;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;
    private String studentName;
    private String helperName;

    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public Long getStudentUserId() { return studentUserId; }
    public void setStudentUserId(Long studentUserId) { this.studentUserId = studentUserId; }
    public Long getHelperUserId() { return helperUserId; }
    public void setHelperUserId(Long helperUserId) { this.helperUserId = helperUserId; }
    public String getHelperRole() { return helperRole; }
    public void setHelperRole(String helperRole) { this.helperRole = helperRole; }
    public String getActivityTitle() { return activityTitle; }
    public void setActivityTitle(String activityTitle) { this.activityTitle = activityTitle; }
    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }
    public String getActivityContent() { return activityContent; }
    public void setActivityContent(String activityContent) { this.activityContent = activityContent; }
    public String getResultSummary() { return resultSummary; }
    public void setResultSummary(String resultSummary) { this.resultSummary = resultSummary; }
    public String getNextPlan() { return nextPlan; }
    public void setNextPlan(String nextPlan) { this.nextPlan = nextPlan; }
    public Date getRecordTime() { return recordTime; }
    public void setRecordTime(Date recordTime) { this.recordTime = recordTime; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getHelperName() { return helperName; }
    public void setHelperName(String helperName) { this.helperName = helperName; }
}
