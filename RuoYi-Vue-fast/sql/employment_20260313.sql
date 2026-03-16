SET NAMES utf8mb4;

USE ry_employment;

CREATE TABLE IF NOT EXISTS emp_student_profile (
  profile_id bigint NOT NULL AUTO_INCREMENT,
  user_id bigint NOT NULL,
  teacher_user_id bigint DEFAULT NULL,
  student_no varchar(32) DEFAULT NULL,
  major_name varchar(64) DEFAULT NULL,
  class_name varchar(64) DEFAULT NULL,
  graduation_year int DEFAULT NULL,
  gpa decimal(3,2) DEFAULT NULL,
  expected_city varchar(64) DEFAULT NULL,
  expected_salary decimal(10,2) DEFAULT NULL,
  job_intention varchar(128) DEFAULT NULL,
  skill_tags varchar(512) DEFAULT NULL,
  contact_address varchar(255) DEFAULT NULL,
  employment_status varchar(16) DEFAULT 'unemployed',
  difficulty_level varchar(16) DEFAULT 'normal',
  warning_level varchar(16) DEFAULT 'low',
  del_flag char(1) DEFAULT '0',
  create_by varchar(64) DEFAULT '',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_by varchar(64) DEFAULT '',
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  remark varchar(500) DEFAULT NULL,
  PRIMARY KEY (profile_id),
  UNIQUE KEY uk_emp_student_profile_user (user_id),
  KEY idx_emp_student_profile_teacher (teacher_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='student profile';

CREATE TABLE IF NOT EXISTS emp_enterprise_profile (
  enterprise_id bigint NOT NULL AUTO_INCREMENT,
  user_id bigint NOT NULL,
  company_name varchar(128) NOT NULL,
  industry_name varchar(64) DEFAULT NULL,
  city varchar(64) DEFAULT NULL,
  address varchar(255) DEFAULT NULL,
  company_size varchar(32) DEFAULT NULL,
  contact_name varchar(64) DEFAULT NULL,
  contact_phone varchar(32) DEFAULT NULL,
  company_intro text,
  certification_status varchar(16) DEFAULT 'pending',
  license_file_url varchar(255) DEFAULT NULL,
  del_flag char(1) DEFAULT '0',
  create_by varchar(64) DEFAULT '',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_by varchar(64) DEFAULT '',
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  remark varchar(500) DEFAULT NULL,
  PRIMARY KEY (enterprise_id),
  UNIQUE KEY uk_emp_enterprise_profile_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='enterprise profile';

CREATE TABLE IF NOT EXISTS emp_resume (
  resume_id bigint NOT NULL AUTO_INCREMENT,
  user_id bigint NOT NULL,
  resume_title varchar(128) NOT NULL,
  version_no int DEFAULT 1,
  template_name varchar(32) DEFAULT 'classic',
  summary text,
  education_experience text,
  practice_experience text,
  campus_experience text,
  skill_list text,
  certificate_list text,
  self_evaluation text,
  optimize_suggestion text,
  extracted_keywords varchar(1000) DEFAULT NULL,
  is_default char(1) DEFAULT 'Y',
  del_flag char(1) DEFAULT '0',
  create_by varchar(64) DEFAULT '',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_by varchar(64) DEFAULT '',
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  remark varchar(500) DEFAULT NULL,
  PRIMARY KEY (resume_id),
  KEY idx_emp_resume_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='resume';

CREATE TABLE IF NOT EXISTS emp_material (
  material_id bigint NOT NULL AUTO_INCREMENT,
  user_id bigint NOT NULL,
  material_type varchar(32) NOT NULL,
  material_name varchar(128) NOT NULL,
  file_name varchar(255) DEFAULT NULL,
  file_url varchar(255) DEFAULT NULL,
  review_status varchar(16) DEFAULT 'pending',
  review_comment varchar(500) DEFAULT NULL,
  reviewer_id bigint DEFAULT NULL,
  review_time datetime DEFAULT NULL,
  del_flag char(1) DEFAULT '0',
  create_by varchar(64) DEFAULT '',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_by varchar(64) DEFAULT '',
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  remark varchar(500) DEFAULT NULL,
  PRIMARY KEY (material_id),
  KEY idx_emp_material_user (user_id),
  KEY idx_emp_material_review_status (review_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='employment material';

CREATE TABLE IF NOT EXISTS emp_job (
  job_id bigint NOT NULL AUTO_INCREMENT,
  enterprise_user_id bigint NOT NULL,
  enterprise_id bigint DEFAULT NULL,
  company_name varchar(128) NOT NULL,
  job_name varchar(128) NOT NULL,
  category_name varchar(64) DEFAULT NULL,
  city varchar(64) DEFAULT NULL,
  salary_min decimal(10,2) DEFAULT NULL,
  salary_max decimal(10,2) DEFAULT NULL,
  education_requirement varchar(64) DEFAULT NULL,
  experience_requirement varchar(64) DEFAULT NULL,
  job_desc text,
  job_require text,
  skill_keywords varchar(500) DEFAULT NULL,
  job_status varchar(16) DEFAULT 'draft',
  audit_status varchar(16) DEFAULT 'pending',
  publish_time datetime DEFAULT NULL,
  deadline datetime DEFAULT NULL,
  del_flag char(1) DEFAULT '0',
  create_by varchar(64) DEFAULT '',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_by varchar(64) DEFAULT '',
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  remark varchar(500) DEFAULT NULL,
  PRIMARY KEY (job_id),
  KEY idx_emp_job_enterprise_user (enterprise_user_id),
  KEY idx_emp_job_audit_status (audit_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='job';

CREATE TABLE IF NOT EXISTS emp_job_application (
  application_id bigint NOT NULL AUTO_INCREMENT,
  job_id bigint NOT NULL,
  student_user_id bigint NOT NULL,
  resume_id bigint DEFAULT NULL,
  enterprise_user_id bigint NOT NULL,
  apply_status varchar(16) DEFAULT 'applied',
  match_score decimal(5,2) DEFAULT 0.00,
  progress_remark varchar(500) DEFAULT NULL,
  applied_time datetime DEFAULT CURRENT_TIMESTAMP,
  feedback_time datetime DEFAULT NULL,
  del_flag char(1) DEFAULT '0',
  create_by varchar(64) DEFAULT '',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_by varchar(64) DEFAULT '',
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  remark varchar(500) DEFAULT NULL,
  PRIMARY KEY (application_id),
  UNIQUE KEY uk_emp_job_application_unique (job_id, student_user_id),
  KEY idx_emp_job_application_student (student_user_id),
  KEY idx_emp_job_application_enterprise (enterprise_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='job application';

CREATE TABLE IF NOT EXISTS emp_interview (
  interview_id bigint NOT NULL AUTO_INCREMENT,
  application_id bigint NOT NULL,
  job_id bigint NOT NULL,
  student_user_id bigint NOT NULL,
  enterprise_user_id bigint NOT NULL,
  interview_time datetime DEFAULT NULL,
  interview_type varchar(32) DEFAULT NULL,
  interview_location varchar(255) DEFAULT NULL,
  interview_status varchar(16) DEFAULT 'pending',
  student_feedback varchar(500) DEFAULT NULL,
  evaluation varchar(500) DEFAULT NULL,
  del_flag char(1) DEFAULT '0',
  create_by varchar(64) DEFAULT '',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_by varchar(64) DEFAULT '',
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  remark varchar(500) DEFAULT NULL,
  PRIMARY KEY (interview_id),
  KEY idx_emp_interview_student (student_user_id),
  KEY idx_emp_interview_enterprise (enterprise_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='interview';

CREATE TABLE IF NOT EXISTS emp_employment_status (
  status_id bigint NOT NULL AUTO_INCREMENT,
  student_user_id bigint NOT NULL,
  teacher_user_id bigint DEFAULT NULL,
  current_status varchar(16) DEFAULT 'unemployed',
  difficulty_level varchar(16) DEFAULT 'normal',
  warning_level varchar(16) DEFAULT 'low',
  employment_company varchar(128) DEFAULT NULL,
  position_name varchar(128) DEFAULT NULL,
  salary_amount decimal(10,2) DEFAULT NULL,
  progress_stage varchar(32) DEFAULT 'resume',
  evidence_material_id bigint DEFAULT NULL,
  risk_score decimal(5,2) DEFAULT 0.00,
  reason_note varchar(500) DEFAULT NULL,
  last_follow_time datetime DEFAULT NULL,
  del_flag char(1) DEFAULT '0',
  create_by varchar(64) DEFAULT '',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_by varchar(64) DEFAULT '',
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  remark varchar(500) DEFAULT NULL,
  PRIMARY KEY (status_id),
  UNIQUE KEY uk_emp_employment_status_student (student_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='employment status';

CREATE TABLE IF NOT EXISTS emp_assistance_record (
  record_id bigint NOT NULL AUTO_INCREMENT,
  student_user_id bigint NOT NULL,
  helper_user_id bigint NOT NULL,
  helper_role varchar(32) DEFAULT NULL,
  activity_title varchar(128) NOT NULL,
  activity_type varchar(32) DEFAULT NULL,
  activity_content text,
  result_summary varchar(500) DEFAULT NULL,
  next_plan varchar(500) DEFAULT NULL,
  record_time datetime DEFAULT CURRENT_TIMESTAMP,
  del_flag char(1) DEFAULT '0',
  create_by varchar(64) DEFAULT '',
  create_time datetime DEFAULT CURRENT_TIMESTAMP,
  update_by varchar(64) DEFAULT '',
  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  remark varchar(500) DEFAULT NULL,
  PRIMARY KEY (record_id),
  KEY idx_emp_assistance_record_student (student_user_id),
  KEY idx_emp_assistance_record_helper (helper_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='assistance record';

UPDATE sys_config
SET config_value = 'true', update_time = NOW(), update_by = 'admin'
WHERE config_key = 'sys.account.registerUser';

INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '就业材料类型', 'emp_material_type', '0', 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'emp_material_type');

INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '审核状态', 'emp_review_status', '0', 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'emp_review_status');

INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '就业状态', 'emp_employment_status', '0', 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'emp_employment_status');

INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '困难等级', 'emp_difficulty_level', '0', 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'emp_difficulty_level');

INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '预警等级', 'emp_warning_level', '0', 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'emp_warning_level');

INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '岗位状态', 'emp_job_status', '0', 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'emp_job_status');

INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '岗位审核状态', 'emp_job_audit_status', '0', 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'emp_job_audit_status');

INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '应聘状态', 'emp_apply_status', '0', 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'emp_apply_status');

INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '面试状态', 'emp_interview_status', '0', 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'emp_interview_status');

INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark)
SELECT '企业认证状态', 'emp_cert_status', '0', 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'emp_cert_status');

DELETE FROM sys_dict_data WHERE dict_type IN (
  'emp_material_type', 'emp_review_status', 'emp_employment_status', 'emp_difficulty_level',
  'emp_warning_level', 'emp_job_status', 'emp_job_audit_status', 'emp_apply_status',
  'emp_interview_status', 'emp_cert_status'
);

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '三方协议', 'agreement', 'emp_material_type', '', 'primary', 'N', '0', 'admin', NOW(), ''),
(2, '录用通知', 'offer', 'emp_material_type', '', 'success', 'N', '0', 'admin', NOW(), ''),
(3, '就业证明', 'employment_cert', 'emp_material_type', '', 'warning', 'N', '0', 'admin', NOW(), ''),
(4, '企业资质', 'license', 'emp_material_type', '', 'info', 'N', '0', 'admin', NOW(), ''),
(1, '待审核', 'pending', 'emp_review_status', '', 'warning', 'N', '0', 'admin', NOW(), ''),
(2, '已通过', 'approved', 'emp_review_status', '', 'success', 'N', '0', 'admin', NOW(), ''),
(3, '已驳回', 'rejected', 'emp_review_status', '', 'danger', 'N', '0', 'admin', NOW(), ''),
(1, '未就业', 'unemployed', 'emp_employment_status', '', 'danger', 'N', '0', 'admin', NOW(), ''),
(2, '已就业', 'employed', 'emp_employment_status', '', 'success', 'N', '0', 'admin', NOW(), ''),
(3, '困难就业', 'difficult', 'emp_employment_status', '', 'warning', 'N', '0', 'admin', NOW(), ''),
(1, '正常', 'normal', 'emp_difficulty_level', '', 'info', 'N', '0', 'admin', NOW(), ''),
(2, '关注', 'attention', 'emp_difficulty_level', '', 'warning', 'N', '0', 'admin', NOW(), ''),
(3, '重点', 'key', 'emp_difficulty_level', '', 'danger', 'N', '0', 'admin', NOW(), ''),
(1, '低', 'low', 'emp_warning_level', '', 'info', 'N', '0', 'admin', NOW(), ''),
(2, '中', 'medium', 'emp_warning_level', '', 'warning', 'N', '0', 'admin', NOW(), ''),
(3, '高', 'high', 'emp_warning_level', '', 'danger', 'N', '0', 'admin', NOW(), ''),
(1, '草稿', 'draft', 'emp_job_status', '', 'info', 'N', '0', 'admin', NOW(), ''),
(2, '招聘中', 'published', 'emp_job_status', '', 'success', 'N', '0', 'admin', NOW(), ''),
(3, '已下架', 'offline', 'emp_job_status', '', 'danger', 'N', '0', 'admin', NOW(), ''),
(1, '待审核', 'pending', 'emp_job_audit_status', '', 'warning', 'N', '0', 'admin', NOW(), ''),
(2, '已通过', 'approved', 'emp_job_audit_status', '', 'success', 'N', '0', 'admin', NOW(), ''),
(3, '已驳回', 'rejected', 'emp_job_audit_status', '', 'danger', 'N', '0', 'admin', NOW(), ''),
(1, '已投递', 'applied', 'emp_apply_status', '', 'primary', 'N', '0', 'admin', NOW(), ''),
(2, '待面试', 'interview', 'emp_apply_status', '', 'warning', 'N', '0', 'admin', NOW(), ''),
(3, '已录用', 'offer', 'emp_apply_status', '', 'success', 'N', '0', 'admin', NOW(), ''),
(4, '未通过', 'rejected', 'emp_apply_status', '', 'danger', 'N', '0', 'admin', NOW(), ''),
(1, '待确认', 'pending', 'emp_interview_status', '', 'warning', 'N', '0', 'admin', NOW(), ''),
(2, '已确认', 'confirmed', 'emp_interview_status', '', 'success', 'N', '0', 'admin', NOW(), ''),
(3, '已完成', 'finished', 'emp_interview_status', '', 'primary', 'N', '0', 'admin', NOW(), ''),
(4, '已取消', 'cancelled', 'emp_interview_status', '', 'danger', 'N', '0', 'admin', NOW(), ''),
(1, '待认证', 'pending', 'emp_cert_status', '', 'warning', 'N', '0', 'admin', NOW(), ''),
(2, '已认证', 'approved', 'emp_cert_status', '', 'success', 'N', '0', 'admin', NOW(), ''),
(3, '已驳回', 'rejected', 'emp_cert_status', '', 'danger', 'N', '0', 'admin', NOW(), '');

INSERT INTO sys_role(role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark)
SELECT '学生端', 'student', 3, '1', 1, 1, '0', '0', 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE role_key = 'student');

INSERT INTO sys_role(role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark)
SELECT '教师端', 'teacher', 4, '1', 1, 1, '0', '0', 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE role_key = 'teacher');

INSERT INTO sys_role(role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark)
SELECT '企业端', 'company', 5, '1', 1, 1, '0', '0', 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_role WHERE role_key = 'company');

DELETE FROM sys_menu WHERE menu_id BETWEEN 2000 AND 2499;

INSERT INTO sys_menu(menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark) VALUES
(2000, '就业管理', 0, 4, 'employment', NULL, '', 'Employment', 1, 0, 'M', '0', '0', '', 'job', 'admin', NOW(), 'employment'),
(2100, '学生端', 2000, 1, 'student', 'ParentView', '', 'EmploymentStudent', 1, 0, 'M', '0', '0', '', 'user', 'admin', NOW(), ''),
(2101, '个人中心', 2100, 1, 'profile', 'employment/student/profile/index', '', 'EmploymentStudentProfile', 1, 0, 'C', '0', '0', 'employment:student:profile', 'user', 'admin', NOW(), ''),
(2102, '简历管理', 2100, 2, 'resume', 'employment/student/resume/index', '', 'EmploymentStudentResume', 1, 0, 'C', '0', '0', 'employment:resume', 'documentation', 'admin', NOW(), ''),
(2103, '材料管理', 2100, 3, 'material', 'employment/student/material/index', '', 'EmploymentStudentMaterial', 1, 0, 'C', '0', '0', 'employment:material', 'upload', 'admin', NOW(), ''),
(2104, '岗位大厅', 2100, 4, 'job', 'employment/student/job/index', '', 'EmploymentStudentJob', 1, 0, 'C', '0', '0', 'employment:job:view', 'tree', 'admin', NOW(), ''),
(2105, '应聘进度', 2100, 5, 'application', 'employment/student/application/index', '', 'EmploymentStudentApplication', 1, 0, 'C', '0', '0', 'employment:application', 'chart', 'admin', NOW(), ''),
(2200, '教师端', 2000, 2, 'teacher', 'ParentView', '', 'EmploymentTeacher', 1, 0, 'M', '0', '0', '', 'education', 'admin', NOW(), ''),
(2201, '就业状态', 2200, 1, 'status', 'employment/teacher/status/index', '', 'EmploymentTeacherStatus', 1, 0, 'C', '0', '0', 'employment:teacher:status', 'list', 'admin', NOW(), ''),
(2202, '帮扶记录', 2200, 2, 'assist', 'employment/teacher/assist/index', '', 'EmploymentTeacherAssist', 1, 0, 'C', '0', '0', 'employment:teacher:assist', 'guide', 'admin', NOW(), ''),
(2203, '数据看板', 2200, 3, 'dashboard', 'employment/teacher/dashboard/index', '', 'EmploymentTeacherDashboard', 1, 0, 'C', '0', '0', 'employment:teacher:dashboard', 'dashboard', 'admin', NOW(), ''),
(2300, '企业端', 2000, 3, 'company', 'ParentView', '', 'EmploymentCompany', 1, 0, 'M', '0', '0', '', 'peoples', 'admin', NOW(), ''),
(2301, '企业资料', 2300, 1, 'profile', 'employment/company/profile/index', '', 'EmploymentCompanyProfile', 1, 0, 'C', '0', '0', 'employment:company:profile', 'peoples', 'admin', NOW(), ''),
(2302, '岗位管理', 2300, 2, 'job', 'employment/company/job/index', '', 'EmploymentCompanyJob', 1, 0, 'C', '0', '0', 'employment:company:job', 'build', 'admin', NOW(), ''),
(2303, '简历筛选', 2300, 3, 'resume', 'employment/company/resume/index', '', 'EmploymentCompanyResume', 1, 0, 'C', '0', '0', 'employment:company:resume', 'search', 'admin', NOW(), ''),
(2304, '面试管理', 2300, 4, 'interview', 'employment/company/interview/index', '', 'EmploymentCompanyInterview', 1, 0, 'C', '0', '0', 'employment:company:interview', 'date', 'admin', NOW(), ''),
(2400, '管理员端', 2000, 4, 'admin', 'ParentView', '', 'EmploymentAdmin', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', NOW(), ''),
(2401, '状态总览', 2400, 1, 'status', 'employment/admin/status/index', '', 'EmploymentAdminStatus', 1, 0, 'C', '0', '0', 'employment:admin:status', 'form', 'admin', NOW(), ''),
(2402, '预警识别', 2400, 2, 'warning', 'employment/admin/warning/index', '', 'EmploymentAdminWarning', 1, 0, 'C', '0', '0', 'employment:admin:warning', 'message', 'admin', NOW(), ''),
(2403, '统计报表', 2400, 3, 'dashboard', 'employment/admin/dashboard/index', '', 'EmploymentAdminDashboard', 1, 0, 'C', '0', '0', 'employment:admin:dashboard', 'chart', 'admin', NOW(), ''),
(2404, '内容审核', 2400, 4, 'audit', 'employment/admin/audit/index', '', 'EmploymentAdminAudit', 1, 0, 'C', '0', '0', 'employment:admin:audit', 'edit', 'admin', NOW(), '');

DELETE rm
FROM sys_role_menu rm
INNER JOIN sys_role r ON rm.role_id = r.role_id
WHERE r.role_key IN ('student', 'teacher', 'company');

INSERT INTO sys_role_menu(role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (2000, 2100, 2101, 2102, 2103, 2104, 2105)
WHERE r.role_key = 'student';

INSERT INTO sys_role_menu(role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (2000, 2200, 2201, 2202, 2203)
WHERE r.role_key = 'teacher';

INSERT INTO sys_role_menu(role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (2000, 2300, 2301, 2302, 2303, 2304)
WHERE r.role_key = 'company';

INSERT INTO sys_role_menu(role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (2000, 2400, 2401, 2402, 2403, 2404)
WHERE r.role_key = 'admin'
AND NOT EXISTS (
  SELECT 1 FROM sys_role_menu x WHERE x.role_id = r.role_id AND x.menu_id = m.menu_id
);

INSERT INTO sys_user(dept_id, user_name, nick_name, user_type, email, phonenumber, sex, password, status, del_flag, login_ip, login_date, create_by, create_time, remark)
SELECT 103, 'student_demo', '学生演示', '00', 'student_demo@ruoyi.vip', '13800000001', '0', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', NOW(), 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE user_name = 'student_demo');

INSERT INTO sys_user(dept_id, user_name, nick_name, user_type, email, phonenumber, sex, password, status, del_flag, login_ip, login_date, create_by, create_time, remark)
SELECT 103, 'teacher_demo', '教师演示', '00', 'teacher_demo@ruoyi.vip', '13800000002', '0', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', NOW(), 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE user_name = 'teacher_demo');

INSERT INTO sys_user(dept_id, user_name, nick_name, user_type, email, phonenumber, sex, password, status, del_flag, login_ip, login_date, create_by, create_time, remark)
SELECT 104, 'company_demo', '企业演示', '00', 'company_demo@ruoyi.vip', '13800000003', '0', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', NOW(), 'admin', NOW(), 'employment'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE user_name = 'company_demo');

DELETE ur
FROM sys_user_role ur
INNER JOIN sys_user u ON ur.user_id = u.user_id
WHERE u.user_name IN ('student_demo', 'teacher_demo', 'company_demo');

INSERT INTO sys_user_role(user_id, role_id)
SELECT u.user_id, r.role_id
FROM sys_user u
JOIN sys_role r ON r.role_key = 'student'
WHERE u.user_name = 'student_demo';

INSERT INTO sys_user_role(user_id, role_id)
SELECT u.user_id, r.role_id
FROM sys_user u
JOIN sys_role r ON r.role_key = 'teacher'
WHERE u.user_name = 'teacher_demo';

INSERT INTO sys_user_role(user_id, role_id)
SELECT u.user_id, r.role_id
FROM sys_user u
JOIN sys_role r ON r.role_key = 'company'
WHERE u.user_name = 'company_demo';

DELETE FROM emp_student_profile WHERE user_id IN (SELECT user_id FROM sys_user WHERE user_name = 'student_demo');
DELETE FROM emp_enterprise_profile WHERE user_id IN (SELECT user_id FROM sys_user WHERE user_name = 'company_demo');
DELETE FROM emp_resume WHERE user_id IN (SELECT user_id FROM sys_user WHERE user_name = 'student_demo');
DELETE FROM emp_material WHERE user_id IN (SELECT user_id FROM sys_user WHERE user_name = 'student_demo');
DELETE FROM emp_job_application WHERE student_user_id IN (SELECT user_id FROM sys_user WHERE user_name = 'student_demo');
DELETE FROM emp_interview WHERE student_user_id IN (SELECT user_id FROM sys_user WHERE user_name = 'student_demo');
DELETE FROM emp_employment_status WHERE student_user_id IN (SELECT user_id FROM sys_user WHERE user_name = 'student_demo');
DELETE FROM emp_assistance_record WHERE student_user_id IN (SELECT user_id FROM sys_user WHERE user_name = 'student_demo');
DELETE FROM emp_job WHERE enterprise_user_id IN (SELECT user_id FROM sys_user WHERE user_name = 'company_demo');

INSERT INTO emp_student_profile(user_id, teacher_user_id, student_no, major_name, class_name, graduation_year, gpa, expected_city, expected_salary, job_intention, skill_tags, contact_address, employment_status, difficulty_level, warning_level, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, '20260001', '软件工程', '软工2201', 2026, 2.65, '上海', 12000.00, 'Java后端开发', 'Java,Spring Boot,MySQL,Vue3,Redis', '上海市浦东新区演示地址', 'difficult', 'attention', 'medium', 'admin', NOW(), 'admin', NOW(), '缺少高频投递'
FROM sys_user su
JOIN sys_user tu ON tu.user_name = 'teacher_demo'
WHERE su.user_name = 'student_demo';

INSERT INTO emp_enterprise_profile(user_id, company_name, industry_name, city, address, company_size, contact_name, contact_phone, company_intro, certification_status, license_file_url, create_by, create_time, update_by, update_time, remark)
SELECT user_id, '上海智聘科技有限公司', '互联网', '上海', '上海市徐汇区示例路99号', '100-499人', '李经理', '13800000003', '专注于校园招聘和企业数字化服务。', 'approved', '/profile/upload/demo-company-license.pdf', 'admin', NOW(), 'admin', NOW(), 'employment'
FROM sys_user
WHERE user_name = 'company_demo';

INSERT INTO emp_resume(user_id, resume_title, version_no, template_name, summary, education_experience, practice_experience, campus_experience, skill_list, certificate_list, self_evaluation, optimize_suggestion, extracted_keywords, is_default, create_by, create_time, update_by, update_time, remark)
SELECT user_id, 'Java开发实习生简历', 1, 'classic', '具备扎实的Java基础和项目实战经验，目标岗位为Java后端开发。', '2022-09 至今 某某大学 软件工程 本科', '2025-07 至 2025-09 参与就业管理系统开发，负责岗位投递与审核模块。', '担任班级学习委员，组织技术分享8次。', 'Java,Spring Boot,MyBatis,MySQL,Redis,Vue3,ECharts', '英语四级,软考中级', '执行力强，学习速度快，能快速理解业务并落地。', '建议在项目经历中突出量化成果，补充接口性能优化和并发场景描述。', 'Java,Spring Boot,MySQL,Vue3,ECharts,接口设计', 'Y', 'admin', NOW(), 'admin', NOW(), 'employment'
FROM sys_user
WHERE user_name = 'student_demo';

INSERT INTO emp_material(user_id, material_type, material_name, file_name, file_url, review_status, review_comment, create_by, create_time, update_by, update_time, remark)
SELECT user_id, 'agreement', '三方协议扫描件', 'agreement-demo.pdf', '/profile/upload/agreement-demo.pdf', 'approved', '材料完整，审核通过', 'admin', NOW(), 'admin', NOW(), 'employment'
FROM sys_user
WHERE user_name = 'student_demo';

INSERT INTO emp_material(user_id, material_type, material_name, file_name, file_url, review_status, review_comment, create_by, create_time, update_by, update_time, remark)
SELECT user_id, 'offer', '录用通知书', 'offer-demo.pdf', '/profile/upload/offer-demo.pdf', 'pending', '', 'admin', NOW(), 'admin', NOW(), 'employment'
FROM sys_user
WHERE user_name = 'student_demo';

INSERT INTO emp_job(enterprise_user_id, enterprise_id, company_name, job_name, category_name, city, salary_min, salary_max, education_requirement, experience_requirement, job_desc, job_require, skill_keywords, job_status, audit_status, publish_time, deadline, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, ep.enterprise_id, ep.company_name, 'Java开发工程师', '后端开发', '上海', 12000.00, 18000.00, '本科及以上', '1年以内', '负责学生就业管理系统后端接口、数据模型与部署支持。', '熟悉Java、Spring Boot、MySQL、Redis，了解Vue生态优先。', 'Java,Spring Boot,MySQL,Redis,Vue3', 'published', 'approved', NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 'admin', NOW(), 'admin', NOW(), 'employment'
FROM sys_user su
JOIN emp_enterprise_profile ep ON ep.user_id = su.user_id
WHERE su.user_name = 'company_demo';

INSERT INTO emp_job(enterprise_user_id, enterprise_id, company_name, job_name, category_name, city, salary_min, salary_max, education_requirement, experience_requirement, job_desc, job_require, skill_keywords, job_status, audit_status, publish_time, deadline, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, ep.enterprise_id, ep.company_name, '前端开发工程师', '前端开发', '上海', 11000.00, 16000.00, '本科及以上', '1年以内', '负责Vue3管理端页面开发、图表展示与交互优化。', '熟悉Vue3、Element Plus、ECharts，具备组件封装能力。', 'Vue3,Element Plus,ECharts,JavaScript,CSS', 'published', 'pending', NOW(), DATE_ADD(NOW(), INTERVAL 20 DAY), 'admin', NOW(), 'admin', NOW(), 'waiting audit'
FROM sys_user su
JOIN emp_enterprise_profile ep ON ep.user_id = su.user_id
WHERE su.user_name = 'company_demo';

INSERT INTO emp_job_application(job_id, student_user_id, resume_id, enterprise_user_id, apply_status, match_score, progress_remark, applied_time, feedback_time, create_by, create_time, update_by, update_time, remark)
SELECT j.job_id, su.user_id, r.resume_id, j.enterprise_user_id, 'interview', 86.00, '简历匹配度较高，已进入面试环节', NOW(), NOW(), 'admin', NOW(), 'admin', NOW(), 'employment'
FROM emp_job j
JOIN sys_user su ON su.user_name = 'student_demo'
JOIN emp_resume r ON r.user_id = su.user_id AND r.is_default = 'Y'
WHERE j.job_name = 'Java开发工程师'
LIMIT 1;

INSERT INTO emp_interview(application_id, job_id, student_user_id, enterprise_user_id, interview_time, interview_type, interview_location, interview_status, student_feedback, evaluation, create_by, create_time, update_by, update_time, remark)
SELECT a.application_id, a.job_id, a.student_user_id, a.enterprise_user_id, DATE_ADD(NOW(), INTERVAL 3 DAY), '线上', '腾讯会议', 'confirmed', '已确认参加', '基础能力较好，建议二面重点考察项目深度', 'admin', NOW(), 'admin', NOW(), 'employment'
FROM emp_job_application a
JOIN sys_user su ON su.user_id = a.student_user_id
WHERE su.user_name = 'student_demo'
LIMIT 1;

INSERT INTO emp_employment_status(student_user_id, teacher_user_id, current_status, difficulty_level, warning_level, employment_company, position_name, salary_amount, progress_stage, evidence_material_id, risk_score, reason_note, last_follow_time, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, 'difficult', 'attention', 'medium', '上海智聘科技有限公司', 'Java开发工程师', 15000.00, 'interview', (
  SELECT material_id FROM emp_material WHERE user_id = su.user_id ORDER BY material_id ASC LIMIT 1
), 68.00, 'GPA偏低且投递次数有限，需要持续跟进。', NOW(), 'admin', NOW(), 'admin', NOW(), 'employment'
FROM sys_user su
JOIN sys_user tu ON tu.user_name = 'teacher_demo'
WHERE su.user_name = 'student_demo';

INSERT INTO emp_assistance_record(student_user_id, helper_user_id, helper_role, activity_title, activity_type, activity_content, result_summary, next_plan, record_time, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, 'teacher', '一对一简历辅导', 'resume', '针对项目经历进行了量化包装，并补充了技能关键词。', '学生已完成第一版优化简历。', '一周内完成3次定向投递。', NOW(), 'admin', NOW(), 'admin', NOW(), 'employment'
FROM sys_user su
JOIN sys_user tu ON tu.user_name = 'teacher_demo'
WHERE su.user_name = 'student_demo';
