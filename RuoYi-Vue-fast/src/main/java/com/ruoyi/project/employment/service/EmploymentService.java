package com.ruoyi.project.employment.service;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.project.employment.domain.EmploymentAssistanceRecord;
import com.ruoyi.project.employment.domain.EmploymentEnterpriseProfile;
import com.ruoyi.project.employment.domain.EmploymentInterview;
import com.ruoyi.project.employment.domain.EmploymentJob;
import com.ruoyi.project.employment.domain.EmploymentJobApplication;
import com.ruoyi.project.employment.domain.EmploymentMaterial;
import com.ruoyi.project.employment.domain.EmploymentResume;
import com.ruoyi.project.employment.domain.EmploymentStatus;
import com.ruoyi.project.employment.domain.EmploymentStudentProfile;
import com.ruoyi.project.employment.domain.vo.EmploymentDashboardVO;
import com.ruoyi.project.employment.domain.vo.EmploymentWarningVO;

@Service
public class EmploymentService
{
    private static final RowMapper<EmploymentStudentProfile> STUDENT_PROFILE_MAPPER = BeanPropertyRowMapper.newInstance(EmploymentStudentProfile.class);
    private static final RowMapper<EmploymentResume> RESUME_MAPPER = BeanPropertyRowMapper.newInstance(EmploymentResume.class);
    private static final RowMapper<EmploymentMaterial> MATERIAL_MAPPER = BeanPropertyRowMapper.newInstance(EmploymentMaterial.class);
    private static final RowMapper<EmploymentEnterpriseProfile> ENTERPRISE_MAPPER = BeanPropertyRowMapper.newInstance(EmploymentEnterpriseProfile.class);
    private static final RowMapper<EmploymentJob> JOB_MAPPER = BeanPropertyRowMapper.newInstance(EmploymentJob.class);
    private static final RowMapper<EmploymentJobApplication> APPLICATION_MAPPER = BeanPropertyRowMapper.newInstance(EmploymentJobApplication.class);
    private static final RowMapper<EmploymentInterview> INTERVIEW_MAPPER = BeanPropertyRowMapper.newInstance(EmploymentInterview.class);
    private static final RowMapper<EmploymentStatus> STATUS_MAPPER = BeanPropertyRowMapper.newInstance(EmploymentStatus.class);
    private static final RowMapper<EmploymentAssistanceRecord> ASSISTANCE_MAPPER = BeanPropertyRowMapper.newInstance(EmploymentAssistanceRecord.class);
    private static final RowMapper<EmploymentWarningVO> WARNING_MAPPER = BeanPropertyRowMapper.newInstance(EmploymentWarningVO.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public EmploymentStudentProfile getStudentProfile(Long userId)
    {
        List<EmploymentStudentProfile> list = jdbcTemplate.query(
            "select sp.*, su.nick_name student_name, tu.nick_name teacher_name from emp_student_profile sp "
                + "left join sys_user su on su.user_id = sp.user_id "
                + "left join sys_user tu on tu.user_id = sp.teacher_user_id where sp.user_id = ? and sp.del_flag = '0'",
            STUDENT_PROFILE_MAPPER, userId);
        if (!list.isEmpty())
        {
            return list.get(0);
        }
        EmploymentStudentProfile profile = new EmploymentStudentProfile();
        profile.setUserId(userId);
        profile.setEmploymentStatus("unemployed");
        profile.setDifficultyLevel("normal");
        profile.setWarningLevel("low");
        return profile;
    }

    @Transactional
    public EmploymentStudentProfile saveStudentProfile(EmploymentStudentProfile profile, String operator)
    {
        EmploymentStudentProfile current = getStudentProfile(profile.getUserId());
        if (current.getProfileId() == null)
        {
            jdbcTemplate.update("insert into emp_student_profile(user_id, teacher_user_id, student_no, major_name, class_name, graduation_year, gpa, expected_city, expected_salary, job_intention, skill_tags, contact_address, employment_status, difficulty_level, warning_level, create_by, create_time, remark) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?)",
                profile.getUserId(), profile.getTeacherUserId(), profile.getStudentNo(), profile.getMajorName(), profile.getClassName(), profile.getGraduationYear(), profile.getGpa(), profile.getExpectedCity(), profile.getExpectedSalary(), profile.getJobIntention(), profile.getSkillTags(), profile.getContactAddress(),
                StringUtils.isEmpty(profile.getEmploymentStatus()) ? "unemployed" : profile.getEmploymentStatus(),
                StringUtils.isEmpty(profile.getDifficultyLevel()) ? "normal" : profile.getDifficultyLevel(),
                StringUtils.isEmpty(profile.getWarningLevel()) ? "low" : profile.getWarningLevel(), operator, profile.getRemark());
        }
        else
        {
            jdbcTemplate.update("update emp_student_profile set teacher_user_id = ?, student_no = ?, major_name = ?, class_name = ?, graduation_year = ?, gpa = ?, expected_city = ?, expected_salary = ?, job_intention = ?, skill_tags = ?, contact_address = ?, employment_status = ?, difficulty_level = ?, warning_level = ?, update_by = ?, update_time = now(), remark = ? where user_id = ?",
                profile.getTeacherUserId(), profile.getStudentNo(), profile.getMajorName(), profile.getClassName(), profile.getGraduationYear(), profile.getGpa(), profile.getExpectedCity(), profile.getExpectedSalary(), profile.getJobIntention(), profile.getSkillTags(), profile.getContactAddress(),
                StringUtils.isEmpty(profile.getEmploymentStatus()) ? current.getEmploymentStatus() : profile.getEmploymentStatus(),
                StringUtils.isEmpty(profile.getDifficultyLevel()) ? current.getDifficultyLevel() : profile.getDifficultyLevel(),
                StringUtils.isEmpty(profile.getWarningLevel()) ? current.getWarningLevel() : profile.getWarningLevel(), operator, profile.getRemark(), profile.getUserId());
        }
        rebuildRisk(profile.getUserId(), operator);
        return getStudentProfile(profile.getUserId());
    }

    public List<EmploymentResume> listResumes(Long userId)
    {
        return jdbcTemplate.query("select r.*, u.nick_name student_name from emp_resume r left join sys_user u on u.user_id = r.user_id where r.user_id = ? and r.del_flag = '0' order by r.version_no desc, r.resume_id desc", RESUME_MAPPER, userId);
    }

    public EmploymentResume getResume(Long resumeId)
    {
        List<EmploymentResume> list = jdbcTemplate.query("select r.*, u.nick_name student_name from emp_resume r left join sys_user u on u.user_id = r.user_id where r.resume_id = ? and r.del_flag = '0'", RESUME_MAPPER, resumeId);
        return list.isEmpty() ? null : list.get(0);
    }

    public EmploymentResume getDefaultResume(Long userId)
    {
        List<EmploymentResume> list = jdbcTemplate.query("select r.*, u.nick_name student_name from emp_resume r left join sys_user u on u.user_id = r.user_id where r.user_id = ? and r.del_flag = '0' order by case when r.is_default = 'Y' then 0 else 1 end, r.version_no desc, r.resume_id desc", RESUME_MAPPER, userId);
        return list.isEmpty() ? null : list.get(0);
    }

    public EmploymentResume optimizeResumeDraft(EmploymentResume resume)
    {
        String source = joinText(resume.getSummary(), resume.getEducationExperience(), resume.getPracticeExperience(), resume.getCampusExperience(), resume.getSkillList(), resume.getCertificateList(), resume.getSelfEvaluation());
        List<String> keywords = Arrays.stream(source.replace("，", ",").replace(";", ",").replace("|", ",").split("[,\\s]+"))
            .filter(StringUtils::isNotEmpty)
            .map(String::trim)
            .filter(item -> item.length() >= 2)
            .distinct()
            .limit(10)
            .collect(Collectors.toList());
        List<String> suggestions = new ArrayList<>();
        if (StringUtils.isEmpty(resume.getEducationExperience())) { suggestions.add("补充完整教育经历，至少包含学校、专业、学历和时间范围"); }
        if (StringUtils.isEmpty(resume.getPracticeExperience())) { suggestions.add("补充项目或实习经历，并量化成果"); }
        if (StringUtils.isEmpty(resume.getSkillList())) { suggestions.add("补充核心技能栈，并与目标岗位关键词保持一致"); }
        if (StringUtils.length(resume.getSelfEvaluation()) < 20) { suggestions.add("自我评价建议突出职业目标、技术优势和沟通协作能力"); }
        if (suggestions.isEmpty()) { suggestions.add("简历结构完整，建议继续围绕量化成果与岗位关键词做微调"); }
        resume.setExtractedKeywords(String.join(",", keywords));
        resume.setOptimizeSuggestion(String.join("；", suggestions));
        return resume;
    }
    @Transactional
    public EmploymentResume saveResume(EmploymentResume resume, String operator)
    {
        optimizeResumeDraft(resume);
        if ("Y".equals(resume.getIsDefault()))
        {
            jdbcTemplate.update("update emp_resume set is_default = 'N', update_time = now() where user_id = ? and del_flag = '0'", resume.getUserId());
        }
        if (resume.getResumeId() == null)
        {
            Integer currentVersion = jdbcTemplate.queryForObject("select ifnull(max(version_no), 0) from emp_resume where user_id = ? and del_flag = '0'", Integer.class, resume.getUserId());
            resume.setVersionNo(currentVersion == null ? 1 : currentVersion + 1);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                java.sql.PreparedStatement ps = connection.prepareStatement(
                    "insert into emp_resume(user_id, resume_title, version_no, template_name, summary, education_experience, practice_experience, campus_experience, skill_list, certificate_list, self_evaluation, optimize_suggestion, extracted_keywords, is_default, create_by, create_time, remark) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?)",
                    new String[] { "resume_id" });
                ps.setLong(1, resume.getUserId());
                ps.setString(2, resume.getResumeTitle());
                ps.setInt(3, resume.getVersionNo());
                ps.setString(4, defaultString(resume.getTemplateName(), "classic"));
                ps.setString(5, resume.getSummary());
                ps.setString(6, resume.getEducationExperience());
                ps.setString(7, resume.getPracticeExperience());
                ps.setString(8, resume.getCampusExperience());
                ps.setString(9, resume.getSkillList());
                ps.setString(10, resume.getCertificateList());
                ps.setString(11, resume.getSelfEvaluation());
                ps.setString(12, resume.getOptimizeSuggestion());
                ps.setString(13, resume.getExtractedKeywords());
                ps.setString(14, defaultString(resume.getIsDefault(), currentVersion == null || currentVersion == 0 ? "Y" : "N"));
                ps.setString(15, operator);
                ps.setString(16, resume.getRemark());
                return ps;
            }, keyHolder);
            resume.setResumeId(keyHolder.getKey().longValue());
        }
        else
        {
            jdbcTemplate.update("update emp_resume set resume_title = ?, template_name = ?, summary = ?, education_experience = ?, practice_experience = ?, campus_experience = ?, skill_list = ?, certificate_list = ?, self_evaluation = ?, optimize_suggestion = ?, extracted_keywords = ?, is_default = ?, update_by = ?, update_time = now(), remark = ? where resume_id = ? and user_id = ?",
                resume.getResumeTitle(), defaultString(resume.getTemplateName(), "classic"), resume.getSummary(), resume.getEducationExperience(), resume.getPracticeExperience(), resume.getCampusExperience(), resume.getSkillList(), resume.getCertificateList(), resume.getSelfEvaluation(), resume.getOptimizeSuggestion(), resume.getExtractedKeywords(), defaultString(resume.getIsDefault(), "N"), operator, resume.getRemark(), resume.getResumeId(), resume.getUserId());
        }
        rebuildRisk(resume.getUserId(), operator);
        return getResume(resume.getResumeId());
    }

    @Transactional
    public void deleteResume(Long userId, Long resumeId, String operator)
    {
        jdbcTemplate.update("update emp_resume set del_flag = '2', update_by = ?, update_time = now() where resume_id = ? and user_id = ?", operator, resumeId, userId);
        rebuildRisk(userId, operator);
    }

    public String exportResume(Long resumeId)
    {
        EmploymentResume resume = getResume(resumeId);
        if (resume == null)
        {
            return null;
        }
        String content = "简历标题: " + defaultString(resume.getResumeTitle(), "") + "\r\n\r\n"
            + "摘要:\r\n" + defaultString(resume.getSummary(), "") + "\r\n\r\n"
            + "教育经历:\r\n" + defaultString(resume.getEducationExperience(), "") + "\r\n\r\n"
            + "实践经历:\r\n" + defaultString(resume.getPracticeExperience(), "") + "\r\n\r\n"
            + "校园经历:\r\n" + defaultString(resume.getCampusExperience(), "") + "\r\n\r\n"
            + "技能清单:\r\n" + defaultString(resume.getSkillList(), "") + "\r\n\r\n"
            + "证书:\r\n" + defaultString(resume.getCertificateList(), "") + "\r\n\r\n"
            + "自我评价:\r\n" + defaultString(resume.getSelfEvaluation(), "") + "\r\n\r\n"
            + "优化建议:\r\n" + defaultString(resume.getOptimizeSuggestion(), "") + "\r\n\r\n"
            + "关键词:\r\n" + defaultString(resume.getExtractedKeywords(), "");
        String fileName = UUID.randomUUID().toString().replace("-", "") + "_resume_" + resumeId + ".txt";
        File file = new File(RuoYiConfig.getDownloadPath(), fileName);
        file.getParentFile().mkdirs();
        try (FileOutputStream outputStream = new FileOutputStream(file))
        {
            outputStream.write(content.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex.getMessage(), ex);
        }
        return fileName;
    }

    public List<EmploymentMaterial> listMaterials(EmploymentMaterial query)
    {
        StringBuilder sql = new StringBuilder("select m.*, u.nick_name student_name from emp_material m left join sys_user u on u.user_id = m.user_id where m.del_flag = '0'");
        List<Object> params = new ArrayList<>();
        if (query.getUserId() != null)
        {
            sql.append(" and m.user_id = ?");
            params.add(query.getUserId());
        }
        if (StringUtils.isNotEmpty(query.getReviewStatus()))
        {
            sql.append(" and m.review_status = ?");
            params.add(query.getReviewStatus());
        }
        if (StringUtils.isNotEmpty(query.getMaterialType()))
        {
            sql.append(" and m.material_type = ?");
            params.add(query.getMaterialType());
        }
        sql.append(" order by m.material_id desc");
        return jdbcTemplate.query(sql.toString(), MATERIAL_MAPPER, params.toArray());
    }

    @Transactional
    public EmploymentMaterial saveMaterial(EmploymentMaterial material, String operator)
    {
        if (material.getMaterialId() == null)
        {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                java.sql.PreparedStatement ps = connection.prepareStatement(
                    "insert into emp_material(user_id, material_type, material_name, file_name, file_url, review_status, review_comment, reviewer_id, create_by, create_time, remark) values (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?)",
                    new String[] { "material_id" });
                ps.setLong(1, material.getUserId());
                ps.setString(2, material.getMaterialType());
                ps.setString(3, material.getMaterialName());
                ps.setString(4, material.getFileName());
                ps.setString(5, material.getFileUrl());
                ps.setString(6, defaultString(material.getReviewStatus(), "pending"));
                ps.setString(7, material.getReviewComment());
                setLong(ps, 8, material.getReviewerId());
                ps.setString(9, operator);
                ps.setString(10, material.getRemark());
                return ps;
            }, keyHolder);
            material.setMaterialId(keyHolder.getKey().longValue());
        }
        else
        {
            jdbcTemplate.update("update emp_material set material_type = ?, material_name = ?, file_name = ?, file_url = ?, review_status = ?, review_comment = ?, reviewer_id = ?, update_by = ?, update_time = now(), remark = ? where material_id = ?",
                material.getMaterialType(), material.getMaterialName(), material.getFileName(), material.getFileUrl(), defaultString(material.getReviewStatus(), "pending"), material.getReviewComment(), material.getReviewerId(), operator, material.getRemark(), material.getMaterialId());
        }
        return getMaterial(material.getMaterialId());
    }

    public EmploymentMaterial getMaterial(Long materialId)
    {
        List<EmploymentMaterial> list = jdbcTemplate.query("select m.*, u.nick_name student_name from emp_material m left join sys_user u on u.user_id = m.user_id where m.material_id = ? and m.del_flag = '0'", MATERIAL_MAPPER, materialId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Transactional
    public void deleteMaterial(Long materialId, Long userId, String operator)
    {
        jdbcTemplate.update("update emp_material set del_flag = '2', update_by = ?, update_time = now() where material_id = ? and user_id = ?", operator, materialId, userId);
    }

    public EmploymentEnterpriseProfile getEnterpriseProfile(Long userId)
    {
        List<EmploymentEnterpriseProfile> list = jdbcTemplate.query("select * from emp_enterprise_profile where user_id = ? and del_flag = '0'", ENTERPRISE_MAPPER, userId);
        if (!list.isEmpty())
        {
            return list.get(0);
        }
        EmploymentEnterpriseProfile profile = new EmploymentEnterpriseProfile();
        profile.setUserId(userId);
        profile.setCertificationStatus("pending");
        return profile;
    }

    @Transactional
    public EmploymentEnterpriseProfile saveEnterpriseProfile(EmploymentEnterpriseProfile profile, String operator)
    {
        EmploymentEnterpriseProfile current = getEnterpriseProfile(profile.getUserId());
        if (current.getEnterpriseId() == null)
        {
            jdbcTemplate.update("insert into emp_enterprise_profile(user_id, company_name, industry_name, city, address, company_size, contact_name, contact_phone, company_intro, certification_status, license_file_url, create_by, create_time, remark) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?)",
                profile.getUserId(), profile.getCompanyName(), profile.getIndustryName(), profile.getCity(), profile.getAddress(), profile.getCompanySize(), profile.getContactName(), profile.getContactPhone(), profile.getCompanyIntro(), defaultString(profile.getCertificationStatus(), "pending"), profile.getLicenseFileUrl(), operator, profile.getRemark());
        }
        else
        {
            jdbcTemplate.update("update emp_enterprise_profile set company_name = ?, industry_name = ?, city = ?, address = ?, company_size = ?, contact_name = ?, contact_phone = ?, company_intro = ?, certification_status = ?, license_file_url = ?, update_by = ?, update_time = now(), remark = ? where user_id = ?",
                profile.getCompanyName(), profile.getIndustryName(), profile.getCity(), profile.getAddress(), profile.getCompanySize(), profile.getContactName(), profile.getContactPhone(), profile.getCompanyIntro(), defaultString(profile.getCertificationStatus(), current.getCertificationStatus()), profile.getLicenseFileUrl(), operator, profile.getRemark(), profile.getUserId());
        }
        return getEnterpriseProfile(profile.getUserId());
    }
    public List<EmploymentJob> listJobs(EmploymentJob query)
    {
        StringBuilder sql = new StringBuilder("select j.*, (select count(1) from emp_job_application a where a.job_id = j.job_id and a.del_flag = '0') application_count, (select ifnull(avg(a.match_score), 0) from emp_job_application a where a.job_id = j.job_id and a.del_flag = '0') average_match_score from emp_job j where j.del_flag = '0'");
        List<Object> params = new ArrayList<>();
        if (query.getEnterpriseUserId() != null)
        {
            sql.append(" and j.enterprise_user_id = ?");
            params.add(query.getEnterpriseUserId());
        }
        if (StringUtils.isNotEmpty(query.getJobName()))
        {
            sql.append(" and j.job_name like ?");
            params.add("%" + query.getJobName() + "%");
        }
        if (StringUtils.isNotEmpty(query.getCategoryName()))
        {
            sql.append(" and j.category_name = ?");
            params.add(query.getCategoryName());
        }
        if (StringUtils.isNotEmpty(query.getCity()))
        {
            sql.append(" and j.city like ?");
            params.add("%" + query.getCity() + "%");
        }
        if (StringUtils.isNotEmpty(query.getJobStatus()))
        {
            sql.append(" and j.job_status = ?");
            params.add(query.getJobStatus());
        }
        if (StringUtils.isNotEmpty(query.getAuditStatus()))
        {
            sql.append(" and j.audit_status = ?");
            params.add(query.getAuditStatus());
        }
        sql.append(" order by ifnull(j.publish_time, j.create_time) desc, j.job_id desc");
        return jdbcTemplate.query(sql.toString(), JOB_MAPPER, params.toArray());
    }

    public EmploymentJob getJob(Long jobId)
    {
        List<EmploymentJob> list = jdbcTemplate.query("select j.*, (select count(1) from emp_job_application a where a.job_id = j.job_id and a.del_flag = '0') application_count, (select ifnull(avg(a.match_score), 0) from emp_job_application a where a.job_id = j.job_id and a.del_flag = '0') average_match_score from emp_job j where j.job_id = ? and j.del_flag = '0'", JOB_MAPPER, jobId);
        return list.isEmpty() ? null : list.get(0);
    }

    @Transactional
    public EmploymentJob saveJob(EmploymentJob job, String operator, boolean resetAudit)
    {
        String jobStatus = defaultString(job.getJobStatus(), "published");
        if ("published".equals(jobStatus) && job.getPublishTime() == null)
        {
            job.setPublishTime(new Date());
        }
        if (job.getJobId() == null)
        {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                java.sql.PreparedStatement ps = connection.prepareStatement(
                    "insert into emp_job(enterprise_user_id, enterprise_id, company_name, job_name, category_name, city, salary_min, salary_max, education_requirement, experience_requirement, job_desc, job_require, skill_keywords, job_status, audit_status, publish_time, deadline, create_by, create_time, remark) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?)",
                    new String[] { "job_id" });
                ps.setLong(1, job.getEnterpriseUserId());
                setLong(ps, 2, job.getEnterpriseId());
                ps.setString(3, job.getCompanyName());
                ps.setString(4, job.getJobName());
                ps.setString(5, job.getCategoryName());
                ps.setString(6, job.getCity());
                setDouble(ps, 7, job.getSalaryMin());
                setDouble(ps, 8, job.getSalaryMax());
                ps.setString(9, job.getEducationRequirement());
                ps.setString(10, job.getExperienceRequirement());
                ps.setString(11, job.getJobDesc());
                ps.setString(12, job.getJobRequire());
                ps.setString(13, job.getSkillKeywords());
                ps.setString(14, jobStatus);
                ps.setString(15, defaultString(job.getAuditStatus(), "pending"));
                setDate(ps, 16, job.getPublishTime());
                setDate(ps, 17, job.getDeadline());
                ps.setString(18, operator);
                ps.setString(19, job.getRemark());
                return ps;
            }, keyHolder);
            job.setJobId(keyHolder.getKey().longValue());
        }
        else
        {
            jdbcTemplate.update("update emp_job set company_name = ?, job_name = ?, category_name = ?, city = ?, salary_min = ?, salary_max = ?, education_requirement = ?, experience_requirement = ?, job_desc = ?, job_require = ?, skill_keywords = ?, job_status = ?, audit_status = ?, publish_time = ?, deadline = ?, update_by = ?, update_time = now(), remark = ? where job_id = ?",
                job.getCompanyName(), job.getJobName(), job.getCategoryName(), job.getCity(), job.getSalaryMin(), job.getSalaryMax(), job.getEducationRequirement(), job.getExperienceRequirement(), job.getJobDesc(), job.getJobRequire(), job.getSkillKeywords(), jobStatus,
                resetAudit ? "pending" : defaultString(job.getAuditStatus(), "pending"), toSqlDate(job.getPublishTime()), toSqlDate(job.getDeadline()), operator, job.getRemark(), job.getJobId());
        }
        return getJob(job.getJobId());
    }

    @Transactional
    public void auditJob(Long jobId, String auditStatus, String operator)
    {
        String jobStatus = "approved".equals(auditStatus) ? "published" : "draft";
        jdbcTemplate.update("update emp_job set audit_status = ?, job_status = ?, update_by = ?, update_time = now() where job_id = ?", auditStatus, jobStatus, operator, jobId);
    }

    @Transactional
    public EmploymentJobApplication applyJob(Long jobId, Long studentUserId, String operator)
    {
        List<EmploymentJobApplication> existing = jdbcTemplate.query("select * from emp_job_application where job_id = ? and student_user_id = ? and del_flag = '0'", APPLICATION_MAPPER, jobId, studentUserId);
        if (!existing.isEmpty())
        {
            return existing.get(0);
        }
        EmploymentResume resume = getDefaultResume(studentUserId);
        EmploymentJob job = getJob(jobId);
        if (resume == null || job == null)
        {
            throw new RuntimeException("简历或岗位不存在");
        }
        double score = calculateMatchScore(job, resume);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            java.sql.PreparedStatement ps = connection.prepareStatement(
                "insert into emp_job_application(job_id, student_user_id, resume_id, enterprise_user_id, apply_status, match_score, progress_remark, applied_time, create_by, create_time, remark) values (?, ?, ?, ?, ?, ?, ?, now(), ?, now(), ?)",
                new String[] { "application_id" });
            ps.setLong(1, jobId);
            ps.setLong(2, studentUserId);
            ps.setLong(3, resume.getResumeId());
            ps.setLong(4, job.getEnterpriseUserId());
            ps.setString(5, "applied");
            ps.setDouble(6, score);
            ps.setString(7, "系统已根据关键词自动计算匹配度");
            ps.setString(8, operator);
            ps.setString(9, "employment");
            return ps;
        }, keyHolder);
        rebuildRisk(studentUserId, operator);
        return getApplication(keyHolder.getKey().longValue());
    }

    public EmploymentJobApplication getApplication(Long applicationId)
    {
        List<EmploymentJobApplication> list = jdbcTemplate.query("select a.*, su.nick_name student_name, sp.major_name, sp.class_name, j.job_name, j.company_name, r.resume_title, sp.skill_tags from emp_job_application a left join sys_user su on su.user_id = a.student_user_id left join emp_student_profile sp on sp.user_id = a.student_user_id and sp.del_flag = '0' left join emp_job j on j.job_id = a.job_id left join emp_resume r on r.resume_id = a.resume_id where a.application_id = ? and a.del_flag = '0'", APPLICATION_MAPPER, applicationId);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<EmploymentJobApplication> listApplications(EmploymentJobApplication query)
    {
        StringBuilder sql = new StringBuilder("select a.*, su.nick_name student_name, sp.major_name, sp.class_name, j.job_name, j.company_name, r.resume_title, sp.skill_tags from emp_job_application a left join sys_user su on su.user_id = a.student_user_id left join emp_student_profile sp on sp.user_id = a.student_user_id and sp.del_flag = '0' left join emp_job j on j.job_id = a.job_id left join emp_resume r on r.resume_id = a.resume_id where a.del_flag = '0'");
        List<Object> params = new ArrayList<>();
        if (query.getStudentUserId() != null)
        {
            sql.append(" and a.student_user_id = ?");
            params.add(query.getStudentUserId());
        }
        if (query.getEnterpriseUserId() != null)
        {
            sql.append(" and a.enterprise_user_id = ?");
            params.add(query.getEnterpriseUserId());
        }
        if (StringUtils.isNotEmpty(query.getApplyStatus()))
        {
            sql.append(" and a.apply_status = ?");
            params.add(query.getApplyStatus());
        }
        sql.append(" order by a.applied_time desc, a.application_id desc");
        return jdbcTemplate.query(sql.toString(), APPLICATION_MAPPER, params.toArray());
    }

    @Transactional
    public EmploymentJobApplication updateApplicationStatus(EmploymentJobApplication application, String operator)
    {
        jdbcTemplate.update("update emp_job_application set apply_status = ?, progress_remark = ?, feedback_time = now(), update_by = ?, update_time = now(), remark = ? where application_id = ?",
            application.getApplyStatus(), application.getProgressRemark(), operator, application.getRemark(), application.getApplicationId());
        EmploymentJobApplication current = getApplication(application.getApplicationId());
        if (current != null)
        {
            rebuildRisk(current.getStudentUserId(), operator);
        }
        return current;
    }
    public List<EmploymentInterview> listInterviews(EmploymentInterview query)
    {
        StringBuilder sql = new StringBuilder("select i.*, su.nick_name student_name, j.job_name from emp_interview i left join sys_user su on su.user_id = i.student_user_id left join emp_job j on j.job_id = i.job_id where i.del_flag = '0'");
        List<Object> params = new ArrayList<>();
        if (query.getStudentUserId() != null) { sql.append(" and i.student_user_id = ?"); params.add(query.getStudentUserId()); }
        if (query.getEnterpriseUserId() != null) { sql.append(" and i.enterprise_user_id = ?"); params.add(query.getEnterpriseUserId()); }
        if (StringUtils.isNotEmpty(query.getInterviewStatus())) { sql.append(" and i.interview_status = ?"); params.add(query.getInterviewStatus()); }
        sql.append(" order by i.interview_time desc, i.interview_id desc");
        return jdbcTemplate.query(sql.toString(), INTERVIEW_MAPPER, params.toArray());
    }

    @Transactional
    public EmploymentInterview saveInterview(EmploymentInterview interview, String operator)
    {
        if (interview.getApplicationId() != null)
        {
            EmploymentJobApplication application = getApplication(interview.getApplicationId());
            if (application == null)
            {
                throw new RuntimeException("应聘记录不存在");
            }
            if (interview.getJobId() == null)
            {
                interview.setJobId(application.getJobId());
            }
            if (interview.getStudentUserId() == null)
            {
                interview.setStudentUserId(application.getStudentUserId());
            }
            if (interview.getEnterpriseUserId() == null)
            {
                interview.setEnterpriseUserId(application.getEnterpriseUserId());
            }
        }
        if (interview.getJobId() == null || interview.getStudentUserId() == null || interview.getEnterpriseUserId() == null)
        {
            throw new RuntimeException("面试记录缺少必要的关联信息");
        }
        if (interview.getInterviewId() == null)
        {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                java.sql.PreparedStatement ps = connection.prepareStatement(
                    "insert into emp_interview(application_id, job_id, student_user_id, enterprise_user_id, interview_time, interview_type, interview_location, interview_status, student_feedback, evaluation, create_by, create_time, remark) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?)",
                    new String[] { "interview_id" });
                ps.setLong(1, interview.getApplicationId());
                ps.setLong(2, interview.getJobId());
                ps.setLong(3, interview.getStudentUserId());
                ps.setLong(4, interview.getEnterpriseUserId());
                setDate(ps, 5, interview.getInterviewTime());
                ps.setString(6, interview.getInterviewType());
                ps.setString(7, interview.getInterviewLocation());
                ps.setString(8, defaultString(interview.getInterviewStatus(), "pending"));
                ps.setString(9, interview.getStudentFeedback());
                ps.setString(10, interview.getEvaluation());
                ps.setString(11, operator);
                ps.setString(12, interview.getRemark());
                return ps;
            }, keyHolder);
            interview.setInterviewId(keyHolder.getKey().longValue());
        }
        else
        {
            jdbcTemplate.update("update emp_interview set interview_time = ?, interview_type = ?, interview_location = ?, interview_status = ?, student_feedback = ?, evaluation = ?, update_by = ?, update_time = now(), remark = ? where interview_id = ?",
                toSqlDate(interview.getInterviewTime()), interview.getInterviewType(), interview.getInterviewLocation(), interview.getInterviewStatus(), interview.getStudentFeedback(), interview.getEvaluation(), operator, interview.getRemark(), interview.getInterviewId());
        }
        if (interview.getApplicationId() != null)
        {
            jdbcTemplate.update("update emp_job_application set apply_status = 'interview', feedback_time = now(), update_by = ?, update_time = now() where application_id = ?", operator, interview.getApplicationId());
        }
        rebuildRisk(interview.getStudentUserId(), operator);
        return listInterviews(interview).stream().findFirst().orElse(interview);
    }

    public List<EmploymentStatus> listStatuses(EmploymentStatus query)
    {
        StringBuilder sql = new StringBuilder("select es.*, su.nick_name student_name, sp.student_no, sp.major_name, sp.class_name, tu.nick_name teacher_name, m.file_url evidence_file_url from emp_employment_status es left join sys_user su on su.user_id = es.student_user_id left join emp_student_profile sp on sp.user_id = es.student_user_id and sp.del_flag = '0' left join sys_user tu on tu.user_id = es.teacher_user_id left join emp_material m on m.material_id = es.evidence_material_id where es.del_flag = '0'");
        List<Object> params = new ArrayList<>();
        if (query.getStudentUserId() != null) { sql.append(" and es.student_user_id = ?"); params.add(query.getStudentUserId()); }
        if (query.getTeacherUserId() != null) { sql.append(" and es.teacher_user_id = ?"); params.add(query.getTeacherUserId()); }
        if (StringUtils.isNotEmpty(query.getCurrentStatus())) { sql.append(" and es.current_status = ?"); params.add(query.getCurrentStatus()); }
        if (StringUtils.isNotEmpty(query.getDifficultyLevel())) { sql.append(" and es.difficulty_level = ?"); params.add(query.getDifficultyLevel()); }
        if (StringUtils.isNotEmpty(query.getWarningLevel())) { sql.append(" and es.warning_level = ?"); params.add(query.getWarningLevel()); }
        if (StringUtils.isNotEmpty(query.getMajorName())) { sql.append(" and sp.major_name = ?"); params.add(query.getMajorName()); }
        if (StringUtils.isNotEmpty(query.getClassName())) { sql.append(" and sp.class_name = ?"); params.add(query.getClassName()); }
        if (StringUtils.isNotEmpty(query.getStudentName())) { sql.append(" and su.nick_name like ?"); params.add("%" + query.getStudentName() + "%"); }
        sql.append(" order by es.risk_score desc, es.status_id desc");
        return jdbcTemplate.query(sql.toString(), STATUS_MAPPER, params.toArray());
    }

    @Transactional
    public EmploymentStatus saveStatus(EmploymentStatus status, String operator)
    {
        Integer count = jdbcTemplate.queryForObject("select count(1) from emp_employment_status where student_user_id = ? and del_flag = '0'", Integer.class, status.getStudentUserId());
        if (count != null && count > 0)
        {
            jdbcTemplate.update("update emp_employment_status set teacher_user_id = ?, current_status = ?, difficulty_level = ?, warning_level = ?, employment_company = ?, position_name = ?, salary_amount = ?, progress_stage = ?, evidence_material_id = ?, risk_score = ?, reason_note = ?, last_follow_time = ?, update_by = ?, update_time = now(), remark = ? where student_user_id = ?",
                status.getTeacherUserId(), status.getCurrentStatus(), status.getDifficultyLevel(), status.getWarningLevel(), status.getEmploymentCompany(), status.getPositionName(), status.getSalaryAmount(), status.getProgressStage(), status.getEvidenceMaterialId(), status.getRiskScore(), status.getReasonNote(), toSqlDate(status.getLastFollowTime()), operator, status.getRemark(), status.getStudentUserId());
        }
        else
        {
            jdbcTemplate.update("insert into emp_employment_status(student_user_id, teacher_user_id, current_status, difficulty_level, warning_level, employment_company, position_name, salary_amount, progress_stage, evidence_material_id, risk_score, reason_note, last_follow_time, create_by, create_time, remark) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?)",
                status.getStudentUserId(), status.getTeacherUserId(), defaultString(status.getCurrentStatus(), "unemployed"), defaultString(status.getDifficultyLevel(), "normal"), defaultString(status.getWarningLevel(), "low"), status.getEmploymentCompany(), status.getPositionName(), status.getSalaryAmount(), status.getProgressStage(), status.getEvidenceMaterialId(), status.getRiskScore(), status.getReasonNote(), toSqlDate(status.getLastFollowTime()), operator, status.getRemark());
        }
        jdbcTemplate.update("update emp_student_profile set employment_status = ?, difficulty_level = ?, warning_level = ?, update_by = ?, update_time = now() where user_id = ?",
            defaultString(status.getCurrentStatus(), "unemployed"), defaultString(status.getDifficultyLevel(), "normal"), defaultString(status.getWarningLevel(), "low"), operator, status.getStudentUserId());
        rebuildRisk(status.getStudentUserId(), operator);
        return getStatus(status.getStudentUserId());
    }

    public EmploymentStatus getStatus(Long studentUserId)
    {
        EmploymentStatus query = new EmploymentStatus();
        query.setStudentUserId(studentUserId);
        List<EmploymentStatus> list = listStatuses(query);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<EmploymentAssistanceRecord> listAssistanceRecords(EmploymentAssistanceRecord query)
    {
        StringBuilder sql = new StringBuilder("select ar.*, su.nick_name student_name, hu.nick_name helper_name from emp_assistance_record ar left join sys_user su on su.user_id = ar.student_user_id left join sys_user hu on hu.user_id = ar.helper_user_id where ar.del_flag = '0'");
        List<Object> params = new ArrayList<>();
        if (query.getStudentUserId() != null) { sql.append(" and ar.student_user_id = ?"); params.add(query.getStudentUserId()); }
        if (query.getHelperUserId() != null) { sql.append(" and ar.helper_user_id = ?"); params.add(query.getHelperUserId()); }
        if (StringUtils.isNotEmpty(query.getActivityType())) { sql.append(" and ar.activity_type = ?"); params.add(query.getActivityType()); }
        sql.append(" order by ar.record_time desc, ar.record_id desc");
        return jdbcTemplate.query(sql.toString(), ASSISTANCE_MAPPER, params.toArray());
    }

    @Transactional
    public EmploymentAssistanceRecord saveAssistanceRecord(EmploymentAssistanceRecord record, String operator)
    {
        if (record.getRecordId() == null)
        {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                java.sql.PreparedStatement ps = connection.prepareStatement(
                    "insert into emp_assistance_record(student_user_id, helper_user_id, helper_role, activity_title, activity_type, activity_content, result_summary, next_plan, record_time, create_by, create_time, remark) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?)",
                    new String[] { "record_id" });
                ps.setLong(1, record.getStudentUserId());
                ps.setLong(2, record.getHelperUserId());
                ps.setString(3, record.getHelperRole());
                ps.setString(4, record.getActivityTitle());
                ps.setString(5, record.getActivityType());
                ps.setString(6, record.getActivityContent());
                ps.setString(7, record.getResultSummary());
                ps.setString(8, record.getNextPlan());
                setDate(ps, 9, record.getRecordTime());
                ps.setString(10, operator);
                ps.setString(11, record.getRemark());
                return ps;
            }, keyHolder);
            record.setRecordId(keyHolder.getKey().longValue());
        }
        else
        {
            jdbcTemplate.update("update emp_assistance_record set activity_title = ?, activity_type = ?, activity_content = ?, result_summary = ?, next_plan = ?, record_time = ?, update_by = ?, update_time = now(), remark = ? where record_id = ?",
                record.getActivityTitle(), record.getActivityType(), record.getActivityContent(), record.getResultSummary(), record.getNextPlan(), toSqlDate(record.getRecordTime()), operator, record.getRemark(), record.getRecordId());
        }
        rebuildRisk(record.getStudentUserId(), operator);
        return listAssistanceRecords(record).stream().findFirst().orElse(record);
    }

    public List<EmploymentWarningVO> listWarnings(Long teacherUserId, EmploymentWarningVO query)
    {
        StringBuilder sql = new StringBuilder("select es.student_user_id, su.nick_name student_name, sp.student_no, sp.major_name, sp.class_name, tu.nick_name teacher_name, es.warning_level, es.difficulty_level, es.risk_score, es.reason_note, (select count(1) from emp_job_application a where a.student_user_id = es.student_user_id and a.del_flag = '0') application_count, (select count(1) from emp_resume r where r.user_id = es.student_user_id and r.del_flag = '0') resume_version_count from emp_employment_status es left join sys_user su on su.user_id = es.student_user_id left join emp_student_profile sp on sp.user_id = es.student_user_id and sp.del_flag = '0' left join sys_user tu on tu.user_id = es.teacher_user_id where es.del_flag = '0'");
        List<Object> params = new ArrayList<>();
        if (teacherUserId != null) { sql.append(" and es.teacher_user_id = ?"); params.add(teacherUserId); }
        if (query != null && StringUtils.isNotEmpty(query.getWarningLevel())) { sql.append(" and es.warning_level = ?"); params.add(query.getWarningLevel()); }
        if (query != null && StringUtils.isNotEmpty(query.getDifficultyLevel())) { sql.append(" and es.difficulty_level = ?"); params.add(query.getDifficultyLevel()); }
        sql.append(" order by es.risk_score desc, es.student_user_id desc");
        return jdbcTemplate.query(sql.toString(), WARNING_MAPPER, params.toArray());
    }

    public EmploymentDashboardVO buildDashboard(Long teacherUserId, Long enterpriseUserId)
    {
        EmploymentDashboardVO dashboard = new EmploymentDashboardVO();
        dashboard.setTotalStudents(queryInt("select count(1) from emp_student_profile where del_flag = '0'" + (teacherUserId != null ? " and teacher_user_id = ?" : ""), teacherUserId));
        dashboard.setEmployedStudents(queryInt("select count(1) from emp_employment_status es left join emp_student_profile sp on sp.user_id = es.student_user_id and sp.del_flag = '0' where es.del_flag = '0' and es.current_status = 'employed'" + (teacherUserId != null ? " and sp.teacher_user_id = ?" : ""), teacherUserId));
        dashboard.setUnemployedStudents(queryInt("select count(1) from emp_employment_status es left join emp_student_profile sp on sp.user_id = es.student_user_id and sp.del_flag = '0' where es.del_flag = '0' and es.current_status = 'unemployed'" + (teacherUserId != null ? " and sp.teacher_user_id = ?" : ""), teacherUserId));
        dashboard.setDifficultStudents(queryInt("select count(1) from emp_employment_status es left join emp_student_profile sp on sp.user_id = es.student_user_id and sp.del_flag = '0' where es.del_flag = '0' and es.current_status = 'difficult'" + (teacherUserId != null ? " and sp.teacher_user_id = ?" : ""), teacherUserId));
        dashboard.setPendingMaterials(queryInt("select count(1) from emp_material m left join emp_student_profile sp on sp.user_id = m.user_id and sp.del_flag = '0' where m.del_flag = '0' and m.review_status = 'pending'" + (teacherUserId != null ? " and sp.teacher_user_id = ?" : ""), teacherUserId));
        dashboard.setPendingJobs(queryInt("select count(1) from emp_job where del_flag = '0' and audit_status = 'pending'"));
        dashboard.setTotalJobs(queryInt("select count(1) from emp_job where del_flag = '0'" + (enterpriseUserId != null ? " and enterprise_user_id = ?" : ""), enterpriseUserId));
        dashboard.setTotalInterviews(queryInt("select count(1) from emp_interview i left join emp_student_profile sp on sp.user_id = i.student_user_id and sp.del_flag = '0' where i.del_flag = '0'"
            + (teacherUserId != null ? " and sp.teacher_user_id = ?" : "") + (enterpriseUserId != null ? " and i.enterprise_user_id = ?" : ""), teacherUserId, enterpriseUserId));
        dashboard.setStatusPie(jdbcTemplate.queryForList("select es.current_status name, count(1) value from emp_employment_status es left join emp_student_profile sp on sp.user_id = es.student_user_id and sp.del_flag = '0' where es.del_flag = '0'" + (teacherUserId != null ? " and sp.teacher_user_id = ?" : "") + " group by es.current_status order by value desc", teacherUserId == null ? new Object[]{} : new Object[]{teacherUserId}));
        dashboard.setTrend(jdbcTemplate.queryForList("select date_format(update_time, '%Y-%m') name, count(1) value from emp_employment_status where del_flag = '0' group by date_format(update_time, '%Y-%m') order by name asc limit 6"));
        dashboard.setIndustry(jdbcTemplate.queryForList("select category_name name, count(1) value from emp_job where del_flag = '0' and audit_status = 'approved' group by category_name order by value desc limit 8"));
        dashboard.setTopWarnings(jdbcTemplate.queryForList("select su.nick_name name, es.risk_score value, es.reason_note reason from emp_employment_status es left join sys_user su on su.user_id = es.student_user_id left join emp_student_profile sp on sp.user_id = es.student_user_id and sp.del_flag = '0' where es.del_flag = '0'" + (teacherUserId != null ? " and sp.teacher_user_id = ?" : "") + " order by es.risk_score desc limit 5", teacherUserId == null ? new Object[]{} : new Object[]{teacherUserId}));
        return dashboard;
    }

    public void rebuildRisk(Long studentUserId, String operator)
    {
        EmploymentStudentProfile profile = getStudentProfile(studentUserId);
        EmploymentStatus status = getStatus(studentUserId);
        int resumeCount = queryInt("select count(1) from emp_resume where user_id = ? and del_flag = '0'", studentUserId);
        int applicationCount = queryInt("select count(1) from emp_job_application where student_user_id = ? and del_flag = '0'", studentUserId);
        int interviewCount = queryInt("select count(1) from emp_interview where student_user_id = ? and del_flag = '0'", studentUserId);
        double risk = 20;
        if (profile.getGpa() != null && profile.getGpa() < 3) { risk += 15; }
        if (profile.getGpa() != null && profile.getGpa() < 2.5) { risk += 10; }
        if (resumeCount == 0) { risk += 25; } else if (resumeCount == 1) { risk += 5; }
        if (applicationCount == 0) { risk += 20; } else if (applicationCount < 3) { risk += 10; }
        if (interviewCount > 0) { risk -= 10; }
        String currentStatus = status == null ? defaultString(profile.getEmploymentStatus(), "unemployed") : status.getCurrentStatus();
        if ("unemployed".equals(currentStatus)) { risk += 15; }
        if ("difficult".equals(currentStatus)) { risk += 20; }
        if ("employed".equals(currentStatus)) { risk = Math.max(10, risk - 30); }
        risk = Math.max(5, Math.min(98, risk));
        String warning = risk >= 80 ? "high" : risk >= 60 ? "medium" : "low";
        String difficulty = risk >= 70 ? "key" : risk >= 50 ? "attention" : "normal";
        String reason = "GPA:" + (profile.getGpa() == null ? "未知" : new DecimalFormat("0.00").format(profile.getGpa())) + "，简历版本:" + resumeCount + "，投递次数:" + applicationCount + "，面试次数:" + interviewCount;
        if (status == null)
        {
            jdbcTemplate.update("insert into emp_employment_status(student_user_id, teacher_user_id, current_status, difficulty_level, warning_level, risk_score, reason_note, last_follow_time, create_by, create_time, remark) values (?, ?, ?, ?, ?, ?, ?, now(), ?, now(), ?)",
                studentUserId, profile.getTeacherUserId(), currentStatus, difficulty, warning, risk, reason, operator, "auto");
        }
        else
        {
            jdbcTemplate.update("update emp_employment_status set teacher_user_id = ?, current_status = ?, difficulty_level = ?, warning_level = ?, risk_score = ?, reason_note = ?, last_follow_time = now(), update_by = ?, update_time = now() where student_user_id = ?",
                profile.getTeacherUserId(), currentStatus, difficulty, warning, risk, reason, operator, studentUserId);
        }
        jdbcTemplate.update("update emp_student_profile set employment_status = ?, difficulty_level = ?, warning_level = ?, update_by = ?, update_time = now() where user_id = ?",
            currentStatus, difficulty, warning, operator, studentUserId);
    }

    private double calculateMatchScore(EmploymentJob job, EmploymentResume resume)
    {
        List<String> jobTokens = tokenize(joinText(job.getSkillKeywords(), job.getJobRequire(), job.getJobDesc()));
        List<String> resumeTokens = tokenize(joinText(resume.getExtractedKeywords(), resume.getSkillList(), resume.getSummary(), resume.getPracticeExperience()));
        if (jobTokens.isEmpty() || resumeTokens.isEmpty()) { return 35D; }
        long overlap = jobTokens.stream().filter(resumeTokens::contains).count();
        double ratio = (double) overlap / (double) jobTokens.size();
        return Math.max(35D, Math.min(98D, 35D + ratio * 63D));
    }

    private List<String> tokenize(String text)
    {
        if (StringUtils.isEmpty(text)) { return Collections.emptyList(); }
        return Arrays.stream(text.replace("，", ",").replace("；", ",").replace("/", ",").split("[,\\s]+"))
            .map(String::trim).filter(StringUtils::isNotEmpty).distinct().collect(Collectors.toList());
    }

    private String joinText(String... values)
    {
        return Arrays.stream(values).filter(StringUtils::isNotEmpty).collect(Collectors.joining(" "));
    }

    private String defaultString(String value, String defaultValue)
    {
        return StringUtils.isEmpty(value) ? defaultValue : value;
    }

    private Integer queryInt(String sql, Object... args)
    {
        Integer value = jdbcTemplate.queryForObject(sql, Integer.class, trimNullArgs(args));
        return value == null ? 0 : value;
    }

    private Object[] trimNullArgs(Object... args)
    {
        if (args == null) { return new Object[] {}; }
        List<Object> values = Arrays.stream(args).filter(item -> item != null).collect(Collectors.toList());
        return values.toArray();
    }

    private java.sql.Timestamp toSqlDate(Date date)
    {
        return date == null ? null : new java.sql.Timestamp(date.getTime());
    }

    private void setDate(java.sql.PreparedStatement ps, int index, Date date) throws SQLException
    {
        if (date == null) { ps.setTimestamp(index, null); } else { ps.setTimestamp(index, new java.sql.Timestamp(date.getTime())); }
    }

    private void setLong(java.sql.PreparedStatement ps, int index, Long value) throws SQLException
    {
        if (value == null) { ps.setObject(index, null); } else { ps.setLong(index, value); }
    }

    private void setDouble(java.sql.PreparedStatement ps, int index, Double value) throws SQLException
    {
        if (value == null) { ps.setObject(index, null); } else { ps.setDouble(index, value); }
    }
}




