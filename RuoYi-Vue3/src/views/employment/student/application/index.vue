<template>
  <div class="app-container">
    <el-table :data="list" border>
      <el-table-column label="企业" prop="companyName" />
      <el-table-column label="岗位" prop="jobName" />
      <el-table-column label="简历版本" prop="resumeTitle" />
      <el-table-column label="匹配度" prop="matchScore" />
      <el-table-column label="状态" prop="applyStatus" />
      <el-table-column label="进度说明" prop="progressRemark" min-width="220" />
      <el-table-column label="投递时间" prop="appliedTime" min-width="180" />
    </el-table>
  </div>
</template>

<script setup name="EmploymentStudentApplication">
import { listApplications } from '@/api/employment'

const list = ref([])

function loadData() {
  listApplications().then(res => {
    list.value = res.data || []
  })
}

loadData()
</script>
