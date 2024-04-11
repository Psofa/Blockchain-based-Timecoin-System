<template>
    <div class="addActivityBox">
        <el-header class="header">
            <el-button type="text" @click="search1" style="margin-left: 50px;">未审核</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button type="text" @click="search2">审核通过</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button type="text" @click="search3">审核不通过</el-button>
            <el-divider direction="vertical"></el-divider>
            <el-button type="text" @click="search4" style="margin-right: 50px;">过期活动</el-button>
        </el-header>
        <el-container class="mainBox">
            <el-header style="display: flex;justify-content: space-between;align-items: center;">
                <el-input placeholder="请输入内容" v-model="searchTitle" style="margin-right: 20px;"></el-input>
                <el-button round type="primary">搜索</el-button>
                <el-button round type="primary" @click="addActivity">增添活动</el-button>
            </el-header>
            <el-main class="activity">
                <ul class="infinite-list" v-infinite-scroll="load" infinite-scroll-disabled="busy" infinite-scroll-distance="5" style="overflow:auto;padding-inline-start:0px">
                    <div v-for="(row, index) in tableData" :key="index" @click="handleCardClick(row)">
                        <el-card :body-style="{ padding: '0px' }" shadow="always">
                            <div class="cardContent">
                                <img :src="$activityImagePath" class="image">
                                <div class="contentBox">
                                    <div style="font-size: 17px;">{{ row.title }}</div>
                                    <div style="font-size: 14px;">剩余名额：{{ row.remain }}</div>
                                    <el-progress :percentage="Number(((parseFloat(row.quota) - parseFloat(row.remain)) / parseFloat(row.quota) * 100).toFixed(1))"></el-progress>
                                    <div style="display: flex;justify-content: space-between;align-items: center;font-size: 12px;">
                                        {{ row.date }}
                                    <el-tag size="mini" v-if="!isBeforeDeadline(row.deadline)" type="danger">报名结束</el-tag>
                                    <el-tag size="mini" v-else type="success">报名中</el-tag>
                                    </div>
                                    <div style="font-size: 12px;">{{ row.address }}</div>
                                    <!-- 删除按钮 -->
                                    <div style="display: flex;justify-content: center;align-items: center;">
                                        <el-button type="danger" round size="mini" @click="deleteActivity(row.id)" style="margin-top: 5px;width: 80%;">删除</el-button>
                                    </div>
                                </div>
                            </div>
                        </el-card>
                    </div>
                </ul>
            </el-main>
        </el-container>
        <el-footer>
            <span>
                <router-link to="/serverOld" class="RouterLink">
                    <i class="el-icon-menu"></i>服务中心
                </router-link>
            </span>
            <span>
                <router-link to="/artificialOld" class="RouterLink">
                    <i class="el-icon-s-comment"></i>人工服务
                </router-link>
            </span>
            <span>
                <router-link to="/homeOld" class="RouterLink">
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
            searchTitle: '', // 搜索文本
            status: 1, // 状态
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
            return new Promise((resolve, reject) => {
                // 创建 URLSearchParams 对象
                const params = new URLSearchParams();
                // 添加搜索条件到 URLSearchParams 对象中
                params.append('pageSize', this.pageSize);
                params.append('page', this.currentPage);
                params.append('status',this.status);
                params.append('title',this.searchTitle);
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
                name: 'IdActivityOld', 
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
            this.status = 1;
            this.search();
        },
        search2() {
            this.status = 2;
            this.search();
        },
        search3() {
            this.status = 3;
            this.search();
        },
        search4() {
            this.status = 4;
            this.search();
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
    .el-footer{
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