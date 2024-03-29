<template>
    <div class="addActivityBox">
        <el-header class="searchBox">
            <el-input type="text" v-model="searchTitle" placeholder="请输入活动名称" prefix-icon="el-icon-search" style="width: auto;margin-right: 10px;"></el-input>
            <el-input type="text" v-model="searchAddress" placeholder="请输入活动地址" prefix-icon="el-icon-search" style="width: auto"></el-input>
            <span class="searchBtn" style="margin-left: 20px;">
                <el-button round @click = "search">搜索</el-button>
            </span>
        </el-header>
        <el-container class="mainBox">
            <el-header class="groupBox">
                <el-date-picker type="date" v-model="searchDate" placeholder="活动日期" style="width: auto;"></el-date-picker>
                <el-time-picker
                    v-model="searchBegin"
                    placeholder="活动开始时间"
                    style="vertical-align: middle;width: auto; margin-left: 10px;">
                </el-time-picker>
                <el-time-picker
                    v-model="searchEnd"
                    placeholder="活动结束时间"
                    style="vertical-align: middle;width: auto; margin-left: 10px;">
                </el-time-picker>
            </el-header>
            <el-main class="activity">
                <div v-for="(row, index) in tableData" :key="index" @click="handleCardClick(row)">
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
                    </div>
                    </div>
                </el-card>
                </div>
                <!-- 分页组件 -->
                <div class="pagination-container">
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
    name: 'AddActivityPhone',
    data() {
        return {
            // 搜索数据
            // 日期表
            pickerOptions: {
                shortcuts: [{
                text: '今天',
                onClick(picker) {
                    picker.$emit('pick', new Date());
                }
                }, {
                text: '昨天',
                onClick(picker) {
                    const date = new Date();
                    date.setTime(date.getTime() - 3600 * 1000 * 24);
                    picker.$emit('pick', date);
                }
                }, {
                text: '一周前',
                onClick(picker) {
                    const date = new Date();
                    date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                    picker.$emit('pick', date);
                }
                }]
            },
            searchDate: '', // 活动日期
            searchEnd: '',
            searchBegin: '',
            searchTitle: '',
            searchAddress: '',
            // 示例数据
            data: {
                total:2,
                rows: [
                {
                    id: 1,
                    title: "志愿者活动1",
                    quota: "20",
                    deadline: "2024-03-20",
                    date: "2024-03-21",
                    begin: "09:00:00",
                    end: "12:00:00",
                    address: "北京市朝阳区",
                    oldId: 1,
                    phone: "1234567890",
                    description: "这是志愿者活动1的描述",
                    status: 1,
                    administratorId: null,
                    createTime: "2024-03-15T08:00:00",
                    updateTime: "2024-03-15T10:00:00",
                    message: null,
                    remain: 10
                },
                {
                    id: 2,
                    title: "志愿者活动2",
                    quota: "15",
                    deadline: "2024-03-25",
                    date: "2024-03-26",
                    begin: "14:00:00",
                    end: "17:00:00",
                    address: "上海市浦东新区",
                    oldId: 2,
                    phone: "9876543210",
                    description: "这是志愿者活动2的描述",
                    status: 2,
                    administratorId: 1,
                    createTime: "2024-03-16T09:00:00",
                    updateTime: "2024-03-17T11:00:00",
                    message: "活动已审核通过",
                    remain: 5
                },
                // 添加更多的数据...
                ],
            },
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
            // 格式化日期
            const formatDateString = (dateString) => {
                if (!dateString) return '';
                const date = new Date(dateString);
                const year = date.getFullYear();
                const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
                const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
                return `${year}-${month}-${day}`;
            };
            // 格式化时间
            const formatTimeString = (dateString) => {
                if (!dateString) return '';
                let hours = dateString.getHours().toString().padStart(2, '0');
                let minutes = dateString.getMinutes().toString().padStart(2, '0');
                let seconds = dateString.getSeconds().toString().padStart(2, '0');
                let formattedTime = `${hours}:${minutes}:${seconds}`;
                return formattedTime;
            };
            // 创建 URLSearchParams 对象
            const params = new URLSearchParams();
            // 添加搜索条件到 URLSearchParams 对象中
            params.append('pageSize', this.pageSize);
            params.append('page', this.currentPage);
            params.append('title',this.searchTitle);
            params.append('address',this.searchAddress);
            params.append('date', formatDateString(this.searchDate));
            params.append('begin', formatTimeString(this.searchBegin));
            params.append('end', formatTimeString(this.searchEnd));
            // 将 URLSearchParams 对象转换为查询字符串
            const queryString = params.toString();
            // 发起请求时将查询字符串添加到URL中
            request.get(`/users/vol?${queryString}`)
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
            this.$store.commit('setCardData', row.id);
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
          justify-content: space-between;
          align-items: center;
          padding: 0 5px;
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
            height: 100px;
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