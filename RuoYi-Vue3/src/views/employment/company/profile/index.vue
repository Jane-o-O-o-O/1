<template>
  <div class="app-container">
    <el-card shadow="never">
      <el-form :model="form" label-width="100px">
        <el-row :gutter="16">
          <el-col :md="12" :xs="24"><el-form-item label="企业名称"><el-input v-model="form.companyName" /></el-form-item></el-col>
          <el-col :md="12" :xs="24"><el-form-item label="行业"><el-input v-model="form.industryName" /></el-form-item></el-col>
          <el-col :md="12" :xs="24"><el-form-item label="城市"><el-input v-model="form.city" /></el-form-item></el-col>
          <el-col :md="12" :xs="24"><el-form-item label="规模"><el-input v-model="form.companySize" /></el-form-item></el-col>
          <el-col :md="12" :xs="24"><el-form-item label="联系人"><el-input v-model="form.contactName" /></el-form-item></el-col>
          <el-col :md="12" :xs="24"><el-form-item label="联系电话"><el-input v-model="form.contactPhone" /></el-form-item></el-col>
          <el-col :xs="24"><el-form-item label="企业地址"><el-input v-model="form.address" /></el-form-item></el-col>
          <el-col :xs="24"><el-form-item label="企业简介"><el-input v-model="form.companyIntro" type="textarea" :rows="4" /></el-form-item></el-col>
          <el-col :xs="24"><el-form-item label="资质文件"><file-upload v-model="form.licenseFileUrl" :limit="1" /></el-form-item></el-col>
        </el-row>
        <el-form-item><el-button type="primary" @click="submit">保存</el-button></el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup name="EmploymentCompanyProfile">
import { getCompanyProfile, updateCompanyProfile } from '@/api/employment'

const { proxy } = getCurrentInstance()
const form = ref({})

function loadData() {
  getCompanyProfile().then(res => {
    form.value = res.data || {}
  })
}

function submit() {
  updateCompanyProfile(form.value).then(() => {
    proxy.$modal.msgSuccess('保存成功')
    loadData()
  })
}

loadData()
</script>
