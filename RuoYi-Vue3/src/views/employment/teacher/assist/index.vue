<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8"><el-col :span="24"><el-button type="primary" @click="handleAdd">新增帮扶记录</el-button></el-col></el-row>
    <el-table :data="list" border>
      <el-table-column label="学生" prop="studentName" />
      <el-table-column label="主题" prop="activityTitle" />
      <el-table-column label="类型" prop="activityType" />
      <el-table-column label="内容" prop="activityContent" min-width="220" />
      <el-table-column label="成效" prop="resultSummary" min-width="180" />
      <el-table-column label="时间" prop="recordTime" min-width="180" />
      <el-table-column label="操作" align="center" width="140">
        <template #default="{ row }">
          <el-button link type="success" @click="handleComplete(row)">此帮扶已完成</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="open" title="帮扶记录" width="680px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="学生">
          <el-select v-model="form.studentUserId" filterable placeholder="请选择学生" style="width: 100%">
            <el-option
              v-for="item in studentOptions"
              :key="item.studentUserId"
              :label="`${item.studentName || '-'} / ${item.studentNo || item.studentUserId}`"
              :value="item.studentUserId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="主题"><el-input v-model="form.activityTitle" /></el-form-item>
        <el-form-item label="类型"><el-input v-model="form.activityType" /></el-form-item>
        <el-form-item label="帮扶内容"><el-input v-model="form.activityContent" type="textarea" :rows="4" /></el-form-item>
        <el-form-item label="结果总结"><el-input v-model="form.resultSummary" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="下一步计划"><el-input v-model="form.nextPlan" type="textarea" :rows="3" /></el-form-item>
        <el-form-item label="记录时间">
          <el-date-picker v-model="form.recordTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
        </el-form-item>
      </el-form>
      <template #footer><el-button @click="open = false">取消</el-button><el-button type="primary" @click="submit">保存</el-button></template>
    </el-dialog>
  </div>
</template>

<script setup name="EmploymentTeacherAssist">
import { addAssist, deleteAssist, listAssists, listStatuses } from '@/api/employment'
import { parseTime } from '@/utils/ruoyi'

const { proxy } = getCurrentInstance()
const list = ref([])
const studentOptions = ref([])
const open = ref(false)
const form = ref({})

function loadData() {
  Promise.all([listAssists(), listStatuses({})]).then(([assistRes, statusRes]) => {
    list.value = assistRes.data || []
    studentOptions.value = statusRes.rows || []
  })
}

function handleAdd() {
  form.value = {
    recordTime: parseTime(new Date()),
  }
  open.value = true
}

function submit() {
  const payload = {
    studentUserId: form.value.studentUserId,
    activityTitle: form.value.activityTitle,
    activityType: form.value.activityType,
    activityContent: form.value.activityContent,
    resultSummary: form.value.resultSummary,
    nextPlan: form.value.nextPlan,
    recordTime: form.value.recordTime,
  }
  addAssist(payload).then(() => {
    proxy.$modal.msgSuccess('保存成功')
    open.value = false
    loadData()
  })
}

function handleComplete(row) {
  proxy.$modal.confirm(`确认将“${row.activityTitle || '该帮扶记录'}”标记为已完成并删除吗？`).then(() => deleteAssist(row.recordId)).then(() => {
    proxy.$modal.msgSuccess('删除成功')
    loadData()
  }).catch(() => {})
}

loadData()
</script>
