<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8"><el-col :span="24"><el-button @click="doExport">导出预警</el-button></el-col></el-row>
    <el-table :data="list" border>
      <el-table-column label="学生" prop="studentName" />
      <el-table-column label="学号" prop="studentNo" />
      <el-table-column label="专业" prop="majorName" />
      <el-table-column label="教师" prop="teacherName" />
      <el-table-column label="预警等级" prop="warningLevel" />
      <el-table-column label="困难等级" prop="difficultyLevel" />
      <el-table-column label="风险分" prop="riskScore" />
      <el-table-column label="原因" prop="reasonNote" min-width="260" />
    </el-table>
  </div>
</template>

<script setup name="EmploymentAdminWarning">
import { exportWarnings, listWarnings } from '@/api/employment'

const list = ref([])

function loadData() {
  listWarnings().then(res => {
    list.value = res.rows || []
  })
}

function doExport() {
  exportWarnings({})
}

loadData()
</script>
