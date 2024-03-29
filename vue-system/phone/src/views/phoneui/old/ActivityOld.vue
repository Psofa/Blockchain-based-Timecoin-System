<template>
    <div class="addActivityBox">
        <el-header class="header">
            <el-button type="text" style="margin-left: 50px;">未审核</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button type="text">已审核</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button type="text" style="margin-right: 50px;">已结束</el-button>
        </el-header>
        <el-container class="mainBox">
            <el-header style="display: flex;justify-content: space-between;align-items: center;">
                <el-input placeholder="请输入内容" style="margin-right: 20px;"></el-input>
                <el-button round type="primary">搜索</el-button>
                <el-button round type="primary" @click="addActivity">增添活动</el-button>
            </el-header>
            <el-main class="activity">
                <div v-for="(row, index) in tableData" :key="index" @click="handleCardClick(row)" style="width: 95%;">
                    <el-card :body-style="{ padding: '0px' }" shadow="always">
                        <div class="cardContent">
                            <img :src="$activityImagePath" class="image">
                            <div class="contentBox">
                                <div>活动：{{ row.title }}</div>
                                <div>剩余名额：{{ row.quota }}</div>
                                <div style="display: flex;justify-content: space-between;align-items: center;">
                                活动日期：{{ row.date }}
                                <el-tag size="mini" v-if="!isBeforeDeadline(row.deadline)" type="danger">报名结束</el-tag>
                                <el-tag size="mini" v-else type="success">报名中</el-tag>
                                </div>
                                <div>地址：{{ row.address }}</div>
                                <!-- 删除按钮 -->
                                <div style="display: flex;justify-content: center;align-items: center;">
                                    <el-button type="danger" round size="mini" @click="deleteActivity(row.id)" style="margin-top: 5px;width: 80%;">删除</el-button>
                                </div>
                            </div>
                        </div>
                    </el-card>
                </div>
                <!-- 分页组件 -->
                <div class="pagination-container" style="margin-bottom: 5px;">
                    <el-pagination
                        @current-change="handleCurrentChange"
                        :current-page="currentPage"
                        :page-sizes="[5, 10, 20, 30]" 
                        :page-size="pageSize"
                        layout="total, prev, pager, next"
                        :total="totalItems"
                        style="margin-bottom: 50px;right: 0;">
                    </el-pagination>
                </div>
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
    name: 'ActivityOld',
    data() {
        return {
            // 卡片
            originalData: [],
            pageSize: 5, // 每页显示的条目数量
            totalItems: 0, // 总条目数量
            currentPage: 1, // 当前页码
            tableData: [], // 表格数据
        }
    },
    mounted() {
        // 初始化时计算当前页的数据
        this.search();
    },
    methods: {
        handleCurrentChange(newPage) {
            // 更新当前页码
            this.currentPage = newPage;
            // 重新搜索获取对应页的数据
            this.search();
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
            request.get(`/users/old?${queryString}`)
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
        deleteActivity(id) {
            let ids = [];
            ids.push(id);
            request.delete(`/administrator/users/${ids.join(',')}`)
                .then(response => {
                if(response.code === 1){
                    this.$message.success(response.msg);
                    this.search();
                }
                else{
                    this.$message.error(response.msg);
                }
                })
                .catch(error => {
                console.error('There was a problem with the request:', error);
                });
        },
        addActivity() {
            this.$router.push({ 
                name: 'GetInfoActivity',
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
    .mainBox{
        .activity{
          display: flex;
          flex-direction: column;
          justify-content: center; /* 水平居中 */
          align-items: center; /* 垂直居中 */
          padding: 0px;
          .el-card{
            display: flex;
            padding: 5px;
            height: 120px;
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