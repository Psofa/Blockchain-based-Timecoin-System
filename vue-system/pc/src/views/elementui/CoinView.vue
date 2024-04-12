<template>
    <div class="Box">
      <el-dialog
        title="操作时间币"
        :visible.sync="dialogVisible"
        width="45%"
        :append-to-body="true"
        :modal-append-to-body="false">
        <h3>添加时间币</h3>
        <el-input-number :min="0" placeholder="添加时间币个数" style="width: 60%;"></el-input-number>
        <el-input v-model="contentCoin" style="margin-top: 30px;width: 60%;" placeholder="请输入添加原因"></el-input>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="editCoin">确 定</el-button>
        </span>
      </el-dialog>
      <div>
        <!-- 仪表板 -->
        <div class="dashboard">
          <h2>时间币总量: {{ totalCoins }}</h2>
          <div class="summary">
          <p>最近交易情况: {{ recentTransactionsSummary }}</p>
          <p>用户数量统计: {{ userCount }}</p>
          <p>总交易次数统计: {{ totalTransactions }}</p>
          </div>
          <div class="charts">
          <div class="pie-chart" ref="pieChart"></div>
          <div class="bar-chart" ref="barChart"></div>
          <div class="line-chart" ref="lineChart"></div>
          </div>
          <div class="charts1">
              <div class="dashboard-item">
              <div id="pieChart" class="chart"></div>
              </div>
              <div class="dashboard-item">
              <div id="barChart" class="chart"></div>
              </div>
          </div>
        </div>
    
        <!-- 时间币交易记录 -->
        <el-card class="transaction-history" shadow="hover">
          <h3 slot="header">时间币交易记录</h3>
          <el-input v-model="searchQuery" placeholder="搜索交易记录"></el-input>
          <el-table :data="filteredTransactions" style="width: 100%">
            <el-table-column prop="time" label="交易时间"></el-table-column>
            <el-table-column prop="type" label="交易类型"></el-table-column>
            <el-table-column prop="amount" label="交易金额"></el-table-column>
            <el-table-column prop="parties" label="交易双方"></el-table-column>
          </el-table>
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="filteredTransactions.length">
          </el-pagination>
        </el-card>
    
        <!-- 用户时间币余额管理 -->
        <el-card class="user-balance-management" shadow="hover">
          <h3 slot="header">用户时间币余额管理</h3>
          <el-input v-model="userSearchQuery" placeholder="搜索用户"></el-input>
          <el-table :data="filteredUsers" style="width: 100%">
            <el-table-column prop="id" label="用户ID"></el-table-column>
            <el-table-column prop="username" label="用户名"></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>
            <el-table-column prop="coinBalance" label="时间币余额"></el-table-column>
            <el-table-column label="操作">
              <template>
                <el-button @click="editCoinBalance" type="text">时间币添加</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="filteredUsers.length">
          </el-pagination>
        </el-card>
      </div>
    </div>
  </template>
  
  <script>
  import * as echarts from "echarts";

  export default {
    data() {
      return {
        dialogVisible: false,
        contentCoin: "",
        totalCoins: 1000000,
        recentTransactionsSummary: "最近交易情况摘要",
        userCount: 5000,
        totalTransactions: 10000,
        searchQuery: "",
        userSearchQuery: "",
        transactions: [
            { id: 1, time: "2024-04-10 09:00", type: "服务", amount: 100, parties: "用户A <-> 平台" },
            { id: 2, time: "2024-04-10 10:00", type: "消费", amount: -50, parties: "用户B <-> 平台" },
            { id: 3, time: "2024-04-09 11:00", type: "服务", amount: 200, parties: "用户C <-> 平台" },
            { id: 4, time: "2024-04-09 12:00", type: "消费", amount: -75, parties: "用户D <-> 平台" },
            { id: 5, time: "2024-04-08 13:00", type: "服务", amount: 150, parties: "用户E <-> 平台" },
            { id: 6, time: "2024-04-08 14:00", type: "消费", amount: -90, parties: "用户F <-> 平台" },
            { id: 7, time: "2024-04-07 15:00", type: "服务", amount: 300, parties: "用户G <-> 平台" },
        ],
        users: [
            { id: 1, username: "UserA", email: "usera@example.com", coinBalance: 50 },
            { id: 2, username: "UserB", email: "userb@example.com", coinBalance: 25 },
            { id: 3, username: "UserC", email: "userc@example.com", coinBalance: 6 },
            { id: 4, username: "UserD", email: "userd@example.com", coinBalance: 20 },
            { id: 5, username: "UserE", email: "usere@example.com", coinBalance: 80 },
            { id: 6, username: "UserF", email: "userf@example.com", coinBalance: 70 },
            { id: 7, username: "UserG", email: "userg@example.com", coinBalance: 55 },
        ],
        currentPage: 1,
        pageSize: 5
      };
    },
    mounted() {
        this.renderPieChart();
        this.renderBarChart();
        this.renderLineChart();
        this.renderPieChart1();
        this.renderBarChart1();
    },
    computed: {
      filteredTransactions() {
        return this.transactions.filter(transaction => {
          return transaction.parties.toLowerCase().includes(this.searchQuery.toLowerCase());
        });
      },
      filteredUsers() {
        return this.users.filter(user => {
          return user.username.toLowerCase().includes(this.userSearchQuery.toLowerCase());
        });
      }
    },
    methods: {
      editCoinBalance() {
        // 编辑用户时间币余额的操作，可以弹出对话框或者跳转到编辑页面等
        this.dialogVisible = true;
      },
      editCoin() {
        // 编辑用户时间币余额的操作，可以弹出对话框或者跳转到编辑页面等
        this.dialogVisible = false;
        this.$message({
          message: "时间币添加成功",
          type: "success"
        });
      },
      handleSizeChange(val) {
        this.pageSize = val;
      },
      handleCurrentChange(val) {
        this.currentPage = val;
      },
      renderPieChart() {
        const pieChart = echarts.init(this.$refs.pieChart);
        const option = {
            title: {
            text: '时间币交易类型分布',
            left: 'center'
            },
            tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)',
            },
            legend: {
                orient: 'vertical',
                left: 10,
                data: ['志愿获取', '消费', '其他']
            },
            series: [
            {
                name: '交易类型',
                type: 'pie',
                radius: ['40%', '70%'],
                avoidLabelOverlap: false,
                label: {
                show: false,
                position: 'center'
                },
                emphasis: {
                label: {
                    show: true,
                    fontSize: '30',
                    fontWeight: 'bold'
                }
                },
                labelLine: {
                show: false
                },
                data: [
                { value: 500, name: '志愿获取' },
                { value: 300, name: '消费' },
                { value: 200, name: '其他' }
                ]
            }
            ]
        };
        pieChart.setOption(option);
        },
        renderBarChart() {
        const barChart = echarts.init(this.$refs.barChart);
        const option = {
            title: {
            text: '部分用户时间币余额',
            left: 'center'
            },
            tooltip: {
            trigger: 'axis',
                axisPointer: {
                    type: 'shadow'
                }
            },
            xAxis: {
            type: 'category',
            data: ['用户A', '用户B', '用户C', '用户D', '用户E']
            },
            yAxis: {
            type: 'value'
            },
            series: [
            {
                name: '时间币余额',
                type: 'bar',
                data: [
                { value: 500, itemStyle: { color: '#0099cc' } },
                { value: 750, itemStyle: { color: '#ffcc99' } },
                { value: 600, itemStyle: { color: '#66cccc' } },
                { value: 900, itemStyle: { color: '#ff6666' } },
                { value: 800, itemStyle: { color: '#cc99ff' } }
                ]
            }
            ]
        };
        barChart.setOption(option);
        },
        renderLineChart() {
        const lineChart = echarts.init(this.$refs.lineChart);
        const option = {
            title: {
            text: '消费与获取时间币趋势',
            left: 'center'
            },
            tooltip: {
            trigger: 'axis'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['消费时间币', '获取时间币']
            },
            xAxis: {
            type: 'category',
            data: ['2024-04-01', '2024-04-02', '2024-04-03', '2024-04-04', '2024-04-05']
            },
            yAxis: {
            type: 'value'
            },
            series: [
            {
                name: '消费时间币',
                type: 'line',
                data: [100, 200, 150, 300, 250]
            },
            {
                name: '获取时间币',
                type: 'line',
                data: [50, 75, 60, 90, 80]
            }
            ]
        };
            lineChart.setOption(option);
        },
        renderPieChart1() {
        const pieChart = echarts.init(document.getElementById('pieChart'));
        const pieOption = {
            title: {
            text: '用户活跃度分布',
            left: 'center'
            },
            tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            legend: {
            orient: 'vertical',
            left: 10,
            data: ['高活跃用户', '中活跃用户', '低活跃用户']
            },
            series: [
            {
                name: '活跃度分布',
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                show: false,
                position: 'center'
                },
                emphasis: {
                label: {
                    show: true,
                    fontSize: '30',
                    fontWeight: 'bold'
                }
                },
                labelLine: {
                show: false
                },
                data: [
                { value: 335, name: '高活跃用户' },
                { value: 310, name: '中活跃用户' },
                { value: 234, name: '低活跃用户' }
                ]
            }
            ]
        };
        pieChart.setOption(pieOption);
        },
        renderBarChart1() {
        const barChart = echarts.init(document.getElementById('barChart'));
        const barOption = {
            title: {
            text: '用户地区分布',
            left: 'center'
            },
            tooltip: {
            trigger: 'axis',
            axisPointer: { 
                type: 'shadow' 
            }
            },
            legend: {
            left: 10,
            data: ['用户数量']
            },
            xAxis: {
            type: 'category',
            data: ['北京', '上海', '广州', '深圳', '成都']
            },
            yAxis: {
            type: 'value',
            name: '数量'
            },
            series: [
            {
                name: '用户数量',
                type: 'bar',
                data: [
                { value: 400, itemStyle: { color: '#0099cc' } },
                { value: 1200, itemStyle: { color: '#ffcc99' } },
                { value: 1000, itemStyle: { color: '#66cccc' } },
                { value: 1000, itemStyle: { color: '#ff6666' } },
                { value: 600, itemStyle: { color: '#cc99ff' } }
                ]
            }
            ]
        };
        barChart.setOption(barOption);
        },
    }
  };
  </script>
  
<style scoped>
/* 样式可以根据需要自行添加 */
.Box {
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
}
.dashboard {
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: white;
  margin-bottom: 20px;
}

.summary p {
  margin: 5px 0;
}

.charts {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}
.charts1 {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  width: 70%;
}

.pie-chart,
.bar-chart,
.line-chart {
  width: 30%;
  height: 300px;
}
.dashboard-item {
  width: 50%;
}

.chart {
  height: 300px;
}
.summary p {
    margin: 5px 0;
}
.transaction-history,
.user-balance-management {
    margin-bottom: 20px;
}
</style>
  