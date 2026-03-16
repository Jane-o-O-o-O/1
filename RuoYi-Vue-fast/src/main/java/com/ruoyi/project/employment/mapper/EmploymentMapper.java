package com.ruoyi.project.employment.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.project.employment.domain.EmploymentAssistanceRecord;
import com.ruoyi.project.employment.domain.EmploymentEnterpriseProfile;
import com.ruoyi.project.employment.domain.EmploymentInterview;
import com.ruoyi.project.employment.domain.EmploymentJob;
import com.ruoyi.project.employment.domain.EmploymentJobApplication;
import com.ruoyi.project.employment.domain.EmploymentMaterial;
import com.ruoyi.project.employment.domain.EmploymentResume;
import com.ruoyi.project.employment.domain.EmploymentStatus;
import com.ruoyi.project.employment.domain.EmploymentStudentProfile;
import com.ruoyi.project.employment.domain.vo.EmploymentWarningVO;

public interface EmploymentMapper
{
    EmploymentStudentProfile selectStudentProfileByUserId(Long userId);

    int insertStudentProfile(EmploymentStudentProfile profile);

    int updateStudentProfile(EmploymentStudentProfile profile);

    EmploymentResume selectDefaultResumeByUserId(Long userId);

    EmploymentResume selectResumeById(Long resumeId);

    List<EmploymentResume> selectResumeListByUserId(Long userId);

    int insertResume(EmploymentResume resume);

    int updateResume(EmploymentResume resume);

    int deleteResumeById(@Param("resumeId") Long resumeId, @Param("userId") Long userId);

    EmploymentMaterial selectMaterialById(Long materialId);

    List<EmploymentMaterial> selectMaterialList(EmploymentMaterial material);

    int insertMaterial(EmploymentMaterial material);

    int updateMaterial(EmploymentMaterial material);

    int deleteMaterialById(@Param("materialId") Long materialId, @Param("userId") Long userId);

    EmploymentEnterpriseProfile selectEnterpriseProfileByUserId(Long userId);

    int insertEnterpriseProfile(EmploymentEnterpriseProfile profile);

    int updateEnterpriseProfile(EmploymentEnterpriseProfile profile);

    EmploymentJob selectJobById(Long jobId);

    List<EmploymentJob> selectJobList(EmploymentJob job);

    int insertJob(EmploymentJob job);

    int updateJob(EmploymentJob job);

    EmploymentJobApplication selectApplicationById(Long applicationId);

    EmploymentJobApplication selectApplicationByJobAndStudent(@Param("jobId") Long jobId, @Param("studentUserId") Long studentUserId);

    List<EmploymentJobApplication> selectApplicationList(EmploymentJobApplication application);

    int insertApplication(EmploymentJobApplication application);

    int updateApplication(EmploymentJobApplication application);

    EmploymentInterview selectInterviewById(Long interviewId);

    List<EmploymentInterview> selectInterviewList(EmploymentInterview interview);

    int insertInterview(EmploymentInterview interview);

    int updateInterview(EmploymentInterview interview);

    EmploymentStatus selectEmploymentStatusByStudentUserId(Long studentUserId);

    List<EmploymentStatus> selectEmploymentStatusList(EmploymentStatus status);

    int insertEmploymentStatus(EmploymentStatus status);

    int updateEmploymentStatus(EmploymentStatus status);

    List<EmploymentAssistanceRecord> selectAssistanceRecordList(EmploymentAssistanceRecord record);

    int insertAssistanceRecord(EmploymentAssistanceRecord record);

    int updateAssistanceRecord(EmploymentAssistanceRecord record);

    EmploymentWarningVO selectWarningByStudentUserId(Long studentUserId);

    List<EmploymentWarningVO> selectWarningList(EmploymentWarningVO warning);

    Integer countStudentTotal(@Param("teacherUserId") Long teacherUserId);

    Integer countEmploymentStatus(@Param("teacherUserId") Long teacherUserId, @Param("status") String status);

    Integer countPendingMaterials(@Param("teacherUserId") Long teacherUserId);

    Integer countPendingJobs();

    Integer countTotalJobs(@Param("enterpriseUserId") Long enterpriseUserId);

    Integer countTotalInterviews(@Param("teacherUserId") Long teacherUserId, @Param("enterpriseUserId") Long enterpriseUserId);

    List<Map<String, Object>> selectStatusPie(@Param("teacherUserId") Long teacherUserId);

    List<Map<String, Object>> selectTrend(@Param("teacherUserId") Long teacherUserId);

    List<Map<String, Object>> selectIndustryDistribution(@Param("teacherUserId") Long teacherUserId);

    List<Map<String, Object>> selectTopWarnings(@Param("teacherUserId") Long teacherUserId);
}
