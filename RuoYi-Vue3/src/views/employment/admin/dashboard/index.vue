<template>
  <div class="app-container dashboard-page">
    <el-row :gutter="16" class="mb8">
      <el-col v-for="card in cards" :key="card.label" :lg="4" :sm="8" :xs="12"><el-card shadow="never" class="metric-card"><div class="label">{{ card.label }}</div><div class="value">{{ card.value }}</div></el-card></el-col>
    </el-row>
    <el-row :gutter="16">
      <el-col :lg="12" :xs="24"><el-card shadow="never"><template #header>就业状态分布</template><div ref="pieRef" class="chart" /></el-card></el-col>
      <el-col :lg="12" :xs="24"><el-card shadow="never"><template #header>行业分布</template><div ref="barRef" class="chart" /></el-card></el-col>
    </el-row>
  </div>
</template>

<script setup name="EmploymentAdminDashboard">
import * as echarts from 'echarts'
import { getAdminDashboard } from '@/api/employment'

const pieRef = ref()
const barRef = ref()
const cards = ref([])
let pieChart
let barChart

function loadData() {
  getAdminDashboard().then(res => {
    const data = res.data || {}
    cards.value = [
      { label: '学生总数', value: data.totalStudents || 0 },
      { label: '已就业', value: data.employedStudents || 0 },
      { label: '未就业', value: data.unemployedStudents || 0 },
      { label: '困难学生', value: data.difficultStudents || 0 },
      { label: '待审材料', value: data.pendingMaterials || 0 },
      { label: '待审岗位', value: data.pendingJobs || 0 }
    ]
    pieChart ||= echarts.init(pieRef.value)
    barChart ||= echarts.init(barRef.value)
    pieChart.setOption({ tooltip: {}, series: [{ type: 'pie', data: data.statusPie || [] }] })
    barChart.setOption({ tooltip: {}, xAxis: { type: 'category', data: (data.industry || []).map(item => item.name) }, yAxis: { type: 'value' }, series: [{ type: 'bar', data: (data.industry || []).map(item => item.value), itemStyle: { color: '#3c8c6b' } }] })
  })
}

onMounted(loadData)
</script>

<style scoped lang="scss">
.metric-card .label { color: var(--el-text-color-secondary); }
.metric-card .value { font-size: 24px; font-weight: 700; margin-top: 10px; }
.chart { height: 320px; }
</style>
