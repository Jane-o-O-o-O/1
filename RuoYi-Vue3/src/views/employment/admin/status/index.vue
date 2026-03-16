<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8"><el-col :span="24"><el-button @click="doExport">导出报表</el-button></el-col></el-row>
    <el-table :data="list" border>
      <el-table-column label="学生" prop="studentName" />
      <el-table-column label="学号" prop="studentNo" />
      <el-table-column label="专业" prop="majorName" />
      <el-table-column label="班级" prop="className" />
      <el-table-column label="就业状态" prop="currentStatus" />
      <el-table-column label="困难等级" prop="difficultyLevel" />
      <el-table-column label="预警等级" prop="warningLevel" />
      <el-table-column label="风险分" prop="riskScore" />
      <el-table-column label="操作" width="140"><template #default="scope"><el-button link type="primary" @click="handleEdit(scope.row)">维护</el-button></template></el-table-column>
    </el-table>
    <el-dialog v-model="open" title="维护状态" width="640px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="就业状态"><el-select v-model="form.currentStatus" style="width: 100%"><el-option label="未就业" value="unemployed" /><el-option label="已就业" value="employed" /><el-option label="困难就业" value="difficult" /></el-select></el-form-item>
        <el-form-item label="困难等级"><el-select v-model="form.difficultyLevel" style="width: 100%"><el-option label="正常" value="normal" /><el-option label="关注" value="attention" /><el-option label="重点" value="key" /></el-select></el-form-item>
        <el-form-item label="预警等级"><el-select v-model="form.warningLevel" style="width: 100%"><el-option label="低" value="low" /><el-option label="中" value="medium" /><el-option label="高" value="high" /></el-select></el-form-item>
        <el-form-item label="就业单位"><el-input v-model="form.employmentCompany" /></el-form-item>
        <el-form-item label="岗位名称"><el-input v-model="form.positionName" /></el-form-item>
        <el-form-item label="薪资"><el-input-number v-model="form.salaryAmount" :min="0" style="width: 100%" /></el-form-item>
        <el-form-item label="原因说明"><el-input v-model="form.reasonNote" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="open = false">取消</el-button><el-button type="primary" @click="submit">保存</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup name="EmploymentAdminStatus">
import { exportStatuses, listStatuses, updateStatus } from '@/api/employment'

const { proxy } = getCurrentInstance()
const list = ref([])
const open = ref(false)
const form = ref({})

function loadData() {
  listStatuses().then(res => {
    list.value = res.rows || []
  })
}

function handleEdit(row) {
  form.value = { ...row }
  open.value = true
}

function submit() {
  updateStatus(form.value).then(() => {
    proxy.$modal.msgSuccess('保存成功')
    open.value = false
    loadData()
  })
}

function doExport() {
  exportStatuses({})
}

loadData()
</script>
