<template>
  <div class="app-container dashboard-page">
    <el-row :gutter="16" class="mb8">
      <el-col v-for="card in cards" :key="card.label" :lg="6" :sm="12" :xs="24">
        <el-card shadow="never" class="metric-card"><div class="label">{{ card.label }}</div><div class="value">{{ card.value }}</div></el-card>
      </el-col>
    </el-row>
    <el-row :gutter="16">
      <el-col :lg="12" :xs="24"><el-card shadow="never"><template #header>就业状态分布</template><div ref="pieRef" class="chart" /></el-card></el-col>
      <el-col :lg="12" :xs="24"><el-card shadow="never"><template #header>月度跟踪趋势</template><div ref="trendRef" class="chart" /></el-card></el-col>
    </el-row>
  </div>
</template>

<script setup name="EmploymentTeacherDashboard">
import * as echarts from 'echarts'
import { getTeacherDashboard } from '@/api/employment'

const pieRef = ref()
const trendRef = ref()
const cards = ref([])
let pieChart
let trendChart

function renderCharts(data) {
  pieChart ||= echarts.init(pieRef.value)
  trendChart ||= echarts.init(trendRef.value)
  pieChart.setOption({ tooltip: {}, series: [{ type: 'pie', radius: ['40%', '70%'], data: data.statusPie || [] }] })
  trendChart.setOption({ tooltip: {}, xAxis: { type: 'category', data: (data.trend || []).map(item => item.name) }, yAxis: { type: 'value' }, series: [{ type: 'line', smooth: true, data: (data.trend || []).map(item => item.value) }] })
}

function loadData() {
  getTeacherDashboard().then(res => {
    const data = res.data || {}
    cards.value = [
      { label: '学生总数', value: data.totalStudents || 0 },
      { label: '已就业', value: data.employedStudents || 0 },
      { label: '未就业', value: data.unemployedStudents || 0 },
      { label: '困难学生', value: data.difficultStudents || 0 }
    ]
    renderCharts(data)
  })
}

onMounted(loadData)
</script>

<style scoped lang="scss">
.metric-card .label { color: var(--el-text-color-secondary); }
.metric-card .value { font-size: 28px; font-weight: 700; margin-top: 12px; }
.chart { height: 320px; }
</style>
