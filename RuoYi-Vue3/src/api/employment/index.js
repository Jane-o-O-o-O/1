import request, { download } from '@/utils/request'

export function getStudentProfile() {
  return request({ url: '/employment/student/profile', method: 'get' })
}

export function updateStudentProfile(data) {
  return request({ url: '/employment/student/profile', method: 'put', data })
}

export function listResumes() {
  return request({ url: '/employment/resume/list', method: 'get' })
}

export function getResume(resumeId) {
  return request({ url: `/employment/resume/${resumeId}`, method: 'get' })
}

export function optimizeResume(data) {
  return request({ url: '/employment/resume/optimize', method: 'post', data })
}

export function addResume(data) {
  return request({ url: '/employment/resume', method: 'post', data })
}

export function updateResume(data) {
  return request({ url: '/employment/resume', method: 'put', data })
}

export function deleteResume(resumeId) {
  return request({ url: `/employment/resume/${resumeId}`, method: 'delete' })
}

export function exportResume(resumeId) {
  return download(`/employment/resume/export/${resumeId}`, {}, `resume-${resumeId}.txt`)
}

export function listMaterials(params) {
  return request({ url: '/employment/material/list', method: 'get', params })
}

export function addMaterial(data) {
  return request({ url: '/employment/material', method: 'post', data })
}

export function updateMaterial(data) {
  return request({ url: '/employment/material', method: 'put', data })
}

export function deleteMaterial(materialId) {
  return request({ url: `/employment/material/${materialId}`, method: 'delete' })
}

export function reviewMaterial(data) {
  return request({ url: '/employment/material/review', method: 'put', data })
}

export function getCompanyProfile() {
  return request({ url: '/employment/company/profile', method: 'get' })
}

export function updateCompanyProfile(data) {
  return request({ url: '/employment/company/profile', method: 'put', data })
}

export function listJobs(params) {
  return request({ url: '/employment/job/list', method: 'get', params })
}

export function getJob(jobId) {
  return request({ url: `/employment/job/${jobId}`, method: 'get' })
}

export function addJob(data) {
  return request({ url: '/employment/job', method: 'post', data })
}

export function updateJob(data) {
  return request({ url: '/employment/job', method: 'put', data })
}

export function auditJob(jobId, auditStatus) {
  return request({ url: `/employment/job/${jobId}/audit`, method: 'put', data: { auditStatus } })
}

export function applyJob(jobId) {
  return request({ url: `/employment/application/apply/${jobId}`, method: 'post' })
}

export function listApplications(params) {
  return request({ url: '/employment/application/list', method: 'get', params })
}

export function updateApplicationStatus(data) {
  return request({ url: '/employment/application/status', method: 'put', data })
}

export function listInterviews(params) {
  return request({ url: '/employment/interview/list', method: 'get', params })
}

export function addInterview(data) {
  return request({ url: '/employment/interview', method: 'post', data })
}

export function updateInterview(data) {
  return request({ url: '/employment/interview', method: 'put', data })
}

export function getMyStatus() {
  return request({ url: '/employment/status/me', method: 'get' })
}

export function listStatuses(params) {
  return request({ url: '/employment/status/list', method: 'get', params })
}

export function updateStatus(data) {
  return request({ url: '/employment/status', method: 'put', data })
}

export function exportStatuses(params) {
  return download('/employment/status/export', params, 'employment-status.xlsx')
}

export function listAssists(params) {
  return request({ url: '/employment/assist/list', method: 'get', params })
}

export function addAssist(data) {
  return request({ url: '/employment/assist', method: 'post', data })
}

export function listWarnings(params) {
  return request({ url: '/employment/warning/list', method: 'get', params })
}

export function exportWarnings(params) {
  return download('/employment/warning/export', params, 'employment-warning.xlsx')
}

export function getTeacherDashboard() {
  return request({ url: '/employment/dashboard/teacher', method: 'get' })
}

export function getAdminDashboard() {
  return request({ url: '/employment/dashboard/admin', method: 'get' })
}

export function getCompanyDashboard() {
  return request({ url: '/employment/dashboard/company', method: 'get' })
}
