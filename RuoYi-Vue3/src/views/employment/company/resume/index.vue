<template>
  <div class="app-container">
    <el-table :data="list" border>
      <el-table-column label="学生" prop="studentName" />
      <el-table-column label="专业" prop="majorName" />
      <el-table-column label="岗位" prop="jobName" />
      <el-table-column label="简历" prop="resumeTitle" />
      <el-table-column label="技能标签" prop="skillTags" min-width="180" />
      <el-table-column label="匹配度" prop="matchScore" />
      <el-table-column label="状态" prop="applyStatus" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button link type="warning" @click="mark(scope.row, 'interview')">邀约面试</el-button>
          <el-button link type="success" @click="mark(scope.row, 'offer')">录用</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup name="EmploymentCompanyResume">
import { listApplications, updateApplicationStatus } from '@/api/employment'

const { proxy } = getCurrentInstance()
const list = ref([])

function loadData() {
  listApplications().then(res => {
    list.value = res.data || []
  })
}

function mark(row, status) {
  updateApplicationStatus({ applicationId: row.applicationId, applyStatus: status, progressRemark: status === 'offer' ? '企业已发放录用意向' : '已发送面试邀约' }).then(() => {
    proxy.$modal.msgSuccess('处理成功')
    loadData()
  })
}

loadData()
</script>
