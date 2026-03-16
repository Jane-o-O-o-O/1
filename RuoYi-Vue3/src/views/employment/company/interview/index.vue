<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8"><el-col :span="24"><el-button type="primary" @click="handleAdd">发起面试</el-button></el-col></el-row>
    <el-table :data="list" border>
      <el-table-column label="学生" prop="studentName" />
      <el-table-column label="岗位" prop="jobName" />
      <el-table-column label="面试时间" prop="interviewTime" min-width="180" />
      <el-table-column label="形式" prop="interviewType" />
      <el-table-column label="地点/链接" prop="interviewLocation" min-width="160" />
      <el-table-column label="状态" prop="interviewStatus" />
      <el-table-column label="评价" prop="evaluation" min-width="180" />
    </el-table>

    <el-dialog v-model="open" title="发起面试" width="620px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="应聘记录">
          <el-select v-model="form.applicationId" filterable placeholder="请选择应聘记录" style="width: 100%" @change="handleApplicationChange">
            <el-option
              v-for="item in applicationOptions"
              :key="item.applicationId"
              :label="`${item.studentName || '-'} / ${item.jobName || '-'} / ${item.companyName || '-'}`"
              :value="item.applicationId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="学生"><el-input :model-value="form.studentName || '-'" readonly /></el-form-item>
        <el-form-item label="岗位"><el-input :model-value="form.jobName || '-'" readonly /></el-form-item>
        <el-form-item label="面试时间">
          <el-date-picker v-model="form.interviewTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
        </el-form-item>
        <el-form-item label="面试形式"><el-input v-model="form.interviewType" /></el-form-item>
        <el-form-item label="地点/链接"><el-input v-model="form.interviewLocation" /></el-form-item>
        <el-form-item label="面试评价"><el-input v-model="form.evaluation" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="open = false">取消</el-button><el-button type="primary" @click="submit">保存</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup name="EmploymentCompanyInterview">
import { addInterview, listApplications, listInterviews } from '@/api/employment'
import { parseTime } from '@/utils/ruoyi'

const { proxy } = getCurrentInstance()
const list = ref([])
const applicationOptions = ref([])
const open = ref(false)
const form = ref({})

function loadData() {
  Promise.all([listInterviews(), listApplications({})]).then(([interviewRes, applicationRes]) => {
    list.value = interviewRes.data || []
    applicationOptions.value = applicationRes.data || []
  })
}

function handleAdd() {
  form.value = {
    interviewStatus: 'pending',
    interviewTime: parseTime(new Date()),
  }
  open.value = true
}

function handleApplicationChange(applicationId) {
  const current = applicationOptions.value.find(item => item.applicationId === applicationId)
  if (!current) {
    return
  }
  form.value = {
    ...form.value,
    applicationId: current.applicationId,
    jobId: current.jobId,
    studentUserId: current.studentUserId,
    studentName: current.studentName,
    jobName: current.jobName,
  }
}

function submit() {
  const payload = {
    applicationId: form.value.applicationId,
    jobId: form.value.jobId,
    studentUserId: form.value.studentUserId,
    interviewTime: form.value.interviewTime,
    interviewType: form.value.interviewType,
    interviewLocation: form.value.interviewLocation,
    interviewStatus: form.value.interviewStatus,
    evaluation: form.value.evaluation,
  }
  addInterview(payload).then(() => {
    proxy.$modal.msgSuccess('保存成功')
    open.value = false
    loadData()
  })
}

loadData()
</script>
