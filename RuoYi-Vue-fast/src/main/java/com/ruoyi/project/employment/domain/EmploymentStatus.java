package com.ruoyi.project.employment.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

public class EmploymentStatus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long statusId;
    private Long studentUserId;
    private Long teacherUserId;
    @Excel(name = "就业状态")
    private String currentStatus;
    @Excel(name = "困难等级")
    private String difficultyLevel;
    @Excel(name = "预警等级")
    private String warningLevel;
    @Excel(name = "就业单位")
    private String employmentCompany;
    @Excel(name = "岗位名称")
    private String positionName;
    @Excel(name = "薪资")
    private Double salaryAmount;
    @Excel(name = "求职阶段")
    private String progressStage;
    private Long evidenceMaterialId;
    @Excel(name = "风险分")
    private Double riskScore;
    @Excel(name = "原因说明")
    private String reasonNote;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastFollowTime;
    @Excel(name = "学生姓名")
    private String studentName;
    @Excel(name = "学号")
    private String studentNo;
    @Excel(name = "专业")
    private String majorName;
    @Excel(name = "班级")
    private String className;
    @Excel(name = "教师")
    private String teacherName;
    private String evidenceFileUrl;

    public Long getStatusId() { return statusId; }
    public void setStatusId(Long statusId) { this.statusId = statusId; }
    public Long getStudentUserId() { return studentUserId; }
    public void setStudentUserId(Long studentUserId) { this.studentUserId = studentUserId; }
    public Long getTeacherUserId() { return teacherUserId; }
    public void setTeacherUserId(Long teacherUserId) { this.teacherUserId = teacherUserId; }
    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }
    public String getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(String difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    public String getWarningLevel() { return warningLevel; }
    public void setWarningLevel(String warningLevel) { this.warningLevel = warningLevel; }
    public String getEmploymentCompany() { return employmentCompany; }
    public void setEmploymentCompany(String employmentCompany) { this.employmentCompany = employmentCompany; }
    public String getPositionName() { return positionName; }
    public void setPositionName(String positionName) { this.positionName = positionName; }
    public Double getSalaryAmount() { return salaryAmount; }
    public void setSalaryAmount(Double salaryAmount) { this.salaryAmount = salaryAmount; }
    public String getProgressStage() { return progressStage; }
    public void setProgressStage(String progressStage) { this.progressStage = progressStage; }
    public Long getEvidenceMaterialId() { return evidenceMaterialId; }
    public void setEvidenceMaterialId(Long evidenceMaterialId) { this.evidenceMaterialId = evidenceMaterialId; }
    public Double getRiskScore() { return riskScore; }
    public void setRiskScore(Double riskScore) { this.riskScore = riskScore; }
    public String getReasonNote() { return reasonNote; }
    public void setReasonNote(String reasonNote) { this.reasonNote = reasonNote; }
    public Date getLastFollowTime() { return lastFollowTime; }
    public void setLastFollowTime(Date lastFollowTime) { this.lastFollowTime = lastFollowTime; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }
    public String getMajorName() { return majorName; }
    public void setMajorName(String majorName) { this.majorName = majorName; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public String getEvidenceFileUrl() { return evidenceFileUrl; }
    public void setEvidenceFileUrl(String evidenceFileUrl) { this.evidenceFileUrl = evidenceFileUrl; }
}
