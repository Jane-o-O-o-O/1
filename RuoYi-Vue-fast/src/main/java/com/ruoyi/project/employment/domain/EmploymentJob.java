package com.ruoyi.project.employment.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.web.domain.BaseEntity;

public class EmploymentJob extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long jobId;
    private Long enterpriseUserId;
    private Long enterpriseId;
    private String companyName;
    private String jobName;
    private String categoryName;
    private String city;
    private Double salaryMin;
    private Double salaryMax;
    private String educationRequirement;
    private String experienceRequirement;
    private String jobDesc;
    private String jobRequire;
    private String skillKeywords;
    private String jobStatus;
    private String auditStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;
    private Integer applicationCount;
    private Double averageMatchScore;

    public Long getJobId() { return jobId; }
    public void setJobId(Long jobId) { this.jobId = jobId; }
    public Long getEnterpriseUserId() { return enterpriseUserId; }
    public void setEnterpriseUserId(Long enterpriseUserId) { this.enterpriseUserId = enterpriseUserId; }
    public Long getEnterpriseId() { return enterpriseId; }
    public void setEnterpriseId(Long enterpriseId) { this.enterpriseId = enterpriseId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getJobName() { return jobName; }
    public void setJobName(String jobName) { this.jobName = jobName; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public Double getSalaryMin() { return salaryMin; }
    public void setSalaryMin(Double salaryMin) { this.salaryMin = salaryMin; }
    public Double getSalaryMax() { return salaryMax; }
    public void setSalaryMax(Double salaryMax) { this.salaryMax = salaryMax; }
    public String getEducationRequirement() { return educationRequirement; }
    public void setEducationRequirement(String educationRequirement) { this.educationRequirement = educationRequirement; }
    public String getExperienceRequirement() { return experienceRequirement; }
    public void setExperienceRequirement(String experienceRequirement) { this.experienceRequirement = experienceRequirement; }
    public String getJobDesc() { return jobDesc; }
    public void setJobDesc(String jobDesc) { this.jobDesc = jobDesc; }
    public String getJobRequire() { return jobRequire; }
    public void setJobRequire(String jobRequire) { this.jobRequire = jobRequire; }
    public String getSkillKeywords() { return skillKeywords; }
    public void setSkillKeywords(String skillKeywords) { this.skillKeywords = skillKeywords; }
    public String getJobStatus() { return jobStatus; }
    public void setJobStatus(String jobStatus) { this.jobStatus = jobStatus; }
    public String getAuditStatus() { return auditStatus; }
    public void setAuditStatus(String auditStatus) { this.auditStatus = auditStatus; }
    public Date getPublishTime() { return publishTime; }
    public void setPublishTime(Date publishTime) { this.publishTime = publishTime; }
    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }
    public Integer getApplicationCount() { return applicationCount; }
    public void setApplicationCount(Integer applicationCount) { this.applicationCount = applicationCount; }
    public Double getAverageMatchScore() { return averageMatchScore; }
    public void setAverageMatchScore(Double averageMatchScore) { this.averageMatchScore = averageMatchScore; }
}
