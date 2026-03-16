package com.ruoyi.project.employment.domain.vo;

import java.util.List;
import java.util.Map;

public class EmploymentDashboardVO
{
    private Integer totalStudents;
    private Integer employedStudents;
    private Integer unemployedStudents;
    private Integer difficultStudents;
    private Integer pendingMaterials;
    private Integer pendingJobs;
    private Integer totalJobs;
    private Integer totalInterviews;
    private List<Map<String, Object>> statusPie;
    private List<Map<String, Object>> trend;
    private List<Map<String, Object>> industry;
    private List<Map<String, Object>> topWarnings;

    public Integer getTotalStudents() { return totalStudents; }
    public void setTotalStudents(Integer totalStudents) { this.totalStudents = totalStudents; }
    public Integer getEmployedStudents() { return employedStudents; }
    public void setEmployedStudents(Integer employedStudents) { this.employedStudents = employedStudents; }
    public Integer getUnemployedStudents() { return unemployedStudents; }
    public void setUnemployedStudents(Integer unemployedStudents) { this.unemployedStudents = unemployedStudents; }
    public Integer getDifficultStudents() { return difficultStudents; }
    public void setDifficultStudents(Integer difficultStudents) { this.difficultStudents = difficultStudents; }
    public Integer getPendingMaterials() { return pendingMaterials; }
    public void setPendingMaterials(Integer pendingMaterials) { this.pendingMaterials = pendingMaterials; }
    public Integer getPendingJobs() { return pendingJobs; }
    public void setPendingJobs(Integer pendingJobs) { this.pendingJobs = pendingJobs; }
    public Integer getTotalJobs() { return totalJobs; }
    public void setTotalJobs(Integer totalJobs) { this.totalJobs = totalJobs; }
    public Integer getTotalInterviews() { return totalInterviews; }
    public void setTotalInterviews(Integer totalInterviews) { this.totalInterviews = totalInterviews; }
    public List<Map<String, Object>> getStatusPie() { return statusPie; }
    public void setStatusPie(List<Map<String, Object>> statusPie) { this.statusPie = statusPie; }
    public List<Map<String, Object>> getTrend() { return trend; }
    public void setTrend(List<Map<String, Object>> trend) { this.trend = trend; }
    public List<Map<String, Object>> getIndustry() { return industry; }
    public void setIndustry(List<Map<String, Object>> industry) { this.industry = industry; }
    public List<Map<String, Object>> getTopWarnings() { return topWarnings; }
    public void setTopWarnings(List<Map<String, Object>> topWarnings) { this.topWarnings = topWarnings; }
}
