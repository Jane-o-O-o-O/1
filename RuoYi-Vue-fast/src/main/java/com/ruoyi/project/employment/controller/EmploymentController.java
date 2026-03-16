package com.ruoyi.project.employment.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.security.LoginUser;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
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
import com.ruoyi.project.employment.service.EmploymentService;

@RestController
@RequestMapping("/employment")
public class EmploymentController extends BaseController
{
    @Autowired
    private EmploymentService employmentService;

    @PreAuthorize("@ss.hasPermi('employment:student:profile')")
    @GetMapping("/student/profile")
    public AjaxResult studentProfile()
    {
        return success(employmentService.getStudentProfile(getUserId()));
    }

    @PreAuthorize("@ss.hasPermi('employment:student:profile')")
    @PutMapping("/student/profile")
    public AjaxResult saveStudentProfile(@RequestBody EmploymentStudentProfile profile)
    {
        profile.setUserId(getUserId());
        return success(employmentService.saveStudentProfile(profile, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('employment:resume')")
    @GetMapping("/resume/list")
    public AjaxResult resumeList()
    {
        return success(employmentService.listResumes(getUserId()));
    }

    @PreAuthorize("@ss.hasPermi('employment:resume')")
    @GetMapping("/resume/{resumeId}")
    public AjaxResult resumeInfo(@PathVariable Long resumeId)
    {
        return success(employmentService.getResume(resumeId));
    }

    @PreAuthorize("@ss.hasPermi('employment:resume')")
    @PostMapping("/resume/optimize")
    public AjaxResult optimizeResume(@RequestBody EmploymentResume resume)
    {
        return success(employmentService.optimizeResumeDraft(resume));
    }

    @PreAuthorize("@ss.hasPermi('employment:resume')")
    @PostMapping("/resume")
    public AjaxResult addResume(@RequestBody EmploymentResume resume)
    {
        resume.setUserId(getUserId());
        return success(employmentService.saveResume(resume, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('employment:resume')")
    @PutMapping("/resume")
    public AjaxResult editResume(@RequestBody EmploymentResume resume)
    {
        resume.setUserId(getUserId());
        return success(employmentService.saveResume(resume, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('employment:resume')")
    @DeleteMapping("/resume/{resumeId}")
    public AjaxResult deleteResume(@PathVariable Long resumeId)
    {
        employmentService.deleteResume(getUserId(), resumeId, getUsername());
        return success();
    }

    @PreAuthorize("@ss.hasPermi('employment:resume')")
    @PostMapping("/resume/export/{resumeId}")
    public void exportResume(HttpServletResponse response, @PathVariable Long resumeId) throws Exception
    {
        String fileName = employmentService.exportResume(resumeId);
        if (StringUtils.isEmpty(fileName))
        {
            return;
        }
        FileUtils.setAttachmentResponseHeader(response, "resume-" + resumeId + ".txt");
        FileUtils.writeBytes(RuoYiConfig.getDownloadPath() + fileName, response.getOutputStream());
    }

    @PreAuthorize("@ss.hasPermi('employment:material')")
    @GetMapping("/material/list")
    public AjaxResult materialList(EmploymentMaterial query)
    {
        if (!hasRole("admin"))
        {
            query.setUserId(getUserId());
        }
        return success(employmentService.listMaterials(query));
    }

    @PreAuthorize("@ss.hasPermi('employment:material')")
    @PostMapping("/material")
    public AjaxResult addMaterial(@RequestBody EmploymentMaterial material)
    {
        material.setUserId(getUserId());
        material.setReviewStatus("pending");
        return success(employmentService.saveMaterial(material, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('employment:material')")
    @PutMapping("/material")
    public AjaxResult editMaterial(@RequestBody EmploymentMaterial material)
    {
        if (!hasRole("admin"))
        {
            material.setUserId(getUserId());
            material.setReviewStatus("pending");
        }
        return success(employmentService.saveMaterial(material, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('employment:material')")
    @DeleteMapping("/material/{materialId}")
    public AjaxResult deleteMaterial(@PathVariable Long materialId)
    {
        employmentService.deleteMaterial(materialId, getUserId(), getUsername());
        return success();
    }

    @PreAuthorize("@ss.hasPermi('employment:admin:audit')")
    @PutMapping("/material/review")
    public AjaxResult reviewMaterial(@RequestBody EmploymentMaterial material)
    {
        material.setReviewerId(getUserId());
        return success(employmentService.saveMaterial(material, getUsername()));
    }

    @PreAuthorize("@ss.hasPermi('employment:company:profile')")
    @GetMapping("/company/profile")
    public AjaxResult companyProfile()
    {
        return success(employmentService.getEnterpriseProfile(getUserId()));
    }

    @PreAuthorize("@ss.hasPermi('employment:company:profile')")
    @PutMapping("/company/profile")
    public AjaxResult saveCompanyProfile(@RequestBody EmploymentEnterpriseProfile profile)
    {
        profile.setUserId(getUserId());
        return success(employmentService.saveEnterpriseProfile(profile, getUsername()));
    }

    @GetMapping("/job/list")
    public AjaxResult jobList(EmploymentJob query)
    {
        if (hasRole("company"))
        {
            query.setEnterpriseUserId(getUserId());
        }
        else if (!hasRole("admin"))
        {
            query.setAuditStatus("approved");
            query.setJobStatus("published");
        }
        return success(employmentService.listJobs(query));
    }

    @GetMapping("/job/{jobId}")
    public AjaxResult jobInfo(@PathVariable Long jobId)
    {
        return success(employmentService.getJob(jobId));
    }

    @PreAuthorize("@ss.hasPermi('employment:company:job')")
    @PostMapping("/job")
    public AjaxResult addJob(@RequestBody EmploymentJob job)
    {
        EmploymentEnterpriseProfile profile = employmentService.getEnterpriseProfile(getUserId());
        job.setEnterpriseUserId(getUserId());
        job.setEnterpriseId(profile.getEnterpriseId());
        job.setCompanyName(profile.getCompanyName());
        job.setAuditStatus("pending");
        return success(employmentService.saveJob(job, getUsername(), false));
    }

    @PreAuthorize("@ss.hasPermi('employment:company:job')")
    @PutMapping("/job")
    public AjaxResult editJob(@RequestBody EmploymentJob job)
    {
        EmploymentEnterpriseProfile profile = employmentService.getEnterpriseProfile(getUserId());
        job.setEnterpriseUserId(getUserId());
        job.setEnterpriseId(profile.getEnterpriseId());
        job.setCompanyName(profile.getCompanyName());
        return success(employmentService.saveJob(job, getUsername(), true));
    }

    @PreAuthorize("@ss.hasPermi('employment:admin:audit')")
    @PutMapping("/job/{jobId}/audit")
    public AjaxResult auditJob(@PathVariable Long jobId, @RequestBody Map<String, String> body)
    {
        employmentService.auditJob(jobId, body.get("auditStatus"), getUsername());
        return success();
    }
    @PreAuthorize("@ss.hasPermi('employment:application')")
    @PostMapping("/application/apply/{jobId}")
    public AjaxResult applyJob(@PathVariable Long jobId)
    {
        return success(employmentService.applyJob(jobId, getUserId(), getUsername()));
    }

    @GetMapping("/application/list")
    public AjaxResult applicationList(EmploymentJobApplication query)
    {
        if (hasRole("company"))
        {
            query.setEnterpriseUserId(getUserId());
        }
        else if (!hasRole("admin"))
        {
            query.setStudentUserId(getUserId());
        }
        return success(employmentService.listApplications(query));
    }

    @PreAuthorize("@ss.hasPermi('employment:application') or @ss.hasPermi('employment:company:resume')")
    @PutMapping("/application/status")
    public AjaxResult updateApplication(@RequestBody EmploymentJobApplication application)
    {
        return success(employmentService.updateApplicationStatus(application, getUsername()));
    }

    @GetMapping("/interview/list")
    public AjaxResult interviewList(EmploymentInterview query)
    {
        if (hasRole("company"))
        {
            query.setEnterpriseUserId(getUserId());
        }
        else if (!hasRole("admin"))
        {
            query.setStudentUserId(getUserId());
        }
        return success(employmentService.listInterviews(query));
    }

    @PreAuthorize("@ss.hasPermi('employment:company:interview')")
    @PostMapping("/interview")
    public AjaxResult addInterview(@RequestBody EmploymentInterview interview)
    {
        interview.setEnterpriseUserId(getUserId());
        return success(employmentService.saveInterview(interview, getUsername()));
    }

    @PutMapping("/interview")
    public AjaxResult editInterview(@RequestBody EmploymentInterview interview)
    {
        if (hasRole("company"))
        {
            interview.setEnterpriseUserId(getUserId());
        }
        return success(employmentService.saveInterview(interview, getUsername()));
    }

    @GetMapping("/status/me")
    public AjaxResult myStatus()
    {
        return success(employmentService.getStatus(getUserId()));
    }

    @GetMapping("/status/list")
    public TableDataInfo statusList(EmploymentStatus query)
    {
        if (hasRole("teacher"))
        {
            query.setTeacherUserId(getUserId());
        }
        List<EmploymentStatus> list = employmentService.listStatuses(query);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('employment:teacher:status') or @ss.hasPermi('employment:admin:status')")
    @PutMapping("/status")
    public AjaxResult saveStatus(@RequestBody EmploymentStatus status)
    {
        if (hasRole("teacher"))
        {
            status.setTeacherUserId(getUserId());
        }
        return success(employmentService.saveStatus(status, getUsername()));
    }

    @GetMapping("/assist/list")
    public AjaxResult assistList(EmploymentAssistanceRecord query)
    {
        if (hasRole("teacher"))
        {
            query.setHelperUserId(getUserId());
        }
        return success(employmentService.listAssistanceRecords(query));
    }

    @PreAuthorize("@ss.hasPermi('employment:teacher:assist') or @ss.hasPermi('employment:admin:status')")
    @PostMapping("/assist")
    public AjaxResult addAssist(@RequestBody EmploymentAssistanceRecord record)
    {
        record.setHelperUserId(getUserId());
        if (StringUtils.isEmpty(record.getHelperRole()))
        {
            record.setHelperRole(hasRole("teacher") ? "teacher" : "admin");
        }
        return success(employmentService.saveAssistanceRecord(record, getUsername()));
    }

    @GetMapping("/warning/list")
    public TableDataInfo warningList(EmploymentWarningVO query)
    {
        Long teacherUserId = hasRole("teacher") ? getUserId() : null;
        List<EmploymentWarningVO> list = employmentService.listWarnings(teacherUserId, query);
        return getDataTable(list);
    }

    @PostMapping("/status/export")
    public void exportStatus(HttpServletResponse response, EmploymentStatus query)
    {
        if (hasRole("teacher"))
        {
            query.setTeacherUserId(getUserId());
        }
        List<EmploymentStatus> list = employmentService.listStatuses(query);
        ExcelUtil<EmploymentStatus> util = new ExcelUtil<EmploymentStatus>(EmploymentStatus.class);
        util.exportExcel(response, list, "就业状态数据");
    }

    @PostMapping("/warning/export")
    public void exportWarning(HttpServletResponse response, EmploymentWarningVO query)
    {
        Long teacherUserId = hasRole("teacher") ? getUserId() : null;
        List<EmploymentWarningVO> list = employmentService.listWarnings(teacherUserId, query);
        ExcelUtil<EmploymentWarningVO> util = new ExcelUtil<EmploymentWarningVO>(EmploymentWarningVO.class);
        util.exportExcel(response, list, "困难学生预警");
    }

    @GetMapping("/dashboard/teacher")
    public AjaxResult teacherDashboard()
    {
        return success(employmentService.buildDashboard(getUserId(), null));
    }

    @GetMapping("/dashboard/admin")
    public AjaxResult adminDashboard()
    {
        return success(employmentService.buildDashboard(null, null));
    }

    @GetMapping("/dashboard/company")
    public AjaxResult companyDashboard()
    {
        return success(employmentService.buildDashboard(null, getUserId()));
    }

    private boolean hasRole(String roleKey)
    {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null || loginUser.getUser() == null || loginUser.getUser().getRoles() == null)
        {
            return false;
        }
        return loginUser.getUser().getRoles().stream().anyMatch(role -> roleKey.equals(role.getRoleKey()) || "admin".equals(role.getRoleKey()));
    }
}

