package com.ruoyi.project.employment.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.web.domain.BaseEntity;

public class EmploymentJobApplication extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long applicationId;
    private Long jobId;
    private Long studentUserId;
    private Long resumeId;
    private Long enterpriseUserId;
    private String applyStatus;
    private Double matchScore;
    private String progressRemark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appliedTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date feedbackTime;
    private String studentName;
    private String majorName;
    private String className;
    private String jobName;
    private String companyName;
    private String resumeTitle;
    private String skillTags;

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }
    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }
    public Long getStudentUserId() { return studentUserId; }
    public void setStudentUserId(Long studentUserId) { this.studentUserId = studentUserId; }
    public Long getResumeId() { return resumeId; }
    public void setResumeId(Long resumeId) { this.resumeId = resumeId; }
    public Long getEnterpriseUserId() { return enterpriseUserId; }
    public void setEnterpriseUserId(Long enterpriseUserId) { this.enterpriseUserId = enterpriseUserId; }
    public String getApplyStatus() { return applyStatus; }
    public void setApplyStatus(String applyStatus) { this.applyStatus = applyStatus; }
    public Double getMatchScore() { return matchScore; }
    public void setMatchScore(Double matchScore) { this.matchScore = matchScore; }
    public String getProgressRemark() { return progressRemark; }
    public void setProgressRemark(String progressRemark) { this.progressRemark = progressRemark; }
    public Date getAppliedTime() { return appliedTime; }
    public void setAppliedTime(Date appliedTime) { this.appliedTime = appliedTime; }
    public Date getFeedbackTime() { return feedbackTime; }
    public void setFeedbackTime(Date feedbackTime) { this.feedbackTime = feedbackTime; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getMajorName() { return majorName; }
    public void setMajorName(String majorName) { this.majorName = majorName; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getJobName() { return jobName; }
    public void setJobName(String jobName) { this.jobName = jobName; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getResumeTitle() { return resumeTitle; }
    public void setResumeTitle(String resumeTitle) { this.resumeTitle = resumeTitle; }
    public String getSkillTags() { return skillTags; }
    public void setSkillTags(String skillTags) { this.skillTags = skillTags; }
}
