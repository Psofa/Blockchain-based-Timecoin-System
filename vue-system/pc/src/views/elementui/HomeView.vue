<template>
  <div class="dashboard">
    <div class="container">
      <!-- 左侧内容 -->
      <div class="left">
        <div class="panel">
          <!-- 个人信息 -->
          <div class="user-info">
            <img v-if="avatar" class="avatar" :src="avatar" alt="">
            <span v-else class="username">{{ name?.charAt(0) }}</span>
            <div class="company-info">
              <div class="title">
                杭州云象网络技术有限公司
                <span>体验版</span>
              </div>
              <div class="depart">{{ name }} ｜ {{ company }}-{{ departmentName }}</div>
            </div>
          </div>
          <!-- 代办 -->
          <div class="todo-list">
            <div class="todo-item">
              <span>时间币总个数</span>
              <!-- 起始值 终点值 动画时间 -->
              <count-to
                :start-val="0"
                :end-val="homeData.coinTotal"
                :duration="1000"
              />
            </div>
            <div class="todo-item">
              <span>注册人数</span>
              <count-to
                :start-val="0"
                :end-val="homeData.registTotal"
                :duration="1000"
              />
            </div>
            <div class="todo-item">
              <span>活动个数</span>
              <count-to
                :start-val="0"
                :end-val="homeData.ActivityTotal"
                :duration="1000"
              />
            </div>
            <div class="todo-item">
              <span>志愿者人数</span>
              <count-to
                :start-val="0"
                :end-val="homeData.volunteerTotal"
                :duration="1000"
              />
            </div>
            <div class="todo-item">
              <span>老人人数</span>
              <count-to
                :start-val="0"
                :end-val="homeData.oldManTotal"
                :duration="1000"
              />
            </div>
            <div class="todo-item">
              <span>接口总访问</span>
              <count-to
                :start-val="0"
                :end-val="homeData.interfaceAccessTotal"
                :duration="1000"
              />
            </div>
            <div class="todo-item">
              <span>在线人数</span>
              <count-to
                :start-val="0"
                :end-val="homeData.onlineTotal"
                :duration="1000"
              />
            </div>
          </div>
        </div>
        <!-- 快捷入口 -->
        <div class="panel">
          <div class="panel-title">快捷入口</div>
          <div class="quick-entry">
            <div class="entry-item">
              <div class="entry-icon role" />
              <span>用户管理</span>
            </div>
            <div class="entry-item">
              <div class="entry-icon salary" />
              <span>时间币管理</span>
            </div>
            <div class="entry-item">
              <div class="entry-icon bpm" />
              <span>服务管理</span>
            </div>
            <div class="entry-item">
              <div class="entry-icon departMent" />
              <span>活动管理</span>
            </div>
            <div class="entry-item">
              <div class="entry-icon support" />
              <span>人工服务</span>
            </div>
            <div class="entry-item">
              <div class="entry-icon home" />
              <span>系统管理</span>
            </div>
          </div>
        </div>
        <!-- 图表数据 -->
        <div class="panel">
          <div class="panel-title">用户注册数据</div>
          <div class="chart-container">
            <div class="chart-info">
              <div class="info-main">
                <span>注册人数</span>
                <!-- homeData: {} -->
                <count-to
                  :start-val="0"
                  :end-val="homeData.registTotal"
                  :duration="1000"
                />
              </div>
              <div class="info-list">
                <div class="info-list-item">
                  <span>志愿者(人)</span>
                  <count-to
                    :start-val="0"
                    :end-val="homeData.volunteerTotal"
                    :duration="1000"
                  />
                </div>
                <div class="info-list-item">
                  <span>老人(人)</span>
                  <count-to
                    :start-val="0"
                    :end-val="homeData.oldManTotal"
                    :duration="1000"
                  />
                </div>
                <div class="info-list-item">
                  <span>管理员(人)</span>
                  <count-to
                    :start-val="0"
                    :end-val="homeData.AdministratorTotal"
                    :duration="1000"
                  />
                </div>
              </div>
            </div>
            <div class="chart">
              <div id="myChart0" style="width: 600px; height: 400px;"></div>
            </div>
          </div>
        </div>
        <!-- 图表数据 -->
        <div class="panel">
          <div class="panel-title">活动服务数据</div>
          <div class="chart-container">
            <div class="chart-info">
              <div class="info-main">
                <span>活动个数</span>
                <count-to
                  :start-val="0"
                  :end-val="homeData.ActivityTotal"
                  :duration="1000"
                />
              </div>
              <div class="info-list">
                <div class="info-list-item">
                  <span>待审核(个)</span>
                  <count-to
                    :start-val="0"
                    :end-val="homeData.processingActivityTotal"
                    :duration="1000"
                  />
                </div>
                <div class="info-list-item">
                  <span>报名中(个)</span>
                  <count-to
                    :start-val="0"
                    :end-val="homeData.siginActivityTotal"
                    :duration="1000"
                  />
                </div>
                <div class="info-list-item">
                  <span>进行中(个)</span>
                  <count-to
                    :start-val="0"
                    :end-val="homeData.continueActivityTotal"
                    :duration="1000"
                  />
                </div>
                <div class="info-list-item">
                  <span>已结束(个)</span>
                  <count-to
                    :start-val="0"
                    :end-val="homeData.endActivityTotal"
                    :duration="1000"
                  />
                </div>
              </div>
            </div>
            <div class="chart">
              <div
                class="HelloWorld echart-box"
                id="myChart1"
                :style="{ width: '520px', height: '400px',background:'#ffffff'}"
              ></div>
            </div>
          </div>
        </div>
      </div>
      <!-- 右侧内容 -->
      <div class="right">
        <!-- 帮助链接 -->
        <div class="panel">
          <div class="help">
            <div class="help-left">
              <div class="panel-title">帮助链接</div>
              <div class="help-list">
                <div class="help-block">
                  <i class="icon-entry" />
                  入门指南
                </div>
                <div class="help-block">
                  <i class="icon-help" />
                  在线帮助手册
                </div>
                <div class="help-block">
                  <i class="icon-support" />
                  联系技术支持
                </div>
                <div class="help-block">
                  <i class="icon-add" />
                  添加链接
                </div>
              </div>
            </div>
            <div class="help-right">
              <div class="calendar">
                <!-- <el-calendar /> -->
                <el-calendar />
              </div>
            </div>
          </div>
        </div>
        <!-- 通知公告 -->
        <div class="panel">
          <div class="panel-title">通知公告</div>
          <div class="information-list">
            <div v-for="(item,index) in list" :key="index" class="information-list-item">
              <img :src="require('@/assets/myResource/information1.jpg')" />
              <div style="margin-left:30px">
                <p style="margin-bottom: 0px !important;margin-top: 15px !important;">
                  {{ item.notice }}
                </p>
                <p style="margin-top: 3px !important;">
                  {{ item.createTime }}
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CountTo from 'vue-count-to'
import * as echarts from "echarts";

export default {
  name: 'HomeView',
  components: {
    CountTo,
  },
  data() {
    return {
      homeData : {
        coinTotal: 1000,
        registTotal: 500,
        ActivityTotal: 450,
        interfaceAccessTotal: 5000,
        onlineTotal: 213,
        
        volunteerTotal: 394,
        oldManTotal: 98,
        AdministratorTotal: 8,
        
        processingActivityTotal: 90,
        siginActivityTotal: 135,
        continueActivityTotal: 180,
        endActivityTotal: 45,
      }, // 存放首页数据的对象
      list: [],
      name: '张三',
      avatar: '',
      company: '杭州云象网络技术有限公司',
      departmentName: '技术部',
      // 柱状图
      
      // 扇形图
      datas: [
        { value: 20, name: '未审核' },
        { value: 30, name: '报名中' },
        { value: 40, name: '进行中' },
        { value: 10, name: '已结束' },
      ],
    }
  },
  mounted() {
    this.renderBarChart();
    this.drawLine();
    // 生成示例数据
    this.generateSampleData();
  },
  created() {
    
  },
  methods: {
    renderBarChart() {
      // 初始化 ECharts 实例
      let myChart = echarts.init(document.getElementById('myChart0'));

      // 配置图表参数
      let options = {
        title: {
          text: '老人和志愿者的登录人数最近6周',
          left: 'center',
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          left: 'left', // 设置图例左对齐
          data: ['老人', '志愿者']
        },
        xAxis: {
          type: 'category',
          data: ['第1周', '第2周', '第3周', '第4周', '第5周', '第6周']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '老人',
            type: 'bar',
            data: [100, 200, 150, 80, 70, 110]
          },
          {
            name: '志愿者',
            type: 'bar',
            data: [80, 130, 100, 60, 50, 90]
          }
        ]
      };

      // 绘制图表
      myChart.setOption(options);
    },
    drawLine() {
      // 初始化 ECharts 实例
      let myChart = echarts.init(document.getElementById('myChart1'));

      // 配置图表参数
      let options = {
        title: {
          text: '活动状态分析',
          left: 'center',
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)',
        },
        legend: {
          orient: 'vertical',
          left: 'left',
          data: ['未审核', '报名中', '进行中', '已结束'],
        },
        series: [
          {
            name: '活动状态',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: this.datas,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
              },
            },
          },
        ],
      };

      // 绘制图表
      myChart.setOption(options);
    },
    generateSampleData() {
      // 生成示例数据
      const sampleData = [
        { notice: '通知公告1', createTime: '2024-04-08' },
        { notice: '通知公告2', createTime: '2024-04-07' },
        { notice: '通知公告3', createTime: '2024-04-06' },
        { notice: '通知公告4', createTime: '2024-04-05' },
        { notice: '通知公告5', createTime: '2024-04-04' }
      ];

      // 将示例数据赋值给列表
      this.list = sampleData;
    },
  },
}
</script>

<style scoped lang="scss">
.dashboard {
  background: #f5f6f8;
  width: 100%;
  min-height: calc(100vh - 80px);

  ::v-deep .el-calendar-day {
  height:  40px;
 }
 ::v-deep .el-calendar-table__row td,::v-deep .el-calendar-table tr td:first-child, ::v-deep .el-calendar-table__row td.prev{
  border:none;
 }

.date-content {
  height: 40px;
  text-align: center;
  line-height: 40px;
  font-size: 14px;
}
.date-content .rest {
  color: #fff;
  border-radius: 50%;
  background: rgb(250, 124, 77);
  width: 20px;
  height: 20px;
  line-height: 20px;
  display: inline-block;
  font-size: 12px;
  margin-left: 10px;
}
.date-content .text{
  width: 20px;
  height: 20px;
  line-height: 20px;
 display: inline-block;

}
::v-deep .el-calendar-table td.is-selected .text{
   background: #409eff;
   color: #fff;
   border-radius: 50%;
 }
 ::v-deep .el-calendar__header {
   display: none
 }
  .container {
    display: flex;
    .right {
      width: 40%;
      .panel {
        margin-left: 8px;
      }
      :nth-child(1) {
        margin-top: 0;
      }
    }
    .left {
      flex: 1;
      :nth-child(1) {
        margin-top: 0;
      }
    }
    .panel {
      background-color: #fff;

      margin-top: 8px;
      padding: 20px;
      .panel-title {
        font-size: 16px;
        color: #383c4e;
        font-weight: 500;
      }
      // 用户信息样式
      .user-info {
        display: flex;
        .avatar {
          width: 48px;
          height: 48px;
          border-radius: 12px;
          background-color: #d9d9d9;
          line-height: 48px;
          text-align: center;
        }
         .username {
           width: 30px;
           height: 30px;
           text-align: center;
           line-height: 30px;
           border-radius: 50%;
           background: #04c9be;
           color: #fff;
           margin-right: 4px;
         }
        .company-info {
          margin-left: 10px;
          height: 48px;
          display: flex;
          flex-direction: column;
          justify-content: space-around;
          .title {
            color: #383c4e;
            font-weight: 500;
            font-size: 16px;
            font-family: PingFang SC, PingFang SC-Medium;
            span {
              font-size: 12px;
              background: #f5f6f8;
              text-align: center;
              padding: 2px 8px;
              border-radius: 2px;
              color: #697086;
            }
          }
          .depart {
            font-size: 14px;
            color: #697086;
            font-weight: 400;
          }
        }
      }
      // 代办样式
      .todo-list {
        margin-top: 10px;
        display: flex;
        flex-wrap: wrap;
        .todo-item {
          width: 18%;
          height: 90px;
          display: flex;
          flex-direction: column;
          padding: 10px;
          justify-content: space-around;
          :nth-child(1) {
            color: #697086;
            font-size: 14px;
          }
          :nth-child(2) {
            color: #383c4e;
            font-size: 30px;
            font-weight: 500;
          }
        }
      }
      // 快捷入口
      .quick-entry {
        margin-top: 16px;
        display: flex;
        .entry-item {
          display: flex;
          flex-direction: column;
          align-items: center;
          margin-left: 60px;
          &:nth-child(1) {
            margin-left: 0px;
          }
          .entry-icon {
            width: 40px;
            height: 40px;
            border-radius: 10px;
            background: #f5f6f8;
            background-size: cover;
            &.approval {
              background-image: url('~@/assets/common/approval.png');
            }
             &.social {
              background-image: url('~@/assets/common/social.png');
            }
             &.salary {
              background-image: url('~@/assets/common/salary.png');
            }
            &.role {
              background-image: url('~@/assets/common/role.png');
            }
            &.bpm {
              background-image: url('~@/assets/common/bpm.png');
            }
            &.departMent {
              background-image: url('~@/assets/common/departMent.png');
            }
            &.support {
              background-image: url('~@/assets/common/support.png');
            }
            &.home {
              background-image: url('~@/assets/common/home.png');
            }
          }
          span {
            color: #697086;
            font-size: 14px;
            margin-top: 8px;
          }
        }
      }
      // 图表数据
      .chart-container {
        display: flex;
        .chart-info {
         width: 240px;
          margin-top: 10px;
          .info-main {
            padding: 10px;
            display: flex;
            flex-direction: column;
            :nth-child(1) {
              font-size: 14px;
              color: #697086;
            }
            :nth-child(2) {
              margin-top: 10px;
              font-size: 30px;
              color: #04c9be;
              font-weight: 500;
            }
          }
          .info-list {
            background: #f5f6f8;
            border-radius: 4px;
            padding: 12px 15px;
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            .info-list-item {
              width: 50%;
              margin-top: 10px;
              display: flex;
              flex-direction: column;

              :nth-child(1) {
                font-size: 14px;
                color: #697086;
              }
              :nth-child(2) {
                margin-top: 10px;
                font-size: 30px;
                color: #383c4e;
                font-weight: 500;
              }
            }
          }
        }
        .chart {
          flex:1
        }
      }
      // 帮助链接
      .help {
        display: flex;
        .help-left {
          width: 40%;
        }
        .help-right {
          flex: 1;
        }
        .help-list {
          .help-block {
            background: #f5f6f8;
            border-radius: 4px;
            width: 264px;
            height: 54px;
            padding: 17px 10px;
            font-size: 14px;
            color: #697086;
            margin-top: 10px;
            i {
              width: 14px;
              height: 14px;
              display: inline-block;
              background-size: cover;
              vertical-align: middle;
            }
            i.icon-help {
              background-image: url("~@/assets/common/help.png");
            }
             i.icon-support {
              background-image: url("~@/assets/common/support.png");
            }
             i.icon-add {
              background-image: url("~@/assets/common/add.png");
            }
             i.icon-entry {
              background-image: url("~@/assets/common/entry.png");
            }
          }
        }
      }
      // 通知公告
      .information-list {
        margin-top: 20px;
        .information-list-item {
          display: flex;
          align-items: center;
          margin:15px 0;
          img {
            width: 40px;
            height: 40px;
            border: 50%;
          }
         .col {
           color: #8a97f8;
         }
         div :nth-child(2) {
          color: #697086;
          font-size: 14px;
         }
        }
      }
    }
  }
}
</style>
