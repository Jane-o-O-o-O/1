package com.ruoyi.project.employment.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.web.domain.BaseEntity;

public class EmploymentInterview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long interviewId;
    private Long applicationId;
    private Long jobId;
    private Long studentUserId;
    private Long enterpriseUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date interviewTime;
    private String interviewType;
    private String interviewLocation;
    private String interviewStatus;
    private String studentFeedback;
    private String evaluation;
    private String studentName;
    private String jobName;

    public Long getInterviewId() { return interviewId; }
    public void setInterviewId(Long interviewId) { this.interviewId = interviewId; }
    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }
    public Long getStudentUserId() { return studentUserId; }
    public void setStudentUserId(Long studentUserId) { this.studentUserId = studentUserId; }
    public Long getEnterpriseUserId() { return enterpriseUserId; }
    public void setEnterpriseUserId(Long enterpriseUserId) { this.enterpriseUserId = enterpriseUserId; }
    public Date getInterviewTime() { return interviewTime; }
    public void setInterviewTime(Date interviewTime) { this.interviewTime = interviewTime; }
    public String getInterviewType() { return interviewType; }
    public void setInterviewType(String interviewType) { this.interviewType = interviewType; }
    public String getInterviewLocation() { return interviewLocation; }
    public void setInterviewLocation(String interviewLocation) { this.interviewLocation = interviewLocation; }
    public String getInterviewStatus() { return interviewStatus; }
    public void setInterviewStatus(String interviewStatus) { this.interviewStatus = interviewStatus; }
    public String getStudentFeedback() { return studentFeedback; }
    public void setStudentFeedback(String studentFeedback) { this.studentFeedback = studentFeedback; }
    public String getEvaluation() { return evaluation; }
    public void setEvaluation(String evaluation) { this.evaluation = evaluation; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getJobName() { return jobName; }
    public void setJobName(String jobName) { this.jobName = jobName; }
}
