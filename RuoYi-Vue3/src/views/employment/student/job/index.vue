<template>
  <div class="app-container">
    <el-form :model="query" :inline="true" class="mb8">
      <el-form-item label="岗位名称"><el-input v-model="query.jobName" /></el-form-item>
      <el-form-item label="城市"><el-input v-model="query.city" /></el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">搜索</el-button></el-form-item>
    </el-form>
    <el-table :data="list" border>
      <el-table-column label="企业" prop="companyName" />
      <el-table-column label="岗位" prop="jobName" />
      <el-table-column label="方向" prop="categoryName" />
      <el-table-column label="城市" prop="city" />
      <el-table-column label="薪资范围" min-width="140">
        <template #default="scope">{{ scope.row.salaryMin }} - {{ scope.row.salaryMax }}</template>
      </el-table-column>
      <el-table-column label="要求" prop="skillKeywords" min-width="200" />
      <el-table-column label="操作" width="160">
        <template #default="scope">
          <el-button link type="primary" @click="showDetail(scope.row)">详情</el-button>
          <el-button link type="success" @click="handleApply(scope.row)">投递</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-drawer v-model="drawerOpen" title="岗位详情" size="45%" @close="detail = null">
      <template v-if="detail">
        <h3>{{ detail.jobName }}</h3>
        <p>{{ detail.companyName }} / {{ detail.city }}</p>
        <p>{{ detail.jobDesc }}</p>
        <p><b>技能关键词：</b>{{ detail.skillKeywords }}</p>
        <p><b>任职要求：</b>{{ detail.jobRequire }}</p>
      </template>
    </el-drawer>
  </div>
</template>

<script setup name="EmploymentStudentJob">
import { applyJob, listJobs } from '@/api/employment'

const { proxy } = getCurrentInstance()
const query = ref({})
const list = ref([])
const detail = ref(null)
const drawerOpen = ref(false)

function loadData() {
  listJobs(query.value).then(res => {
    list.value = res.data || []
  })
}

function showDetail(row) {
  detail.value = row
  drawerOpen.value = true
}

function handleApply(row) {
  applyJob(row.jobId).then(() => {
    proxy.$modal.msgSuccess('投递成功')
  })
}

loadData()
</script>


