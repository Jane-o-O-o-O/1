<template>
  <div class="app-container">
    <el-form :model="query" :inline="true" class="mb8">
      <el-form-item label="学生姓名"><el-input v-model="query.studentName" /></el-form-item>
      <el-form-item label="状态">
        <el-select v-model="query.currentStatus" clearable style="width: 140px">
          <el-option label="未就业" value="unemployed" />
          <el-option label="已就业" value="employed" />
          <el-option label="困难就业" value="difficult" />
        </el-select>
      </el-form-item>
      <el-form-item><el-button type="primary" @click="loadData">查询</el-button></el-form-item>
    </el-form>
    <el-table :data="list" border>
      <el-table-column label="学生" prop="studentName" />
      <el-table-column label="学号" prop="studentNo" />
      <el-table-column label="专业" prop="majorName" />
      <el-table-column label="班级" prop="className" />
      <el-table-column label="状态" prop="currentStatus" />
      <el-table-column label="风险分" prop="riskScore" />
      <el-table-column label="困难等级" prop="difficultyLevel" />
      <el-table-column label="预警等级" prop="warningLevel" />
      <el-table-column label="操作" width="140">
        <template #default="scope">
          <el-button link type="primary" @click="handleEdit(scope.row)">维护</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="open" title="维护就业状态" width="640px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="就业状态"><el-select v-model="form.currentStatus" style="width: 100%"><el-option label="未就业" value="unemployed" /><el-option label="已就业" value="employed" /><el-option label="困难就业" value="difficult" /></el-select></el-form-item>
        <el-form-item label="困难等级"><el-select v-model="form.difficultyLevel" style="width: 100%"><el-option label="正常" value="normal" /><el-option label="关注" value="attention" /><el-option label="重点" value="key" /></el-select></el-form-item>
        <el-form-item label="预警等级"><el-select v-model="form.warningLevel" style="width: 100%"><el-option label="低" value="low" /><el-option label="中" value="medium" /><el-option label="高" value="high" /></el-select></el-form-item>
        <el-form-item label="就业单位"><el-input v-model="form.employmentCompany" /></el-form-item>
        <el-form-item label="岗位名称"><el-input v-model="form.positionName" /></el-form-item>
        <el-form-item label="薪资"><el-input-number v-model="form.salaryAmount" :min="0" style="width: 100%" /></el-form-item>
        <el-form-item label="进度阶段"><el-input v-model="form.progressStage" /></el-form-item>
        <el-form-item label="原因说明"><el-input v-model="form.reasonNote" type="textarea" :rows="3" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="open = false">取消</el-button>
        <el-button type="primary" @click="submit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="EmploymentTeacherStatus">
import { listStatuses, updateStatus } from '@/api/employment'

const { proxy } = getCurrentInstance()
const query = ref({})
const list = ref([])
const open = ref(false)
const form = ref({})

function loadData() {
  listStatuses(query.value).then(res => {
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

loadData()
</script>
