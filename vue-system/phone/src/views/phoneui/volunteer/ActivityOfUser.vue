<template>
    <div class="addActivityBox">
        <el-header class="header">
            <el-button type="text" @click="searchBeginActivity" style="margin-left: 100px;">已报名</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button type="text" @click="searchEndActivity" style="margin-right: 100px;">已结束</el-button>
        </el-header>
        <el-container class="mainBox">
            <el-main class="activity">
                <ul class="infinite-list" v-infinite-scroll="load" infinite-scroll-disabled="busy" infinite-scroll-distance="5" style="overflow:auto;padding-inline-start:0px">
                    <div v-if="tableData && tableData.length > 0">
                        <div v-for="(row, index) in tableData" :key="index" @click="handleCardClick(row)">
                        <el-card :body-style="{ padding: '0px' }" shadow="always">
                            <div class="cardContent">
                            <img :src="$activityImagePath" class="image">
                            <div class="contentBox">
                                <div>活动：{{ row.title }}</div>
                                <div>剩余名额：{{ row.quota }}</div>
                                <el-progress :percentage="Number(((parseFloat(row.quota) - parseFloat(row.remain)) / parseFloat(row.quota) * 100).toFixed(1))"></el-progress>
                                <div style="display: flex;justify-content: space-between;align-items: center;">
                                活动日期：{{ row.date }}
                                <el-tag size="mini" v-if="!isBeforeDeadline(row.deadline)" type="danger">报名结束</el-tag>
                                <el-tag size="mini" v-else type="success">报名中</el-tag>
                                </div>
                                <div style="font-size: 12px;">地址：{{ row.address }}</div>
                            </div>
                            </div>
                        </el-card>
                        </div>
                    </div>
                    <div v-else>
                        <el-empty description="暂无数据"></el-empty>
                    </div>
                </ul>
            </el-main>
        </el-container>

        <el-footer class="operations">
            <span>
            <router-link to="/homePhone" class="RouterLink">
                <i class="el-icon-house"></i>用户管理
            </router-link>
            </span>
            <span>
            <router-link to="/addActivityPhone" class="RouterLink">
                <i class="el-icon-circle-plus"></i>报名活动
            </router-link>
            </span>
            <span>
            <router-link to="/infoOfUserPhone" class="RouterLink">
                <i class="el-icon-user-solid"></i>个人中心
            </router-link>
            </span>
        </el-footer>
    </div>
</template>

<script>
import request from '@/utils/request';

export default {
    name: 'ActivityOfUser',
    data() {
        return {
            // 搜索数据
            searchStatus: 2,
            // 卡片
            originalData: [],
            pageSize: 5, // 每页显示的条目数量
            totalItems: 0, // 总条目数量
            currentPage: 1, // 当前页码
            tableData: [], // 表格数据
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
                this.$notify({
                title: '警告',
                message: '没有更多数据了',
                type: 'warning',
                position: 'bottom-right',
                });
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
            // 创建 URLSearchParams 对象
            const params = new URLSearchParams();
            // 添加搜索条件到 URLSearchParams 对象中
            params.append('pageSize', this.pageSize);
            params.append('page', this.currentPage);
            params.append('status',this.searchStatus);
            // 将 URLSearchParams 对象转换为查询字符串
            const queryString = params.toString();
            // 发起请求时将查询字符串添加到URL中
            request.get(`users/vol/activity?${queryString}`)
                .then(response => {
                if (response.code === 1) {
                    this.totalItems = response.data.total;
                    this.originalData = response.data.rows;
                    this.tableData = [];
                    // 合并原始数据到 tableData 数组中
                    this.tableData = [...this.tableData, ...this.originalData];
                    
                } else {
                    this.$message.error(response.msg);
                }
                })
                .catch(error => {
                    console.error('获取数据失败:', error);
                });
        },
        handleCardClick(row) {
            // 在发送路由跳转时将数据作为查询参数传递
            this.$router.push({ 
                name: 'RegisteredActivity', 
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
        searchBeginActivity() {
            this.searchStatus = 2;
            this.search();
        },
        searchEndActivity() {
            this.searchStatus = 4;
            this.search();
        }
    }
}
</script>

<style lang="scss" scoped>
.addActivityBox {
    .header{
        display: flex;
        justify-content: space-between;
        align-items: center;
        border: 1px solid #DCDFE6;
        padding: 0px;
        margin: 5px;
    }
    .searchBox{
        margin-top: 5px;
        margin-bottom: 8px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .mainBox{
        .activity{
          display: flex;
          flex-direction: column;
          justify-content: center; /* 水平居中 */
          align-items: center; /* 垂直居中 */
          padding: 0px;
          .el-card{
            display: flex;
            padding: 8px;
            margin-left: 10px;
            margin-right: 10px;
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
    .operations{
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-top: 10px;
      backdrop-filter: blur(10px);
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
      flex-shrink: 0; /* 防止底部内容被压缩 */
      position: fixed; /* 将底部组件固定在页面底部 */
      bottom: 0;
      width: 100%; /* 设置宽度为 100% */
      .RouterLink {
        text-decoration: none;
      }

    }
}
</style>