<template>
  <div class="app-container employment-page">
    <el-row :gutter="16" class="mb8">
      <el-col :span="24">
        <el-button type="primary" @click="handleAdd">新建版本</el-button>
        <el-button @click="loadData">刷新</el-button>
      </el-col>
    </el-row>
    <el-row :gutter="16">
      <el-col v-for="item in resumeList" :key="item.resumeId" :lg="8" :md="12" :xs="24">
        <el-card shadow="hover" class="resume-card">
          <template #header>
            <div class="resume-head">
              <span>{{ item.resumeTitle }}</span>
              <el-tag size="small" type="success">V{{ item.versionNo }}</el-tag>
            </div>
          </template>
          <p class="muted">模板：{{ item.templateName || 'classic' }}</p>
          <p class="keywords">关键词：{{ item.extractedKeywords || '未生成' }}</p>
          <p class="summary">{{ item.optimizeSuggestion || item.summary || '暂无内容' }}</p>
          <div class="actions">
            <el-button link type="primary" @click="handleEdit(item)">编辑</el-button>
            <el-button link type="success" @click="handleOptimize(item)">AI优化</el-button>
            <el-button link type="warning" @click="handleExport(item)">导出</el-button>
            <el-button link type="danger" @click="handleDelete(item)">删除</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-dialog v-model="open" :title="title" width="900px">
      <el-form ref="formRef" :model="form" label-width="90px">
        <el-row :gutter="16">
          <el-col :md="12" :xs="24"><el-form-item label="标题"><el-input v-model="form.resumeTitle" /></el-form-item></el-col>
          <el-col :md="12" :xs="24"><el-form-item label="模板"><el-select v-model="form.templateName" style="width: 100%"><el-option label="Classic" value="classic" /><el-option label="Modern" value="modern" /><el-option label="Focus" value="focus" /></el-select></el-form-item></el-col>
          <el-col :xs="24"><el-form-item label="摘要"><el-input v-model="form.summary" type="textarea" :rows="3" /></el-form-item></el-col>
          <el-col :xs="24"><el-form-item label="教育经历"><el-input v-model="form.educationExperience" type="textarea" :rows="3" /></el-form-item></el-col>
          <el-col :xs="24"><el-form-item label="实践经历"><el-input v-model="form.practiceExperience" type="textarea" :rows="4" /></el-form-item></el-col>
          <el-col :xs="24"><el-form-item label="校园经历"><el-input v-model="form.campusExperience" type="textarea" :rows="3" /></el-form-item></el-col>
          <el-col :xs="24"><el-form-item label="技能清单"><el-input v-model="form.skillList" type="textarea" :rows="2" /></el-form-item></el-col>
          <el-col :xs="24"><el-form-item label="证书"><el-input v-model="form.certificateList" type="textarea" :rows="2" /></el-form-item></el-col>
          <el-col :xs="24"><el-form-item label="自我评价"><el-input v-model="form.selfEvaluation" type="textarea" :rows="3" /></el-form-item></el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="handleOptimize(form)">AI优化</el-button>
        <el-button @click="open = false">取消</el-button>
        <el-button type="primary" @click="submit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="EmploymentStudentResume">
import { addResume, deleteResume, exportResume, listResumes, optimizeResume, updateResume } from '@/api/employment'

const { proxy } = getCurrentInstance()
const open = ref(false)
const title = ref('')
const resumeList = ref([])
const form = ref({ templateName: 'classic', isDefault: 'Y' })

function loadData() {
  listResumes().then(res => {
    resumeList.value = res.data || []
  })
}

function handleAdd() {
  title.value = '新建简历版本'
  form.value = { templateName: 'classic', isDefault: 'Y' }
  open.value = true
}

function handleEdit(row) {
  title.value = '编辑简历'
  form.value = { ...row }
  open.value = true
}

function handleOptimize(row) {
  optimizeResume(row).then(res => {
    form.value = { ...form.value, ...res.data }
    proxy.$modal.msgSuccess('已生成优化建议')
  })
}

function handleDelete(row) {
  proxy.$modal.confirm(`确认删除简历版本 ${row.versionNo} 吗？`).then(() => deleteResume(row.resumeId)).then(() => {
    proxy.$modal.msgSuccess('删除成功')
    loadData()
  }).catch(() => {})
}

function handleExport(row) {
  exportResume(row.resumeId)
}

function submit() {
  const request = form.value.resumeId ? updateResume : addResume
  request(form.value).then(() => {
    proxy.$modal.msgSuccess('保存成功')
    open.value = false
    loadData()
  })
}

loadData()
</script>

<style scoped lang="scss">
.resume-card {
  margin-bottom: 16px;
  min-height: 260px;
}
.resume-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.summary, .keywords, .muted {
  color: var(--el-text-color-secondary);
  line-height: 1.7;
}
.actions {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}
</style>
