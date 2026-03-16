<template>
  <div class="app-container employment-page">
    <el-row :gutter="16">
      <el-col :lg="16" :xs="24">
        <el-card shadow="never">
          <template #header>
            <div class="card-title">学生画像</div>
          </template>
          <el-form ref="formRef" :model="form" label-width="100px">
            <el-row :gutter="16">
              <el-col :md="12" :xs="24"><el-form-item label="学号"><el-input v-model="form.studentNo" /></el-form-item></el-col>
              <el-col :md="12" :xs="24"><el-form-item label="毕业年份"><el-input-number v-model="form.graduationYear" :min="2020" :max="2035" style="width: 100%" /></el-form-item></el-col>
              <el-col :md="12" :xs="24"><el-form-item label="专业"><el-input v-model="form.majorName" /></el-form-item></el-col>
              <el-col :md="12" :xs="24"><el-form-item label="班级"><el-input v-model="form.className" /></el-form-item></el-col>
              <el-col :md="12" :xs="24"><el-form-item label="GPA"><el-input-number v-model="form.gpa" :min="0" :max="4" :step="0.01" :precision="2" style="width: 100%" /></el-form-item></el-col>
              <el-col :md="12" :xs="24"><el-form-item label="期望城市"><el-input v-model="form.expectedCity" /></el-form-item></el-col>
              <el-col :md="12" :xs="24"><el-form-item label="期望薪资"><el-input-number v-model="form.expectedSalary" :min="0" :step="500" style="width: 100%" /></el-form-item></el-col>
              <el-col :md="12" :xs="24"><el-form-item label="求职意向"><el-input v-model="form.jobIntention" /></el-form-item></el-col>
              <el-col :xs="24"><el-form-item label="技能标签"><el-input v-model="form.skillTags" type="textarea" :rows="3" placeholder="例如：Java,Spring Boot,MySQL,Vue3" /></el-form-item></el-col>
              <el-col :xs="24"><el-form-item label="联系地址"><el-input v-model="form.contactAddress" /></el-form-item></el-col>
              <el-col :xs="24"><el-form-item label="备注"><el-input v-model="form.remark" type="textarea" :rows="2" /></el-form-item></el-col>
            </el-row>
            <el-form-item>
              <el-button type="primary" @click="submit">保存画像</el-button>
              <el-button @click="loadData">刷新</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :lg="8" :xs="24">
        <el-card shadow="never" class="status-card">
          <template #header>
            <div class="card-title">当前状态</div>
          </template>
          <el-descriptions :column="1" border>
            <el-descriptions-item label="就业状态"><dict-tag :options="emp_employment_status" :value="form.employmentStatus" /></el-descriptions-item>
            <el-descriptions-item label="困难等级"><dict-tag :options="emp_difficulty_level" :value="form.difficultyLevel" /></el-descriptions-item>
            <el-descriptions-item label="预警等级"><dict-tag :options="emp_warning_level" :value="form.warningLevel" /></el-descriptions-item>
          </el-descriptions>
          <div class="tip-text">学生端保存后，教师端和管理员端会基于这份画像自动重算风险分。</div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="EmploymentStudentProfile">
import { getStudentProfile, updateStudentProfile } from '@/api/employment'

const { proxy } = getCurrentInstance()
const { emp_employment_status, emp_difficulty_level, emp_warning_level } = proxy.useDict('emp_employment_status', 'emp_difficulty_level', 'emp_warning_level')

const form = ref({})

function loadData() {
  getStudentProfile().then(res => {
    form.value = res.data || {}
  })
}

function submit() {
  updateStudentProfile(form.value).then(() => {
    proxy.$modal.msgSuccess('保存成功')
    loadData()
  })
}

loadData()
</script>

<style scoped lang="scss">
.employment-page .card-title {
  font-size: 16px;
  font-weight: 600;
}
.status-card {
  margin-top: 0;
}
.tip-text {
  margin-top: 16px;
  color: var(--el-text-color-secondary);
  line-height: 1.7;
}
</style>
