<template>
  <div class="app-container">
    <el-alert
      title="Only approved and published jobs are visible in the student hall. New jobs require admin review."
      type="info"
      :closable="false"
      class="mb8"
    />
    <el-row :gutter="10" class="mb8"><el-col :span="24"><el-button type="primary" @click="handleAdd">New Job</el-button></el-col></el-row>
    <el-table :data="list" border>
      <el-table-column label="岗位" prop="jobName" />
      <el-table-column label="方向" prop="categoryName" />
      <el-table-column label="城市" prop="city" />
      <el-table-column label="薪资" min-width="140"><template #default="scope">{{ scope.row.salaryMin }} - {{ scope.row.salaryMax }}</template></el-table-column>
      <el-table-column label="岗位状态" prop="jobStatus" />
      <el-table-column label="审核状态" prop="auditStatus" />
      <el-table-column label="简历数" prop="applicationCount" />
      <el-table-column label="操作" width="140"><template #default="scope"><el-button link type="primary" @click="handleEdit(scope.row)">编辑</el-button></template></el-table-column>
    </el-table>

    <el-dialog v-model="open" :title="title" width="720px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="岗位名称"><el-input v-model="form.jobName" /></el-form-item>
        <el-form-item label="岗位方向"><el-input v-model="form.categoryName" /></el-form-item>
        <el-form-item label="工作城市"><el-input v-model="form.city" /></el-form-item>
        <el-form-item label="最低薪资"><el-input-number v-model="form.salaryMin" :min="0" style="width: 100%" /></el-form-item>
        <el-form-item label="最高薪资"><el-input-number v-model="form.salaryMax" :min="0" style="width: 100%" /></el-form-item>
        <el-form-item label="学历要求"><el-input v-model="form.educationRequirement" /></el-form-item>
        <el-form-item label="经验要求"><el-input v-model="form.experienceRequirement" /></el-form-item>
        <el-form-item label="技能关键词"><el-input v-model="form.skillKeywords" /></el-form-item>
        <el-form-item label="岗位描述"><el-input v-model="form.jobDesc" type="textarea" :rows="4" /></el-form-item>
        <el-form-item label="任职要求"><el-input v-model="form.jobRequire" type="textarea" :rows="4" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="open = false">取消</el-button><el-button type="primary" @click="submit">保存</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup name="EmploymentCompanyJob">
import { addJob, listJobs, updateJob } from '@/api/employment'
import { parseTime } from '@/utils/ruoyi'

const { proxy } = getCurrentInstance()
const list = ref([])
const open = ref(false)
const title = ref('')
const form = ref({})

function loadData() {
  listJobs().then(res => {
    list.value = res.data || []
  })
}

function handleAdd() {
  title.value = 'Create Job'
  form.value = { jobStatus: 'published' }
  open.value = true
}

function handleEdit(row) {
  title.value = 'Edit Job'
  form.value = { ...row }
  open.value = true
}

function submit() {
  const payload = {
    ...form.value,
    jobStatus: 'published',
    publishTime: parseTime(form.value.publishTime || new Date()),
  }
  const request = form.value.jobId ? updateJob : addJob
  request(payload).then(() => {
    proxy.$modal.msgSuccess('已提交审核')
    open.value = false
    loadData()
  })
}

loadData()
</script>
