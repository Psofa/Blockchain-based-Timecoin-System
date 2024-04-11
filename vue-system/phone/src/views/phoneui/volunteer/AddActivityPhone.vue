<template>
    <div class="addActivityBox">
        <el-header class="searchBox">
            <el-input type="text" v-model="searchTitle" placeholder="请输入活动名称" prefix-icon="el-icon-search" style="margin-right: 10px;"></el-input>
            <el-button round @click = "search" style="width: auto;">搜索</el-button>
        </el-header>
        <el-container class="mainBox">
            <el-header class="groupBox">
                <el-button type="text" @click="search1">报名中</el-button>
                <el-divider direction="vertical"></el-divider>
                <el-button type="text" @click="search2">进行中</el-button>
                <el-divider direction="vertical"></el-divider>
                <el-button type="text" @click="search3">已结束</el-button>
            </el-header>
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
    name: 'AddActivityPhone',
    data() {
        return {
            // 搜索数据
            searchDate: '', // 活动日期
            searchDeadline: '', // 报名截止日期
            searchEnd: '',
            searchBegin: '',
            searchTitle: '',
            status: 2,
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
        this.search1();
    },
    methods: {
        load() {
            if (this.tableData.length >= this.totalItems) {
                this.$message.warning('没有更多数据了');
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
                params.append('title',this.searchTitle);
                params.append('deadline',this.searchDeadline);
                params.append('date', this.searchDate);
                params.append('begin', this.searchBegin);
                params.append('end', this.searchEnd);
                params.append('status', this.status);
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
        search1() {
            // 格式化截止时间
            let currentDate = new Date();
            let month = (currentDate.getMonth() + 1).toString().padStart(2, '0'); // 将月份格式化为两位数
            let day = currentDate.getDate().toString().padStart(2, '0'); // 将日期格式化为两位数
            let hours = currentDate.getHours().toString().padStart(2, '0'); // 将小时格式化为两位数
            let minutes = currentDate.getMinutes().toString().padStart(2, '0'); // 将分钟格式化为两位数
            let seconds = currentDate.getSeconds().toString().padStart(2, '0'); // 将秒钟格式化为两位数

            this.searchDeadline = `${currentDate.getFullYear()}-${month}-${day} ${hours}:${minutes}:${seconds}`;
            this.status = 2;
            this.tableData = [];
            this.search();
            this.searchDeadline = '';
        },
        search2() {
            // 创建一个新的 Date 对象，它将自动获取当前日期和时间
            const now = new Date();
            // 获取当前年份
            const year = now.getFullYear();
            // 获取当前月份（注意：月份是从 0 开始计数的，所以要加 1）
            const month = (now.getMonth() + 1).toString().padStart(2, '0');
            // 获取当前日期
            const date = now.getDate().toString().padStart(2, '0');
            // 获取当前小时数（0-23）
            const hours = now.getHours().toString().padStart(2, '0');
            // 获取当前分钟数（0-59）
            const minutes = now.getMinutes().toString().padStart(2, '0');
            // 获取当前秒数（0-59）
            const seconds = now.getSeconds().toString().padStart(2, '0');

            // 根据需要格式化时间
            this.searchDate = `${year}-${month}-${date}`;
            this.searchBegin = `${hours}:${minutes}:${seconds}`;
            this.searchEnd = `${hours}:${minutes}:${seconds}`;
            this.status = 2;
            this.tableData = [];
            this.search();
            this.searchDate = '';
            this.searchBegin = '';
            this.searchEnd = '';
            
        },
        search3() {
            this.status = 4;
            this.tableData = [];
            this.search();
            
        }
    }
}
</script>

<style lang="scss" scoped>
.addActivityBox {
    .searchBox{
        margin-top: 5px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .mainBox{
        .groupBox{
          display: flex;
          align-items: center;
          margin-left: 10px;
        }
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