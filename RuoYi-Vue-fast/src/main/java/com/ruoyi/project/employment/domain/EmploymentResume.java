package com.ruoyi.project.employment.domain;

import com.ruoyi.framework.web.domain.BaseEntity;

public class EmploymentResume extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long resumeId;
    private Long userId;
    private String resumeTitle;
    private Integer versionNo;
    private String templateName;
    private String summary;
    private String educationExperience;
    private String practiceExperience;
    private String campusExperience;
    private String skillList;
    private String certificateList;
    private String selfEvaluation;
    private String optimizeSuggestion;
    private String extractedKeywords;
    private String isDefault;
    private String studentName;

    public Long getResumeId() { return resumeId; }
    public void setResumeId(Long resumeId) { this.resumeId = resumeId; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getResumeTitle() { return resumeTitle; }
    public void setResumeTitle(String resumeTitle) { this.resumeTitle = resumeTitle; }
    public Integer getVersionNo() { return versionNo; }
    public void setVersionNo(Integer versionNo) { this.versionNo = versionNo; }
    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getEducationExperience() { return educationExperience; }
    public void setEducationExperience(String educationExperience) { this.educationExperience = educationExperience; }
    public String getPracticeExperience() { return practiceExperience; }
    public void setPracticeExperience(String practiceExperience) { this.practiceExperience = practiceExperience; }
    public String getCampusExperience() { return campusExperience; }
    public void setCampusExperience(String campusExperience) { this.campusExperience = campusExperience; }
    public String getSkillList() { return skillList; }
    public void setSkillList(String skillList) { this.skillList = skillList; }
    public String getCertificateList() { return certificateList; }
    public void setCertificateList(String certificateList) { this.certificateList = certificateList; }
    public String getSelfEvaluation() { return selfEvaluation; }
    public void setSelfEvaluation(String selfEvaluation) { this.selfEvaluation = selfEvaluation; }
    public String getOptimizeSuggestion() { return optimizeSuggestion; }
    public void setOptimizeSuggestion(String optimizeSuggestion) { this.optimizeSuggestion = optimizeSuggestion; }
    public String getExtractedKeywords() { return extractedKeywords; }
    public void setExtractedKeywords(String extractedKeywords) { this.extractedKeywords = extractedKeywords; }
    public String getIsDefault() { return isDefault; }
    public void setIsDefault(String isDefault) { this.isDefault = isDefault; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
}
