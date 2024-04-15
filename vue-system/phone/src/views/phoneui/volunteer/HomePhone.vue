<template>
    <el-container class="homeBox">
      <el-container class="activityBox">
          <el-select v-model="value" @change="handleSelectClick">
              <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
              </el-option>
          </el-select>
          <el-input type="text" v-model="searchTitle" prefix-icon="el-icon-search"></el-input>
          <span class="searchBtn" style="margin-left: 20px;">
              <el-button round>搜索</el-button>
          </span>
      </el-container>
      <el-main class="mainBox">
        <div class="blockOfImage">
          <el-carousel height="150px">
            <el-carousel-item v-for="(item, index) in images" :key="index">
              <a :href="item.link" target="_blank"> <!-- 在新标签页打开链接 -->
                <img :src="item.url" alt="image" style="width: 100%; height: 100%; object-fit: cover;">
              </a>
            </el-carousel-item>
          </el-carousel>
        </div>
        <div class="usersData">
          <el-row :gutter="20" style="display: flex;
          justify-content: space-between;
          align-items: center;">
            <el-col :span="6">
              <div>
                <el-statistic title="志愿者人数">
                  <template slot="formatter">
                    6
                  </template>
                </el-statistic>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-statistic title="活动数">
                  <template slot="formatter">
                    10
                  </template>
                </el-statistic>
              </div>
            </el-col>
            <el-col :span="6">
              <div>
                <el-statistic title="老人数">
                  <template slot="formatter">
                    4
                  </template>
                </el-statistic>
              </div>
            </el-col>
          </el-row>
        </div>
        <div class="news">
        </div>
        <div class="activities">
          <el-main class="activity">
            <ul class="infinite-list" v-infinite-scroll="load" infinite-scroll-disabled="busy" infinite-scroll-distance="5" style="overflow:auto;padding-inline-start:0px">
              <div v-for="(row, index) in tableData" :key="index" @click="handleCardClick(row)">
                <el-card :body-style="{ padding: '0px' }" shadow="always">
                  <div class="cardContent">
                    <img :src="$activityImagePath" class="image">
                    <div class="contentBox">
                      <div style="font-size: 17px;">{{ row.title }}</div>
                      <div style="font-size: 14px;">剩余名额：{{ row.quota }}</div>
                      <el-progress :percentage="Number(((parseFloat(row.quota) - parseFloat(row.remain)) / parseFloat(row.quota) * 100).toFixed(1))"></el-progress>
                      <div style="display: flex;justify-content: space-between;align-items: center;font-size: 12px;">
                        {{ row.date }}
                      <el-tag size="mini" v-if="!isBeforeDeadline(row.deadline)" type="danger">报名结束</el-tag>
                      <el-tag size="mini" v-else type="success">报名中</el-tag>
                      </div>
                      <div style="font-size: 12px;">{{ row.address }}</div>
                    </div>
                  </div>
                </el-card>
              </div>
            </ul>
          </el-main>
        </div>
      </el-main>
    </el-container>
</template>

<script>
import request from '@/utils/request';

export default {
  name: 'HomePhone',
  data() {
    return {
      // 选择器
      options: [{
        value: 1,
        label: '成都'
      }, {
        value: 2,
        label: '四川'
      }, {
        value: 3,
        label: '重庆'
      }, {
        value: 4,
        label: '山西'
      }],
      value: 1,
      // 照片
      images: [
        { url: require('@/assets/common/Carousel1.png'), link: 'https://chinavolunteer.mca.gov.cn/nvsiwebsite/XLFZYFW' },
        { url: require('@/assets/common/Carousel2.png'), link: 'https://mp.weixin.qq.com/s/t7p-uNdgRIKcGD1s5veENA' },
      ],
      // 卡片
      originalData: [],
      pageSize: 5, // 每页显示的条目数量
      totalItems: 0, // 总条目数量
      currentPage: 1, // 当前页码
      tableData: [], // 表格数据
      // 搜索
      searchAddress: '',
      searchTitle: '',
      // 无限滚动
      busy: false,
    }
  },
  mounted() {
    // 初始化时计算当前页的数据
    this.search();
  },
  methods: {
    load() {
      if (this.tableData.length >= this.totalItems) {
        
        return;
      }
      if (this.busy) return;
      this.busy = true;

      // 调用你的search方法来获取新的数据
      this.search().then(() => {
        this.currentPage++;
        this.busy = false;
      });
    },
    search() {
      return new Promise((resolve, reject) => {
        // 创建 URLSearchParams 对象
        const params = new URLSearchParams();
        // 添加搜索条件到 URLSearchParams 对象中
        params.append('pageSize', this.pageSize);
        params.append('page', this.currentPage);
        params.append('address', this.searchAddress);
        params.append('title', this.searchTitle);
        // 将 URLSearchParams 对象转换为查询字符串
        const queryString = params.toString();

        // 发起请求时将查询字符串添加到URL中
        request.get(`/users/vol?${queryString}`)
          .then(response => {
            if (response.code === 1) {
              this.totalItems = response.data.total;
              this.originalData = response.data.rows;
              // 合并原始数据到 tableData 数组中
              this.tableData = [...this.tableData, ...this.originalData];
              // 将新的数据作为Promise的结果返回
              resolve(this.tableData);
            } else {
              this.$message.error(response.msg);
              reject(response.msg);
            }
          })
          .catch(error => {
              console.error('获取数据失败:', error);
          });
      });
    },
    handleCardClick(row) {
      // 在发送路由跳转时将数据作为查询参数传递
      this.$router.push({ 
          name: 'TargetPage', 
          query: { 
              id: row.id
          } 
      });
    },
    // 判断是否在报名截止日期之前
    isBeforeDeadline(deadline) {
      // 将截止日期字符串转换为日期对象
      const deadlineDate = new Date(deadline);
      // 获取当前时间
      const currentDate = new Date();
      // 如果当前时间早于截止日期，则返回 true，否则返回 false
      return currentDate < deadlineDate;
    },
    // 地址搜索
    handleSelectClick() {
      this.searchAddress = this.options.find(option => option.value === this.value);
      this.tableData = [];
      this.search();
    },
  }
}
</script>

<style lang="scss" scoped>
.homeBox{
  display: flex;
  flex-direction: column;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  .Title{
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 5px;
    height: 40px !important;
    backdrop-filter: blur(10px);
    border-radius: 20px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  }
  .activityBox{
      display: flex;
      align-items: center;
      margin: 10px;
      .el-select{
          width: 100px;
          margin-right: 20px;
      }
      .el-input{
          width: auto;
      }
  }
  .mainBox{
    width: 100%;
    // 走马灯
    .blockOfImage{
      .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 150px;
        margin: 0;
      }

      .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
      }
      
      .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
      }
    }
    .usersData{
      margin-top: 10px;
      backdrop-filter: blur(10px);
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
      padding: 5px;
    }
    .news{
      margin-top: 10px;
      backdrop-filter: blur(10px);
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
      height: 80px;
      background: url("@/assets/common/homePhone1.png") center center/cover;
    }
    .activities{
      .activity{
        display: flex;
        flex-direction: column;
        justify-content: center; /* 水平居中 */
        align-items: center; /* 垂直居中 */
        padding: 0px;
        .el-card{
          display: flex;
          padding: 5px;
          height: 140px;
          align-items: center;
          margin-bottom: 15px;
          .cardContent{
            display: flex;
            justify-content: space-between;
            align-items: center;
            .image {
              width: 40%;
              display: block;
              border-radius: 10px;
              box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            }
            .contentBox {
              padding: 8px;
              width: 60%;
            }
          }
        } 
      }
    }
  }
}
</style>