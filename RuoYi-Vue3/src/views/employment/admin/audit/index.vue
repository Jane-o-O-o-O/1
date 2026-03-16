<template>
  <div class="app-container">
    <el-tabs v-model="active">
      <el-tab-pane label="岗位审核" name="job">
        <el-table :data="jobs" border>
          <el-table-column label="企业" prop="companyName" />
          <el-table-column label="岗位" prop="jobName" />
          <el-table-column label="方向" prop="categoryName" />
          <el-table-column label="审核状态" prop="auditStatus" />
          <el-table-column label="操作" width="180"><template #default="scope"><el-button link type="success" @click="audit(scope.row, 'approved')">通过</el-button><el-button link type="danger" @click="audit(scope.row, 'rejected')">驳回</el-button></template></el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="材料审核" name="material">
        <el-table :data="materials" border>
          <el-table-column label="学生" prop="studentName" />
          <el-table-column label="材料名称" prop="materialName" />
          <el-table-column label="材料类型" prop="materialType" />
          <el-table-column label="审核状态" prop="reviewStatus" />
          <el-table-column label="操作" width="180"><template #default="scope"><el-button link type="success" @click="review(scope.row, 'approved')">通过</el-button><el-button link type="danger" @click="review(scope.row, 'rejected')">驳回</el-button></template></el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup name="EmploymentAdminAudit">
import { auditJob, listJobs, listMaterials, reviewMaterial } from '@/api/employment'

const { proxy } = getCurrentInstance()
const active = ref('job')
const jobs = ref([])
const materials = ref([])

function loadData() {
  listJobs({ auditStatus: 'pending' }).then(res => { jobs.value = res.data || [] })
  listMaterials({ reviewStatus: 'pending' }).then(res => { materials.value = res.data || [] })
}

function audit(row, status) {
  auditJob(row.jobId, status).then(() => {
    proxy.$modal.msgSuccess('审核完成')
    loadData()
  })
}

function review(row, status) {
  reviewMaterial({ ...row, reviewStatus: status }).then(() => {
    proxy.$modal.msgSuccess('审核完成')
    loadData()
  })
}

loadData()
</script>
