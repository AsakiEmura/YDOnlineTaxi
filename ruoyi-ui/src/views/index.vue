<template>
  <div class="dashboard-editor-container">

    <panel-group @handleSetLineChartData="handleSetLineChartData" />

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>

  </div>
</template>

<script>
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import {LineChartData} from '@/api/itaxbill/index';
/*
const lineChartData = {
  newVisitis: {
    actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  invoice: {
    actualData: [180, 160, 151, 106, 145, 150, 130]
  },
  purchases: {
    actualData: [120, 90, 100, 138, 142, 130, 130]
  },
  reimburse: {
    actualData: [120, 82, 91, 154, 162, 140, 130]
  }
}
*/
export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart,
  },
  data() {
    return {
      lineChart:{
        newVisitis:{actualData:[]},
        invoice:{actualData:[]},
        purchases:{actualData:[]},
        reimburse:{actualData:[]},
        },
      lineChartData:{},
    }
  },
  created(){
    console.log(111)
    // LineChartData().then(res=>{
    //   this.lineChart.newVisitis.actualData = res.rows[0].newVisitis;
    //   this.lineChart.invoice.actualData = res.rows[0].invoice;
    //   this.lineChart.purchases.actualData = res.rows[0].purchases;
    //   this.lineChart.reimburse.actualData = res.rows[0].reimburse;
    //   this.lineChartData = this.lineChart.newVisitis;
    // })
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = this.lineChart[type]
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
