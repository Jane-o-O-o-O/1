<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="24"><el-button type="primary" @click="handleAdd">上传材料</el-button></el-col>
    </el-row>
    <el-table :data="list" border>
      <el-table-column label="材料名称" prop="materialName" />
      <el-table-column label="材料类型" prop="materialType" />
      <el-table-column label="审核状态" prop="reviewStatus" />
      <el-table-column label="文件" min-width="180">
        <template #default="scope">
          <el-link :href="scope.row.fileUrl" target="_blank" type="primary">{{ scope.row.fileName || '查看文件' }}</el-link>
        </template>
      </el-table-column>
      <el-table-column label="审核意见" prop="reviewComment" min-width="180" />
      <el-table-column label="操作" width="160">
        <template #default="scope">
          <el-button link type="primary" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="open" :title="title" width="620px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="材料名称"><el-input v-model="form.materialName" /></el-form-item>
        <el-form-item label="材料类型">
          <el-select v-model="form.materialType" style="width: 100%">
            <el-option label="三方协议" value="agreement" />
            <el-option label="录用通知" value="offer" />
            <el-option label="就业证明" value="employment_cert" />
          </el-select>
        </el-form-item>
        <el-form-item label="文件上传"><file-upload v-model="form.fileUrl" :limit="1" /></el-form-item>
        <el-form-item label="文件名"><el-input v-model="form.fileName" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="open = false">取消</el-button>
        <el-button type="primary" @click="submit">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="EmploymentStudentMaterial">
import { addMaterial, deleteMaterial, listMaterials, updateMaterial } from '@/api/employment'

const { proxy } = getCurrentInstance()
const list = ref([])
const open = ref(false)
const title = ref('')
const form = ref({})

function loadData() {
  listMaterials().then(res => {
    list.value = res.data || []
  })
}

function handleAdd() {
  title.value = '上传材料'
  form.value = {}
  open.value = true
}

function handleEdit(row) {
  title.value = '编辑材料'
  form.value = { ...row }
  open.value = true
}

function handleDelete(row) {
  proxy.$modal.confirm(`确认删除材料 ${row.materialName} 吗？`).then(() => deleteMaterial(row.materialId)).then(() => {
    proxy.$modal.msgSuccess('删除成功')
    loadData()
  }).catch(() => {})
}

function submit() {
  const request = form.value.materialId ? updateMaterial : addMaterial
  request(form.value).then(() => {
    proxy.$modal.msgSuccess('保存成功')
    open.value = false
    loadData()
  })
}

loadData()
</script>
