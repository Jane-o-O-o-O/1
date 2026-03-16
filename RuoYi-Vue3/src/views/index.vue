<template>
  <div class="employment-home">
    <section class="hero">
      <div class="hero__main">
        <div class="hero__badge">RuoYi Framework · Employment</div>
        <h1> 就业管理系统</h1>
        <p>
          基于 框架构建的就业管理平台，覆盖学生求职、教师跟踪、企业招聘、管理员统筹四个端口。
        </p>
        <div class="hero__meta">
          <span>{{ roleLabel }}首页</span>
          <span>当前用户：{{ userStore.nickName || userStore.name || '-' }}</span>
          <span>框架版本：RuoYi Vue3</span>
        </div>
      </div>
      <div class="hero__side">
        <div class="hero__panel">
          <div class="hero__panel-label">角色定位</div>
          <div class="hero__panel-value">{{ roleLabel }}</div>
          <div class="hero__panel-desc">{{ roleDescription }}</div>
        </div>
      </div>
    </section>

    <el-row :gutter="16" class="metrics">
      <el-col v-for="item in metrics" :key="item.label" :xl="6" :lg="6" :md="12" :sm="12" :xs="24">
        <el-card shadow="never" class="metric-card">
          <div class="metric-card__label">{{ item.label }}</div>
          <div class="metric-card__value">{{ item.value }}</div>
          <div class="metric-card__hint">{{ item.hint }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="content-row">
      <el-col :lg="16" :xs="24">
        <el-card shadow="never" class="panel-card">
          <template #header>
            <div class="panel-head">
              <span>快捷入口</span>
              <span class="panel-head__sub">常用业务操作</span>
            </div>
          </template>
          <div class="quick-grid">
            <button
              v-for="item in quickEntries"
              :key="item.title"
              class="quick-card"
              type="button"
              @click="goPage(item.path)"
            >
              <div class="quick-card__title">{{ item.title }}</div>
              <div class="quick-card__desc">{{ item.desc }}</div>
            </button>
          </div>
        </el-card>

        <el-card shadow="never" class="panel-card" style="margin-top: 16px">
          <template #header>
            <div class="panel-head">
              <span>{{ boardTitle }}</span>
              <span class="panel-head__sub">{{ boardSubtitle }}</span>
            </div>
          </template>

          <el-table v-if="boardRows.length" :data="boardRows" border>
            <el-table-column
              v-for="col in boardColumns"
              :key="col.prop"
              :label="col.label"
              :prop="col.prop"
              :min-width="col.minWidth || 120"
            />
          </el-table>
          <el-empty v-else description="暂无数据" :image-size="90" />
        </el-card>
      </el-col>

      <el-col :lg="8" :xs="24">
        <el-card shadow="never" class="panel-card">
          <template #header>
            <div class="panel-head">
              <span>业务概览</span>
              <span class="panel-head__sub">角色相关重点信息</span>
            </div>
          </template>
          <div class="insight-list">
            <div v-for="item in insightList" :key="item.label" class="insight-item">
              <div class="insight-item__top">
                <span>{{ item.label }}</span>
                <strong>{{ item.value }}</strong>
              </div>
              <el-progress
                :percentage="normalizePercent(item.percent)"
                :show-text="false"
                :stroke-width="10"
                :color="item.color || '#2d8f6f'"
              />
              <div class="insight-item__remark">{{ item.remark }}</div>
            </div>
          </div>
        </el-card>

        <el-card shadow="never" class="panel-card" style="margin-top: 16px">
          <template #header>
            <div class="panel-head">
              <span>系统说明</span>
              <span class="panel-head__sub"> 框架适配版</span>
            </div>
          </template>
          <ul class="feature-list">
            <li>统一使用 框架的权限、菜单、字典、上传和日志能力。</li>
            <li>学生、教师、企业、管理员四个端口共用同一套认证体系。</li>
            <li>首页内容会根据当前登录角色自动切换，不再显示 默认宣传页。</li>
            <li>当前版本已接入就业画像、岗位投递、面试流转、预警识别和审核管理。</li>
          </ul>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="Index">
import useUserStore from '@/store/modules/user'
import {
  getAdminDashboard,
  getCompanyDashboard,
  getMyStatus,
  getStudentProfile,
  getTeacherDashboard,
  listApplications,
  listInterviews,
  listJobs,
  listMaterials,
  listResumes,
  listStatuses,
  listWarnings
} from '@/api/employment'

const router = useRouter()
const userStore = useUserStore()

const roleType = computed(() => {
  const roles = userStore.roles || []
  if (roles.includes('admin')) return 'admin'
  if (roles.includes('teacher')) return 'teacher'
  if (roles.includes('company')) return 'company'
  return 'student'
})

const roleMap = {
  student: {
    label: '学生端',
    description: '聚焦个人画像、简历优化、就业材料和岗位投递。',
    quickEntries: [
      { title: '个人中心', desc: '完善就业画像与求职意向', path: '/employment/student/profile' },
      { title: '简历管理', desc: '维护多版本简历并导出', path: '/employment/student/resume' },
      { title: '材料管理', desc: '上传三方协议与录用材料', path: '/employment/student/material' },
      { title: '岗位大厅', desc: '查看企业岗位并在线投递', path: '/employment/student/job' }
    ]
  },
  teacher: {
    label: '教师端',
    description: '聚焦学生就业跟踪、困难识别、帮扶记录和班级数据看板。',
    quickEntries: [
      { title: '就业状态', desc: '查看并更新学生就业状态', path: '/employment/teacher/status' },
      { title: '帮扶记录', desc: '记录一对一帮扶过程', path: '/employment/teacher/assist' },
      { title: '数据看板', desc: '查看班级就业走势与分布', path: '/employment/teacher/dashboard' }
    ]
  },
  company: {
    label: '企业端',
    description: '聚焦岗位发布、简历筛选、面试邀约与招聘进度管理。',
    quickEntries: [
      { title: '企业资料', desc: '维护企业信息与资质', path: '/employment/company/profile' },
      { title: '岗位管理', desc: '发布和维护招聘岗位', path: '/employment/company/job' },
      { title: '简历筛选', desc: '查看投递简历和匹配度', path: '/employment/company/resume' },
      { title: '面试管理', desc: '安排面试并记录结果', path: '/employment/company/interview' }
    ]
  },
  admin: {
    label: '管理员端',
    description: '聚焦全局统筹、内容审核、预警识别与就业统计分析。',
    quickEntries: [
      { title: '状态总览', desc: '查看全校就业状态数据', path: '/employment/admin/status' },
      { title: '预警识别', desc: '识别困难学生与高风险对象', path: '/employment/admin/warning' },
      { title: '统计报表', desc: '查看可视化统计报表', path: '/employment/admin/dashboard' },
      { title: '内容审核', desc: '审核岗位与就业材料', path: '/employment/admin/audit' }
    ]
  }
}

const metrics = ref([])
const boardTitle = ref('业务列表')
const boardSubtitle = ref('最新动态')
const boardColumns = ref([])
const boardRows = ref([])
const insightList = ref([])

const roleLabel = computed(() => roleMap[roleType.value].label)
const roleDescription = computed(() => roleMap[roleType.value].description)
const quickEntries = computed(() => roleMap[roleType.value].quickEntries)

function normalizePercent(value) {
  const num = Number(value || 0)
  if (Number.isNaN(num)) return 0
  return Math.max(0, Math.min(100, Math.round(num)))
}

function statusLabel(value) {
  const map = {
    unemployed: '未就业',
    employed: '已就业',
    difficult: '困难就业',
    pending: '待处理',
    approved: '已通过',
    rejected: '已驳回',
    applied: '已投递',
    interview: '待面试',
    offer: '已录用',
    confirmed: '已确认',
    finished: '已完成',
    cancelled: '已取消',
    low: '低',
    medium: '中',
    high: '高',
    normal: '正常',
    attention: '关注',
    key: '重点'
  }
  return map[value] || value || '-'
}

function toRows(data) {
  if (!data) return []
  if (Array.isArray(data.rows)) return data.rows
  if (Array.isArray(data.data)) return data.data
  if (Array.isArray(data)) return data
  return []
}

function goPage(path) {
  router.push(path)
}

async function loadStudentHome() {
  const [profileRes, statusRes, resumeRes, materialRes, applicationRes, jobRes] = await Promise.all([
    getStudentProfile(),
    getMyStatus(),
    listResumes(),
    listMaterials(),
    listApplications(),
    listJobs({}),
  ])
  const profile = profileRes.data || {}
  const status = statusRes.data || {}
  const resumes = resumeRes.data || []
  const materials = materialRes.data || []
  const applications = applicationRes.data || []
  const jobs = jobRes.data || []
  const interviewCount = applications.filter(item => item.applyStatus === 'interview' || item.applyStatus === 'offer').length

  metrics.value = [
    { label: '简历版本', value: resumes.length, hint: '已维护的简历数量' },
    { label: '材料数量', value: materials.length, hint: '已上传就业材料' },
    { label: '投递次数', value: applications.length, hint: '岗位投递记录' },
    { label: '当前状态', value: statusLabel(status.currentStatus || profile.employmentStatus), hint: '实时就业状态' }
  ]

  boardTitle.value = '我的投递进度'
  boardSubtitle.value = '展示最近投递与处理状态'
  boardColumns.value = [
    { label: '企业', prop: 'companyName' },
    { label: '岗位', prop: 'jobName' },
    { label: '匹配度', prop: 'matchScore' },
    { label: '状态', prop: 'applyStatusText' },
    { label: '进度说明', prop: 'progressRemark', minWidth: 220 }
  ]
  boardRows.value = applications.slice(0, 6).map(item => ({
    ...item,
    applyStatusText: statusLabel(item.applyStatus)
  }))

  insightList.value = [
    {
      label: '就业风险',
      value: `${status.warningLevel ? statusLabel(status.warningLevel) : '低'}预警`,
      percent: Number(status.riskScore || 0),
      color: '#d96941',
      remark: `风险分 ${status.riskScore || 0}`
    },
    {
      label: '困难等级',
      value: statusLabel(status.difficultyLevel || profile.difficultyLevel),
      percent: status.difficultyLevel === 'key' ? 90 : status.difficultyLevel === 'attention' ? 65 : 35,
      color: '#c89b2b',
      remark: '用于教师端重点帮扶识别'
    },
    {
      label: '面试进度',
      value: interviewCount,
      percent: applications.length ? (interviewCount / applications.length) * 100 : 0,
      color: '#2d8f6f',
      remark: `推荐岗位 ${jobs.length} 个`
    }
  ]
}

async function loadTeacherHome() {
  const [dashboardRes, warningRes, statusRes] = await Promise.all([
    getTeacherDashboard(),
    listWarnings(),
    listStatuses({})
  ])
  const dashboard = dashboardRes.data || {}
  const warnings = toRows(warningRes)
  const statuses = toRows(statusRes)
  const employRate = dashboard.totalStudents ? ((dashboard.employedStudents || 0) / dashboard.totalStudents) * 100 : 0

  metrics.value = [
    { label: '学生总数', value: dashboard.totalStudents || 0, hint: '当前负责学生规模' },
    { label: '已就业', value: dashboard.employedStudents || 0, hint: '已确认就业人数' },
    { label: '未就业', value: dashboard.unemployedStudents || 0, hint: '待持续跟进学生' },
    { label: '困难学生', value: dashboard.difficultStudents || 0, hint: '重点帮扶对象' }
  ]

  boardTitle.value = '重点预警学生'
  boardSubtitle.value = '按风险分倒序展示'
  boardColumns.value = [
    { label: '学生', prop: 'studentName' },
    { label: '学号', prop: 'studentNo' },
    { label: '专业', prop: 'majorName' },
    { label: '预警等级', prop: 'warningLevelText' },
    { label: '风险分', prop: 'riskScore' },
    { label: '原因说明', prop: 'reasonNote', minWidth: 220 }
  ]
  boardRows.value = warnings.slice(0, 8).map(item => ({
    ...item,
    warningLevelText: statusLabel(item.warningLevel)
  }))

  insightList.value = [
    {
      label: '就业率',
      value: `${employRate.toFixed(1)}%`,
      percent: employRate,
      color: '#2d8f6f',
      remark: '按当前教师名下学生计算'
    },
    {
      label: '待帮扶占比',
      value: `${dashboard.difficultStudents || 0}人`,
      percent: dashboard.totalStudents ? ((dashboard.difficultStudents || 0) / dashboard.totalStudents) * 100 : 0,
      color: '#c89b2b',
      remark: '建议优先查看帮扶记录'
    },
    {
      label: '状态记录完整度',
      value: `${statuses.length}条`,
      percent: dashboard.totalStudents ? (statuses.length / dashboard.totalStudents) * 100 : 0,
      color: '#4c7ff7',
      remark: '已登记就业状态的学生数量'
    }
  ]
}

async function loadCompanyHome() {
  const [dashboardRes, jobRes, applicationRes, interviewRes] = await Promise.all([
    getCompanyDashboard(),
    listJobs({}),
    listApplications(),
    listInterviews({})
  ])
  const dashboard = dashboardRes.data || {}
  const jobs = jobRes.data || []
  const applications = applicationRes.data || []
  const interviews = interviewRes.data || []
  const publishedJobs = jobs.filter(item => item.jobStatus === 'published').length

  metrics.value = [
    { label: '岗位总数', value: dashboard.totalJobs || jobs.length, hint: '当前企业维护岗位' },
    { label: '发布中岗位', value: publishedJobs, hint: '处于招聘中的岗位' },
    { label: '简历投递', value: applications.length, hint: '学生投递记录总数' },
    { label: '面试安排', value: dashboard.totalInterviews || interviews.length, hint: '已创建面试流程' }
  ]

  boardTitle.value = '最新简历投递'
  boardSubtitle.value = '展示企业收到的投递记录'
  boardColumns.value = [
    { label: '学生', prop: 'studentName' },
    { label: '专业', prop: 'majorName' },
    { label: '岗位', prop: 'jobName' },
    { label: '匹配度', prop: 'matchScore' },
    { label: '状态', prop: 'applyStatusText' }
  ]
  boardRows.value = applications.slice(0, 8).map(item => ({
    ...item,
    applyStatusText: statusLabel(item.applyStatus)
  }))

  insightList.value = [
    {
      label: '岗位通过率',
      value: `${jobs.filter(item => item.auditStatus === 'approved').length}/${jobs.length || 0}`,
      percent: jobs.length ? (jobs.filter(item => item.auditStatus === 'approved').length / jobs.length) * 100 : 0,
      color: '#2d8f6f',
      remark: '管理员审核通过的岗位占比'
    },
    {
      label: '面试转化',
      value: `${interviews.length}场`,
      percent: applications.length ? (interviews.length / applications.length) * 100 : 0,
      color: '#4c7ff7',
      remark: '投递进入面试流程的比例'
    },
    {
      label: '待处理简历',
      value: applications.filter(item => item.applyStatus === 'applied').length,
      percent: applications.length ? (applications.filter(item => item.applyStatus === 'applied').length / applications.length) * 100 : 0,
      color: '#d96941',
      remark: '建议优先处理新投递'
    }
  ]
}

async function loadAdminHome() {
  const [dashboardRes, warningRes, statusRes] = await Promise.all([
    getAdminDashboard(),
    listWarnings(),
    listStatuses({})
  ])
  const dashboard = dashboardRes.data || {}
  const warnings = toRows(warningRes)
  const statuses = toRows(statusRes)
  const employRate = dashboard.totalStudents ? ((dashboard.employedStudents || 0) / dashboard.totalStudents) * 100 : 0

  metrics.value = [
    { label: '学生总数', value: dashboard.totalStudents || 0, hint: '纳入系统的学生数据' },
    { label: '已就业', value: dashboard.employedStudents || 0, hint: '确认就业人数' },
    { label: '待审材料', value: dashboard.pendingMaterials || 0, hint: '等待审核的材料' },
    { label: '待审岗位', value: dashboard.pendingJobs || 0, hint: '等待审核的岗位' }
  ]

  boardTitle.value = '全局预警名单'
  boardSubtitle.value = '优先关注高风险就业对象'
  boardColumns.value = [
    { label: '学生', prop: 'studentName' },
    { label: '班级', prop: 'className' },
    { label: '专业', prop: 'majorName' },
    { label: '预警等级', prop: 'warningLevelText' },
    { label: '风险分', prop: 'riskScore' },
    { label: '原因说明', prop: 'reasonNote', minWidth: 240 }
  ]
  boardRows.value = warnings.slice(0, 8).map(item => ({
    ...item,
    warningLevelText: statusLabel(item.warningLevel)
  }))

  insightList.value = [
    {
      label: '全局就业率',
      value: `${employRate.toFixed(1)}%`,
      percent: employRate,
      color: '#2d8f6f',
      remark: '按系统学生总数计算'
    },
    {
      label: '困难学生占比',
      value: `${dashboard.difficultStudents || 0}人`,
      percent: dashboard.totalStudents ? ((dashboard.difficultStudents || 0) / dashboard.totalStudents) * 100 : 0,
      color: '#c89b2b',
      remark: '需重点关注的帮扶对象'
    },
    {
      label: '状态覆盖率',
      value: `${statuses.length}条`,
      percent: dashboard.totalStudents ? (statuses.length / dashboard.totalStudents) * 100 : 0,
      color: '#4c7ff7',
      remark: '已建立就业状态档案的学生数量'
    }
  ]
}

async function loadHome() {
  try {
    if (roleType.value === 'admin') {
      await loadAdminHome()
    } else if (roleType.value === 'teacher') {
      await loadTeacherHome()
    } else if (roleType.value === 'company') {
      await loadCompanyHome()
    } else {
      await loadStudentHome()
    }
  } catch (error) {
    metrics.value = [
      { label: '系统状态', value: '异常', hint: '首页数据加载失败' },
      { label: '角色', value: roleLabel.value, hint: '请检查接口权限或服务状态' }
    ]
    boardRows.value = []
    insightList.value = [
      { label: '提示', value: '加载失败', percent: 0, remark: '请确认后端接口已启动并拥有对应权限。' }
    ]
  }
}

onMounted(loadHome)
</script>

<style scoped lang="scss">
.employment-home {
  padding: 20px;
  background:
    radial-gradient(circle at top left, rgba(42, 142, 109, 0.18), transparent 30%),
    radial-gradient(circle at top right, rgba(76, 127, 247, 0.16), transparent 32%),
    linear-gradient(180deg, #f5f8f7 0%, #eef3f1 100%);
  min-height: calc(100vh - 84px);
}

.hero {
  display: grid;
  grid-template-columns: 1.8fr 0.9fr;
  gap: 16px;
  margin-bottom: 16px;
}

.hero__main,
.hero__side {
  border-radius: 24px;
  overflow: hidden;
}

.hero__main {
  padding: 28px 32px;
  background: linear-gradient(135deg, #173a34 0%, #245a50 48%, #2f7d67 100%);
  color: #f4fbf8;
  box-shadow: 0 20px 40px rgba(23, 58, 52, 0.18);
}

.hero__badge {
  display: inline-flex;
  padding: 6px 12px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.14);
  font-size: 12px;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.hero__main h1 {
  margin: 18px 0 10px;
  font-size: 34px;
  line-height: 1.15;
  font-weight: 700;
}

.hero__main p {
  margin: 0;
  max-width: 760px;
  font-size: 15px;
  line-height: 1.8;
  color: rgba(244, 251, 248, 0.9);
}

.hero__meta {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-top: 18px;
}

.hero__meta span {
  padding: 8px 12px;
  border-radius: 10px;
  background: rgba(255, 255, 255, 0.12);
  font-size: 13px;
}

.hero__side {
  padding: 18px;
  background: linear-gradient(180deg, #ffffff 0%, #f3f7f6 100%);
  border: 1px solid rgba(30, 55, 45, 0.08);
}

.hero__panel {
  height: 100%;
  border-radius: 18px;
  padding: 24px 20px;
  background:
    linear-gradient(145deg, rgba(45, 143, 111, 0.1), rgba(76, 127, 247, 0.08)),
    #fff;
}

.hero__panel-label {
  color: #5e736c;
  font-size: 13px;
}

.hero__panel-value {
  margin-top: 14px;
  font-size: 30px;
  font-weight: 700;
  color: #173a34;
}

.hero__panel-desc {
  margin-top: 12px;
  font-size: 14px;
  line-height: 1.8;
  color: #536863;
}

.metrics {
  margin-bottom: 16px;
}

.metric-card {
  border: none;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.9);
  box-shadow: 0 10px 24px rgba(20, 33, 28, 0.06);
}

.metric-card__label {
  color: #667d76;
  font-size: 13px;
}

.metric-card__value {
  margin-top: 12px;
  font-size: 30px;
  font-weight: 700;
  color: #163831;
}

.metric-card__hint {
  margin-top: 8px;
  font-size: 13px;
  color: #8a9b96;
}

.panel-card {
  border: none;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.92);
  box-shadow: 0 10px 24px rgba(20, 33, 28, 0.06);
}

.panel-head {
  display: flex;
  align-items: baseline;
  justify-content: space-between;
  gap: 12px;
  font-weight: 600;
  color: #1e4039;
}

.panel-head__sub {
  color: #7e8f8a;
  font-size: 12px;
  font-weight: 400;
}

.quick-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 14px;
}

.quick-card {
  padding: 18px;
  border: 1px solid rgba(45, 143, 111, 0.14);
  border-radius: 16px;
  text-align: left;
  background: linear-gradient(180deg, #ffffff 0%, #f6fbf9 100%);
  cursor: pointer;
  transition: transform 0.18s ease, box-shadow 0.18s ease, border-color 0.18s ease;
}

.quick-card:hover {
  transform: translateY(-2px);
  border-color: rgba(45, 143, 111, 0.32);
  box-shadow: 0 12px 24px rgba(45, 143, 111, 0.12);
}

.quick-card__title {
  font-size: 16px;
  font-weight: 700;
  color: #1b3d36;
}

.quick-card__desc {
  margin-top: 8px;
  color: #708580;
  font-size: 13px;
  line-height: 1.7;
}

.insight-list {
  display: flex;
  flex-direction: column;
  gap: 18px;
}

.insight-item {
  padding: 14px 14px 10px;
  border-radius: 14px;
  background: #f8fbfa;
}

.insight-item__top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
  color: #516964;
}

.insight-item__top strong {
  font-size: 18px;
  color: #183a33;
}

.insight-item__remark {
  margin-top: 8px;
  font-size: 12px;
  color: #8a9b96;
}

.feature-list {
  margin: 0;
  padding-left: 18px;
  color: #556b65;
  line-height: 1.9;
}

.feature-list li + li {
  margin-top: 6px;
}

@media (max-width: 1200px) {
  .hero {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .employment-home {
    padding: 12px;
  }

  .hero__main,
  .hero__side,
  .metric-card,
  .panel-card {
    border-radius: 16px;
  }

  .hero__main {
    padding: 22px 18px;
  }

  .hero__main h1 {
    font-size: 28px;
  }

  .quick-grid {
    grid-template-columns: 1fr;
  }
}
</style>
