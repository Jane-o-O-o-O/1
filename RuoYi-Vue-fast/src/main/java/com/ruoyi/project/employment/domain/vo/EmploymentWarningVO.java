package com.ruoyi.project.employment.domain.vo;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

public class EmploymentWarningVO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long studentUserId;
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
    @Excel(name = "预警等级")
    private String warningLevel;
    @Excel(name = "困难等级")
    private String difficultyLevel;
    @Excel(name = "风险分")
    private Double riskScore;
    @Excel(name = "原因")
    private String reasonNote;
    private Integer applicationCount;
    private Integer resumeVersionCount;

    public Long getStudentUserId() { return studentUserId; }
    public void setStudentUserId(Long studentUserId) { this.studentUserId = studentUserId; }
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
    public String getWarningLevel() { return warningLevel; }
    public void setWarningLevel(String warningLevel) { this.warningLevel = warningLevel; }
    public String getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(String difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    public Double getRiskScore() { return riskScore; }
    public void setRiskScore(Double riskScore) { this.riskScore = riskScore; }
    public String getReasonNote() { return reasonNote; }
    public void setReasonNote(String reasonNote) { this.reasonNote = reasonNote; }
    public Integer getApplicationCount() { return applicationCount; }
    public void setApplicationCount(Integer applicationCount) { this.applicationCount = applicationCount; }
    public Integer getResumeVersionCount() { return resumeVersionCount; }
    public void setResumeVersionCount(Integer resumeVersionCount) { this.resumeVersionCount = resumeVersionCount; }
}
