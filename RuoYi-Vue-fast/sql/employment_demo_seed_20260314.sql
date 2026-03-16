SET NAMES utf8mb4;

USE ry_employment;

SET @pwd = '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2';

INSERT INTO sys_user(dept_id, user_name, nick_name, user_type, email, phonenumber, sex, password, status, del_flag, login_ip, login_date, create_by, create_time, remark)
SELECT 103, 'teacher_cs_01', 'Teacher Chen', '00', 'teacher_cs_01@ruoyi.vip', '13800010001', '0', @pwd, '0', '0', '127.0.0.1', NOW(), 'seed20260314', NOW(), 'employment demo'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE user_name = 'teacher_cs_01');

INSERT INTO sys_user(dept_id, user_name, nick_name, user_type, email, phonenumber, sex, password, status, del_flag, login_ip, login_date, create_by, create_time, remark)
SELECT 103, 'teacher_cs_02', 'Teacher Lin', '00', 'teacher_cs_02@ruoyi.vip', '13800010002', '0', @pwd, '0', '0', '127.0.0.1', NOW(), 'seed20260314', NOW(), 'employment demo'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE user_name = 'teacher_cs_02');

INSERT INTO sys_user(dept_id, user_name, nick_name, user_type, email, phonenumber, sex, password, status, del_flag, login_ip, login_date, create_by, create_time, remark)
SELECT 104, 'company_alpha', 'Alpha HR', '00', 'company_alpha@ruoyi.vip', '13800020001', '0', @pwd, '0', '0', '127.0.0.1', NOW(), 'seed20260314', NOW(), 'employment demo'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE user_name = 'company_alpha');

INSERT INTO sys_user(dept_id, user_name, nick_name, user_type, email, phonenumber, sex, password, status, del_flag, login_ip, login_date, create_by, create_time, remark)
SELECT 104, 'company_beta', 'Beta HR', '00', 'company_beta@ruoyi.vip', '13800020002', '0', @pwd, '0', '0', '127.0.0.1', NOW(), 'seed20260314', NOW(), 'employment demo'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE user_name = 'company_beta');

INSERT INTO sys_user(dept_id, user_name, nick_name, user_type, email, phonenumber, sex, password, status, del_flag, login_ip, login_date, create_by, create_time, remark)
SELECT 103, 'stu_java_01', 'Liu Kai', '00', 'stu_java_01@ruoyi.vip', '13810000001', '0', @pwd, '0', '0', '127.0.0.1', NOW(), 'seed20260314', NOW(), 'employment demo'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE user_name = 'stu_java_01');

INSERT INTO sys_user(dept_id, user_name, nick_name, user_type, email, phonenumber, sex, password, status, del_flag, login_ip, login_date, create_by, create_time, remark)
SELECT 103, 'stu_java_02', 'Wang Bo', '00', 'stu_java_02@ruoyi.vip', '13810000002', '0', @pwd, '0', '0', '127.0.0.1', NOW(), 'seed20260314', NOW(), 'employment demo'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE user_name = 'stu_java_02');

INSERT INTO sys_user(dept_id, user_name, nick_name, user_type, email, phonenumber, sex, password, status, del_flag, login_ip, login_date, create_by, create_time, remark)
SELECT 103, 'stu_front_01', 'Zhao Yue', '00', 'stu_front_01@ruoyi.vip', '13810000003', '1', @pwd, '0', '0', '127.0.0.1', NOW(), 'seed20260314', NOW(), 'employment demo'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE user_name = 'stu_front_01');

INSERT INTO sys_user(dept_id, user_name, nick_name, user_type, email, phonenumber, sex, password, status, del_flag, login_ip, login_date, create_by, create_time, remark)
SELECT 103, 'stu_data_01', 'Sun Qi', '00', 'stu_data_01@ruoyi.vip', '13810000004', '0', @pwd, '0', '0', '127.0.0.1', NOW(), 'seed20260314', NOW(), 'employment demo'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE user_name = 'stu_data_01');

INSERT INTO sys_user(dept_id, user_name, nick_name, user_type, email, phonenumber, sex, password, status, del_flag, login_ip, login_date, create_by, create_time, remark)
SELECT 103, 'stu_test_01', 'He Min', '00', 'stu_test_01@ruoyi.vip', '13810000005', '1', @pwd, '0', '0', '127.0.0.1', NOW(), 'seed20260314', NOW(), 'employment demo'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE user_name = 'stu_test_01');

INSERT INTO sys_user(dept_id, user_name, nick_name, user_type, email, phonenumber, sex, password, status, del_flag, login_ip, login_date, create_by, create_time, remark)
SELECT 103, 'stu_ops_01', 'Guo Peng', '00', 'stu_ops_01@ruoyi.vip', '13810000006', '0', @pwd, '0', '0', '127.0.0.1', NOW(), 'seed20260314', NOW(), 'employment demo'
WHERE NOT EXISTS (SELECT 1 FROM sys_user WHERE user_name = 'stu_ops_01');

DELETE ur
FROM sys_user_role ur
JOIN sys_user u ON u.user_id = ur.user_id
WHERE u.user_name IN (
  'teacher_cs_01', 'teacher_cs_02', 'company_alpha', 'company_beta',
  'stu_java_01', 'stu_java_02', 'stu_front_01', 'stu_data_01', 'stu_test_01', 'stu_ops_01'
);

INSERT INTO sys_user_role(user_id, role_id)
SELECT u.user_id, r.role_id FROM sys_user u JOIN sys_role r ON r.role_key = 'teacher'
WHERE u.user_name IN ('teacher_cs_01', 'teacher_cs_02');

INSERT INTO sys_user_role(user_id, role_id)
SELECT u.user_id, r.role_id FROM sys_user u JOIN sys_role r ON r.role_key = 'company'
WHERE u.user_name IN ('company_alpha', 'company_beta');

INSERT INTO sys_user_role(user_id, role_id)
SELECT u.user_id, r.role_id FROM sys_user u JOIN sys_role r ON r.role_key = 'student'
WHERE u.user_name IN ('stu_java_01', 'stu_java_02', 'stu_front_01', 'stu_data_01', 'stu_test_01', 'stu_ops_01');

DELETE FROM emp_assistance_record
WHERE student_user_id IN (
  SELECT user_id FROM sys_user WHERE user_name IN ('stu_java_01', 'stu_java_02', 'stu_front_01', 'stu_data_01', 'stu_test_01', 'stu_ops_01')
);

DELETE FROM emp_interview
WHERE student_user_id IN (
  SELECT user_id FROM sys_user WHERE user_name IN ('stu_java_01', 'stu_java_02', 'stu_front_01', 'stu_data_01', 'stu_test_01', 'stu_ops_01')
)
OR enterprise_user_id IN (
  SELECT user_id FROM sys_user WHERE user_name IN ('company_alpha', 'company_beta')
);

DELETE FROM emp_job_application
WHERE student_user_id IN (
  SELECT user_id FROM sys_user WHERE user_name IN ('stu_java_01', 'stu_java_02', 'stu_front_01', 'stu_data_01', 'stu_test_01', 'stu_ops_01')
)
OR enterprise_user_id IN (
  SELECT user_id FROM sys_user WHERE user_name IN ('company_alpha', 'company_beta')
);

DELETE FROM emp_employment_status
WHERE student_user_id IN (
  SELECT user_id FROM sys_user WHERE user_name IN ('stu_java_01', 'stu_java_02', 'stu_front_01', 'stu_data_01', 'stu_test_01', 'stu_ops_01')
);

DELETE FROM emp_material
WHERE user_id IN (
  SELECT user_id FROM sys_user WHERE user_name IN ('stu_java_01', 'stu_java_02', 'stu_front_01', 'stu_data_01', 'stu_test_01', 'stu_ops_01')
);

DELETE FROM emp_resume
WHERE user_id IN (
  SELECT user_id FROM sys_user WHERE user_name IN ('stu_java_01', 'stu_java_02', 'stu_front_01', 'stu_data_01', 'stu_test_01', 'stu_ops_01')
);

DELETE FROM emp_student_profile
WHERE user_id IN (
  SELECT user_id FROM sys_user WHERE user_name IN ('stu_java_01', 'stu_java_02', 'stu_front_01', 'stu_data_01', 'stu_test_01', 'stu_ops_01')
);

DELETE FROM emp_job
WHERE enterprise_user_id IN (
  SELECT user_id FROM sys_user WHERE user_name IN ('company_alpha', 'company_beta')
);

DELETE FROM emp_enterprise_profile
WHERE user_id IN (
  SELECT user_id FROM sys_user WHERE user_name IN ('company_alpha', 'company_beta')
);

INSERT INTO emp_student_profile(user_id, teacher_user_id, student_no, major_name, class_name, graduation_year, gpa, expected_city, expected_salary, job_intention, skill_tags, contact_address, employment_status, difficulty_level, warning_level, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, '20261001', 'Software Engineering', 'SE2201', 2026, 3.42, 'Shanghai', 14000.00, 'Java Backend Developer', 'Java,Spring Boot,MySQL,Redis,Docker', 'Pudong Shanghai', 'unemployed', 'normal', 'low', 'seed20260314', NOW(), 'seed20260314', NOW(), 'backend student'
FROM sys_user su JOIN sys_user tu ON tu.user_name = 'teacher_cs_01' WHERE su.user_name = 'stu_java_01';

INSERT INTO emp_student_profile(user_id, teacher_user_id, student_no, major_name, class_name, graduation_year, gpa, expected_city, expected_salary, job_intention, skill_tags, contact_address, employment_status, difficulty_level, warning_level, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, '20261002', 'Software Engineering', 'SE2201', 2026, 2.91, 'Suzhou', 12000.00, 'Java Developer', 'Java,Spring MVC,MySQL,Linux', 'Suzhou Jiangsu', 'difficult', 'attention', 'medium', 'seed20260314', NOW(), 'seed20260314', NOW(), 'needs more applications'
FROM sys_user su JOIN sys_user tu ON tu.user_name = 'teacher_cs_01' WHERE su.user_name = 'stu_java_02';

INSERT INTO emp_student_profile(user_id, teacher_user_id, student_no, major_name, class_name, graduation_year, gpa, expected_city, expected_salary, job_intention, skill_tags, contact_address, employment_status, difficulty_level, warning_level, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, '20261003', 'Digital Media', 'FE2201', 2026, 3.56, 'Hangzhou', 13000.00, 'Frontend Developer', 'Vue3,Element Plus,ECharts,TypeScript', 'Hangzhou Zhejiang', 'unemployed', 'normal', 'low', 'seed20260314', NOW(), 'seed20260314', NOW(), 'frontend student'
FROM sys_user su JOIN sys_user tu ON tu.user_name = 'teacher_cs_01' WHERE su.user_name = 'stu_front_01';

INSERT INTO emp_student_profile(user_id, teacher_user_id, student_no, major_name, class_name, graduation_year, gpa, expected_city, expected_salary, job_intention, skill_tags, contact_address, employment_status, difficulty_level, warning_level, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, '20261004', 'Data Science', 'DS2201', 2026, 3.18, 'Shanghai', 13500.00, 'Data Analyst', 'Python,SQL,PowerBI,Excel', 'Minhang Shanghai', 'employed', 'normal', 'low', 'seed20260314', NOW(), 'seed20260314', NOW(), 'already employed'
FROM sys_user su JOIN sys_user tu ON tu.user_name = 'teacher_cs_02' WHERE su.user_name = 'stu_data_01';

INSERT INTO emp_student_profile(user_id, teacher_user_id, student_no, major_name, class_name, graduation_year, gpa, expected_city, expected_salary, job_intention, skill_tags, contact_address, employment_status, difficulty_level, warning_level, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, '20261005', 'Software Testing', 'QA2201', 2026, 3.05, 'Nanjing', 10500.00, 'QA Engineer', 'TestCase,Postman,JMeter,MySQL', 'Nanjing Jiangsu', 'unemployed', 'attention', 'medium', 'seed20260314', NOW(), 'seed20260314', NOW(), 'waiting for interview'
FROM sys_user su JOIN sys_user tu ON tu.user_name = 'teacher_cs_02' WHERE su.user_name = 'stu_test_01';

INSERT INTO emp_student_profile(user_id, teacher_user_id, student_no, major_name, class_name, graduation_year, gpa, expected_city, expected_salary, job_intention, skill_tags, contact_address, employment_status, difficulty_level, warning_level, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, '20261006', 'Network Engineering', 'OPS2201', 2026, 2.74, 'Shanghai', 11000.00, 'DevOps Engineer', 'Linux,Docker,Jenkins,K8s,Shell', 'Baoshan Shanghai', 'difficult', 'key', 'high', 'seed20260314', NOW(), 'seed20260314', NOW(), 'high risk student'
FROM sys_user su JOIN sys_user tu ON tu.user_name = 'teacher_cs_02' WHERE su.user_name = 'stu_ops_01';

INSERT INTO emp_enterprise_profile(user_id, company_name, industry_name, city, address, company_size, contact_name, contact_phone, company_intro, certification_status, license_file_url, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, 'Alpha Cloud Tech', 'Internet', 'Shanghai', 'Zhangjiang Shanghai', '500-999', 'Amy', '13800020001', 'Cloud platform and enterprise software company.', 'approved', '/profile/upload/alpha-license.pdf', 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u WHERE u.user_name = 'company_alpha';

INSERT INTO emp_enterprise_profile(user_id, company_name, industry_name, city, address, company_size, contact_name, contact_phone, company_intro, certification_status, license_file_url, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, 'Beta Data Labs', 'Software Service', 'Hangzhou', 'Yuhang Hangzhou', '100-499', 'Brian', '13800020002', 'Data service and analytics company.', 'approved', '/profile/upload/beta-license.pdf', 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u WHERE u.user_name = 'company_beta';

INSERT INTO emp_resume(user_id, resume_title, version_no, template_name, summary, education_experience, practice_experience, campus_experience, skill_list, certificate_list, self_evaluation, optimize_suggestion, extracted_keywords, is_default, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, 'Java Backend Resume V1', 1, 'classic', 'Java backend student with internship and project experience.', '2022-2026 University of Technology, Software Engineering', 'Built student project APIs with Spring Boot and MySQL.', 'Class committee member and hackathon participant.', 'Java,Spring Boot,MySQL,Redis,Docker', 'CET4', 'Strong execution and learning ability.', 'Quantify project outcomes and highlight API design.', 'Java,Spring Boot,MySQL,Redis,Docker', 'Y', 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u WHERE u.user_name = 'stu_java_01';

INSERT INTO emp_resume(user_id, resume_title, version_no, template_name, summary, education_experience, practice_experience, campus_experience, skill_list, certificate_list, self_evaluation, optimize_suggestion, extracted_keywords, is_default, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, 'Java Backend Resume V2', 2, 'modern', 'Focused on backend engineering and middleware.', '2022-2026 University of Technology, Software Engineering', 'Participated in internship for user center service.', 'Organized coding club weekly sharing.', 'Java,Spring Cloud,MySQL,Redis,MQ', 'CET4', 'Interested in scalable backend systems.', 'Add more metrics and concurrency scenarios.', 'Java,Spring Cloud,Redis,MQ,MySQL', 'N', 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u WHERE u.user_name = 'stu_java_01';

INSERT INTO emp_resume(user_id, resume_title, version_no, template_name, summary, education_experience, practice_experience, campus_experience, skill_list, certificate_list, self_evaluation, optimize_suggestion, extracted_keywords, is_default, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, 'Java Developer Resume', 1, 'classic', 'Developer focusing on Java and database development.', '2022-2026 University, Software Engineering', 'Completed OA system module development.', 'Volunteer in lab maintenance.', 'Java,MySQL,Linux,Git', 'CET4', 'Stable and detail-oriented.', 'Add more project depth and deployment experience.', 'Java,MySQL,Linux,Git', 'Y', 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u WHERE u.user_name = 'stu_java_02';

INSERT INTO emp_resume(user_id, resume_title, version_no, template_name, summary, education_experience, practice_experience, campus_experience, skill_list, certificate_list, self_evaluation, optimize_suggestion, extracted_keywords, is_default, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, 'Frontend Resume', 1, 'modern', 'Frontend candidate with Vue3 and charting experience.', '2022-2026 University, Digital Media', 'Developed admin panel pages with Vue3 and Element Plus.', 'Design team lead in campus media group.', 'Vue3,Element Plus,ECharts,JavaScript,CSS', 'CET6', 'Good communication and UI sense.', 'Show more interaction design outcomes.', 'Vue3,Element Plus,ECharts,JavaScript,CSS', 'Y', 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u WHERE u.user_name = 'stu_front_01';

INSERT INTO emp_resume(user_id, resume_title, version_no, template_name, summary, education_experience, practice_experience, campus_experience, skill_list, certificate_list, self_evaluation, optimize_suggestion, extracted_keywords, is_default, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, 'Data Analyst Resume', 1, 'classic', 'Data student with SQL and BI background.', '2022-2026 University, Data Science', 'Built dashboard reports with Python and PowerBI.', 'Served as study group organizer.', 'Python,SQL,PowerBI,Excel', 'CET4', 'Strong analytical thinking.', 'Highlight business data cases.', 'Python,SQL,PowerBI,Excel', 'Y', 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u WHERE u.user_name = 'stu_data_01';

INSERT INTO emp_resume(user_id, resume_title, version_no, template_name, summary, education_experience, practice_experience, campus_experience, skill_list, certificate_list, self_evaluation, optimize_suggestion, extracted_keywords, is_default, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, 'QA Engineer Resume', 1, 'classic', 'Testing candidate skilled in API and performance test.', '2022-2026 University, Software Testing', 'Completed API automation and pressure testing.', 'Campus assistant for software lab.', 'Postman,JMeter,MySQL,TestCase', 'CET4', 'Careful and process-oriented.', 'Add defect metrics and collaboration cases.', 'Postman,JMeter,MySQL,TestCase', 'Y', 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u WHERE u.user_name = 'stu_test_01';

INSERT INTO emp_resume(user_id, resume_title, version_no, template_name, summary, education_experience, practice_experience, campus_experience, skill_list, certificate_list, self_evaluation, optimize_suggestion, extracted_keywords, is_default, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, 'DevOps Resume', 1, 'modern', 'DevOps candidate focusing on Linux and CI/CD.', '2022-2026 University, Network Engineering', 'Maintained Dockerized services and Jenkins pipelines.', 'Network center volunteer.', 'Linux,Docker,Jenkins,K8s,Shell', 'HCIA', 'Can adapt quickly to operations work.', 'Increase project detail and cloud deployment cases.', 'Linux,Docker,Jenkins,K8s,Shell', 'Y', 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u WHERE u.user_name = 'stu_ops_01';

INSERT INTO emp_material(user_id, material_type, material_name, file_name, file_url, review_status, review_comment, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, 'agreement', 'tripartite_agreement', CONCAT(u.user_name, '-agreement.pdf'), CONCAT('/profile/upload/', u.user_name, '-agreement.pdf'), 'approved', 'approved by admin', 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u WHERE u.user_name IN ('stu_java_01', 'stu_front_01', 'stu_data_01');

INSERT INTO emp_material(user_id, material_type, material_name, file_name, file_url, review_status, review_comment, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, 'offer', 'offer_letter', CONCAT(u.user_name, '-offer.pdf'), CONCAT('/profile/upload/', u.user_name, '-offer.pdf'), CASE WHEN u.user_name='stu_data_01' THEN 'approved' ELSE 'pending' END, 'waiting for final review', 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u WHERE u.user_name IN ('stu_java_02', 'stu_data_01', 'stu_test_01');

INSERT INTO emp_job(enterprise_user_id, enterprise_id, company_name, job_name, category_name, city, salary_min, salary_max, education_requirement, experience_requirement, job_desc, job_require, skill_keywords, job_status, audit_status, publish_time, deadline, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, ep.enterprise_id, ep.company_name, 'Java Backend Engineer', 'Backend', 'Shanghai', 14000.00, 20000.00, 'Bachelor', '0-2 years', 'Develop backend services and platform APIs.', 'Java Spring Boot MySQL Redis', 'Java,Spring Boot,MySQL,Redis', 'published', 'approved', NOW(), DATE_ADD(NOW(), INTERVAL 25 DAY), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u JOIN emp_enterprise_profile ep ON ep.user_id = u.user_id WHERE u.user_name = 'company_alpha';

INSERT INTO emp_job(enterprise_user_id, enterprise_id, company_name, job_name, category_name, city, salary_min, salary_max, education_requirement, experience_requirement, job_desc, job_require, skill_keywords, job_status, audit_status, publish_time, deadline, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, ep.enterprise_id, ep.company_name, 'DevOps Engineer', 'Operations', 'Shanghai', 13000.00, 18000.00, 'Bachelor', '0-2 years', 'Maintain CI/CD and cloud deployment.', 'Linux Docker Jenkins Kubernetes', 'Linux,Docker,Jenkins,K8s', 'published', 'approved', NOW(), DATE_ADD(NOW(), INTERVAL 20 DAY), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u JOIN emp_enterprise_profile ep ON ep.user_id = u.user_id WHERE u.user_name = 'company_alpha';

INSERT INTO emp_job(enterprise_user_id, enterprise_id, company_name, job_name, category_name, city, salary_min, salary_max, education_requirement, experience_requirement, job_desc, job_require, skill_keywords, job_status, audit_status, publish_time, deadline, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, ep.enterprise_id, ep.company_name, 'Frontend Developer', 'Frontend', 'Hangzhou', 12500.00, 17000.00, 'Bachelor', '0-2 years', 'Build enterprise dashboards and management pages.', 'Vue3 Element Plus TypeScript', 'Vue3,Element Plus,ECharts,TypeScript', 'published', 'approved', NOW(), DATE_ADD(NOW(), INTERVAL 18 DAY), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u JOIN emp_enterprise_profile ep ON ep.user_id = u.user_id WHERE u.user_name = 'company_beta';

INSERT INTO emp_job(enterprise_user_id, enterprise_id, company_name, job_name, category_name, city, salary_min, salary_max, education_requirement, experience_requirement, job_desc, job_require, skill_keywords, job_status, audit_status, publish_time, deadline, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, ep.enterprise_id, ep.company_name, 'Data Analyst', 'Data', 'Hangzhou', 12000.00, 16500.00, 'Bachelor', '0-2 years', 'Support report design and business analysis.', 'Python SQL BI Excel', 'Python,SQL,PowerBI,Excel', 'published', 'approved', NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u JOIN emp_enterprise_profile ep ON ep.user_id = u.user_id WHERE u.user_name = 'company_beta';

INSERT INTO emp_job(enterprise_user_id, enterprise_id, company_name, job_name, category_name, city, salary_min, salary_max, education_requirement, experience_requirement, job_desc, job_require, skill_keywords, job_status, audit_status, publish_time, deadline, create_by, create_time, update_by, update_time, remark)
SELECT u.user_id, ep.enterprise_id, ep.company_name, 'QA Engineer', 'Testing', 'Nanjing', 10500.00, 14500.00, 'Bachelor', '0-2 years', 'API and regression testing for business systems.', 'TestCase Postman JMeter SQL', 'Postman,JMeter,MySQL,TestCase', 'published', 'pending', NOW(), DATE_ADD(NOW(), INTERVAL 22 DAY), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user u JOIN emp_enterprise_profile ep ON ep.user_id = u.user_id WHERE u.user_name = 'company_beta';

INSERT INTO emp_job_application(job_id, student_user_id, resume_id, enterprise_user_id, apply_status, match_score, progress_remark, applied_time, feedback_time, create_by, create_time, update_by, update_time, remark)
SELECT j.job_id, su.user_id, r.resume_id, j.enterprise_user_id, 'interview', 91.00, 'Matched well and moved to interview stage.', NOW(), NOW(), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM emp_job j
JOIN sys_user su ON su.user_name = 'stu_java_01'
JOIN emp_resume r ON r.user_id = su.user_id AND r.is_default = 'Y'
WHERE j.job_name = 'Java Backend Engineer'
LIMIT 1;

INSERT INTO emp_job_application(job_id, student_user_id, resume_id, enterprise_user_id, apply_status, match_score, progress_remark, applied_time, feedback_time, create_by, create_time, update_by, update_time, remark)
SELECT j.job_id, su.user_id, r.resume_id, j.enterprise_user_id, 'applied', 72.00, 'Awaiting enterprise screening.', NOW(), NULL, 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM emp_job j
JOIN sys_user su ON su.user_name = 'stu_java_02'
JOIN emp_resume r ON r.user_id = su.user_id AND r.is_default = 'Y'
WHERE j.job_name = 'Java Backend Engineer'
LIMIT 1;

INSERT INTO emp_job_application(job_id, student_user_id, resume_id, enterprise_user_id, apply_status, match_score, progress_remark, applied_time, feedback_time, create_by, create_time, update_by, update_time, remark)
SELECT j.job_id, su.user_id, r.resume_id, j.enterprise_user_id, 'offer', 88.00, 'Enterprise has issued an offer.', NOW(), NOW(), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM emp_job j
JOIN sys_user su ON su.user_name = 'stu_data_01'
JOIN emp_resume r ON r.user_id = su.user_id AND r.is_default = 'Y'
WHERE j.job_name = 'Data Analyst'
LIMIT 1;

INSERT INTO emp_job_application(job_id, student_user_id, resume_id, enterprise_user_id, apply_status, match_score, progress_remark, applied_time, feedback_time, create_by, create_time, update_by, update_time, remark)
SELECT j.job_id, su.user_id, r.resume_id, j.enterprise_user_id, 'interview', 90.00, 'Interview invitation sent.', NOW(), NOW(), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM emp_job j
JOIN sys_user su ON su.user_name = 'stu_front_01'
JOIN emp_resume r ON r.user_id = su.user_id AND r.is_default = 'Y'
WHERE j.job_name = 'Frontend Developer'
LIMIT 1;

INSERT INTO emp_job_application(job_id, student_user_id, resume_id, enterprise_user_id, apply_status, match_score, progress_remark, applied_time, feedback_time, create_by, create_time, update_by, update_time, remark)
SELECT j.job_id, su.user_id, r.resume_id, j.enterprise_user_id, 'applied', 79.00, 'Resume under review.', NOW(), NULL, 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM emp_job j
JOIN sys_user su ON su.user_name = 'stu_test_01'
JOIN emp_resume r ON r.user_id = su.user_id AND r.is_default = 'Y'
WHERE j.job_name = 'QA Engineer'
LIMIT 1;

INSERT INTO emp_job_application(job_id, student_user_id, resume_id, enterprise_user_id, apply_status, match_score, progress_remark, applied_time, feedback_time, create_by, create_time, update_by, update_time, remark)
SELECT j.job_id, su.user_id, r.resume_id, j.enterprise_user_id, 'applied', 83.00, 'Resume delivered successfully.', NOW(), NULL, 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM emp_job j
JOIN sys_user su ON su.user_name = 'stu_ops_01'
JOIN emp_resume r ON r.user_id = su.user_id AND r.is_default = 'Y'
WHERE j.job_name = 'DevOps Engineer'
LIMIT 1;

INSERT INTO emp_job_application(job_id, student_user_id, resume_id, enterprise_user_id, apply_status, match_score, progress_remark, applied_time, feedback_time, create_by, create_time, update_by, update_time, remark)
SELECT j.job_id, su.user_id, r.resume_id, j.enterprise_user_id, 'applied', 77.00, 'Second choice application submitted.', NOW(), NULL, 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM emp_job j
JOIN sys_user su ON su.user_name = 'stu_java_01'
JOIN emp_resume r ON r.user_id = su.user_id AND r.is_default = 'Y'
WHERE j.job_name = 'DevOps Engineer'
LIMIT 1;

INSERT INTO emp_interview(application_id, job_id, student_user_id, enterprise_user_id, interview_time, interview_type, interview_location, interview_status, student_feedback, evaluation, create_by, create_time, update_by, update_time, remark)
SELECT a.application_id, a.job_id, a.student_user_id, a.enterprise_user_id, DATE_ADD(NOW(), INTERVAL 2 DAY), 'online', 'Tencent Meeting', 'confirmed', 'Student confirmed participation.', 'Good backend foundation and clear communication.', 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM emp_job_application a
JOIN sys_user su ON su.user_id = a.student_user_id
WHERE su.user_name = 'stu_java_01' AND a.apply_status = 'interview'
LIMIT 1;

INSERT INTO emp_interview(application_id, job_id, student_user_id, enterprise_user_id, interview_time, interview_type, interview_location, interview_status, student_feedback, evaluation, create_by, create_time, update_by, update_time, remark)
SELECT a.application_id, a.job_id, a.student_user_id, a.enterprise_user_id, DATE_ADD(NOW(), INTERVAL 3 DAY), 'offline', 'Hangzhou office', 'confirmed', 'Student will attend offline interview.', 'UI implementation ability is strong.', 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM emp_job_application a
JOIN sys_user su ON su.user_id = a.student_user_id
WHERE su.user_name = 'stu_front_01' AND a.apply_status = 'interview'
LIMIT 1;

INSERT INTO emp_employment_status(student_user_id, teacher_user_id, current_status, difficulty_level, warning_level, employment_company, position_name, salary_amount, progress_stage, evidence_material_id, risk_score, reason_note, last_follow_time, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, 'unemployed', 'normal', 'low', NULL, NULL, NULL, 'resume', NULL, 36.00, 'Resume completed and applications started.', NOW(), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user su JOIN sys_user tu ON tu.user_name='teacher_cs_01' WHERE su.user_name='stu_java_01';

INSERT INTO emp_employment_status(student_user_id, teacher_user_id, current_status, difficulty_level, warning_level, employment_company, position_name, salary_amount, progress_stage, evidence_material_id, risk_score, reason_note, last_follow_time, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, 'difficult', 'attention', 'medium', NULL, NULL, NULL, 'application', NULL, 67.00, 'Low GPA and limited application count.', NOW(), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user su JOIN sys_user tu ON tu.user_name='teacher_cs_01' WHERE su.user_name='stu_java_02';

INSERT INTO emp_employment_status(student_user_id, teacher_user_id, current_status, difficulty_level, warning_level, employment_company, position_name, salary_amount, progress_stage, evidence_material_id, risk_score, reason_note, last_follow_time, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, 'unemployed', 'normal', 'low', NULL, NULL, NULL, 'interview', NULL, 42.00, 'Interview arranged, continue follow-up.', NOW(), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user su JOIN sys_user tu ON tu.user_name='teacher_cs_01' WHERE su.user_name='stu_front_01';

INSERT INTO emp_employment_status(student_user_id, teacher_user_id, current_status, difficulty_level, warning_level, employment_company, position_name, salary_amount, progress_stage, evidence_material_id, risk_score, reason_note, last_follow_time, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, 'employed', 'normal', 'low', 'Beta Data Labs', 'Data Analyst', 15000.00, 'offer', (
  SELECT material_id FROM emp_material m WHERE m.user_id = su.user_id ORDER BY material_id DESC LIMIT 1
), 18.00, 'Offer approved and material uploaded.', NOW(), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user su JOIN sys_user tu ON tu.user_name='teacher_cs_02' WHERE su.user_name='stu_data_01';

INSERT INTO emp_employment_status(student_user_id, teacher_user_id, current_status, difficulty_level, warning_level, employment_company, position_name, salary_amount, progress_stage, evidence_material_id, risk_score, reason_note, last_follow_time, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, 'unemployed', 'attention', 'medium', NULL, NULL, NULL, 'application', NULL, 58.00, 'Applied but waiting for further screening.', NOW(), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user su JOIN sys_user tu ON tu.user_name='teacher_cs_02' WHERE su.user_name='stu_test_01';

INSERT INTO emp_employment_status(student_user_id, teacher_user_id, current_status, difficulty_level, warning_level, employment_company, position_name, salary_amount, progress_stage, evidence_material_id, risk_score, reason_note, last_follow_time, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, 'difficult', 'key', 'high', NULL, NULL, NULL, 'resume', NULL, 86.00, 'High risk due to GPA, low interview count and urgent guidance need.', NOW(), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user su JOIN sys_user tu ON tu.user_name='teacher_cs_02' WHERE su.user_name='stu_ops_01';

INSERT INTO emp_assistance_record(student_user_id, helper_user_id, helper_role, activity_title, activity_type, activity_content, result_summary, next_plan, record_time, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, 'teacher', 'Resume coaching', 'resume', 'Improved resume summary and quantified project outcomes.', 'Student finished second resume revision.', 'Apply to 3 more backend jobs this week.', NOW(), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user su JOIN sys_user tu ON tu.user_name='teacher_cs_01' WHERE su.user_name='stu_java_02';

INSERT INTO emp_assistance_record(student_user_id, helper_user_id, helper_role, activity_title, activity_type, activity_content, result_summary, next_plan, record_time, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, 'teacher', 'Interview guidance', 'interview', 'Reviewed self introduction and common frontend interview questions.', 'Student interview confidence improved.', 'Track interview result after 3 days.', NOW(), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user su JOIN sys_user tu ON tu.user_name='teacher_cs_01' WHERE su.user_name='stu_front_01';

INSERT INTO emp_assistance_record(student_user_id, helper_user_id, helper_role, activity_title, activity_type, activity_content, result_summary, next_plan, record_time, create_by, create_time, update_by, update_time, remark)
SELECT su.user_id, tu.user_id, 'teacher', 'Risk intervention', 'counseling', 'One-to-one discussion on job direction and daily application plan.', 'Student agreed to weekly reporting.', 'Monitor application count and resume delivery every week.', NOW(), 'seed20260314', NOW(), 'seed20260314', NOW(), 'employment demo'
FROM sys_user su JOIN sys_user tu ON tu.user_name='teacher_cs_02' WHERE su.user_name='stu_ops_01';
