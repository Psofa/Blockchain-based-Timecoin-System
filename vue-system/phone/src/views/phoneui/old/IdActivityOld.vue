<template>
    <div class="pageBox">
        <el-dialog :visible.sync="dialogVisible" title="填写服务评分" :append-to-body="true"
            :modal-append-to-body="false">
            <div style="display: flex;flex-direction: column;
                justify-content: center;align-items: center;">
                <h3>请为该用户的服务进行评分</h3>
                <el-rate
                    v-model="value1"
                    :colors="colors">
                </el-rate>
                <el-button type="primary" @click="dialogVisible=false" style="margin-top: 20px;">确 定</el-button>
            </div>
        </el-dialog>
      <img :src="$activityImagePath" class="image">
      <div class="content">
        <div>
            {{ form.title }}
        </div>
        <el-divider content-position="center">报名截止</el-divider>
        <div v-if="deadline" style="width: 100%; display: inline-block; margin-bottom: 10px;">
            <el-statistic
              format="DD天HH小时mm分钟ss秒"
              :value="deadline"
              time-indices
            >
            </el-statistic>
        </div>
      </div>
      <div class="content">
        <el-form ref="form" :model="form" label-width="100px" style="width: 100%;">
            <el-form-item label="活动标题">
                <el-input v-model="form.title"  prefix-icon="el-icon-edit"></el-input>
            </el-form-item>
            <el-form-item label="活动名额">
                <el-input v-model="form.quota"  prefix-icon="el-icon-user"></el-input>
            </el-form-item>
            <el-form-item label="剩余名额">
                <el-input v-model="form.remain" readonly prefix-icon="el-icon-sell"></el-input>
            </el-form-item>
            <el-form-item label="报名截止时间">
                <div class="block">
                    <el-date-picker
                        v-model="form.deadline"
                        type="datetime"
                        placeholder="选择日期时间"
                        align="right"
                        :picker-options="pickerOptionsofform"
                    >
                    </el-date-picker>
                </div>
            </el-form-item>
            <el-form-item label="活动日期">
                <el-date-picker type="date" v-model="form.date" style="width: 100%;" ></el-date-picker>
            </el-form-item>
            <el-form-item label="活动开始时间">
                <el-input v-model="form.begin"  prefix-icon="el-icon-time"></el-input>
            </el-form-item>
            <el-form-item label="活动结束时间">
                <el-input v-model="form.end"  prefix-icon="el-icon-time"></el-input>
            </el-form-item>
            <el-form-item label="活动地址">
                <el-input v-model="form.address"  prefix-icon="el-icon-location"></el-input>
            </el-form-item>
            <el-form-item label="发布人电话">
                <el-input v-model="form.phone"  prefix-icon="el-icon-phone"></el-input>
            </el-form-item>
            <el-form-item label="活动描述">
                <el-input type="textarea" v-model="form.description" ></el-input>
            </el-form-item>
            <el-form-item label="活动状态">
                <el-input v-model="statusLabel" readonly prefix-icon="el-icon-info"></el-input>
            </el-form-item>
        </el-form>
      </div>
      <div>
        <el-button @click="handleCancel" style="margin-right: 20px;">取 消</el-button>
        <el-button type="primary" @click="onSubmitForm">提交修改</el-button>
      </div>
      <el-divider content-position="center">活动报名情况</el-divider>
      <div class="activity">
        <ul class="infinite-list" v-infinite-scroll="load" infinite-scroll-disabled="busy" infinite-scroll-distance="5" style="overflow:auto;padding-inline-start:0px">
            <div v-for="(row, index) in tableData" :key="index">
                <el-card :body-style="{ padding: '0px' }" shadow="always">
                    <div class="cardContent">
                        <img :src="require('@/assets/common/header1.jpg')" class="image">
                        <div class="contentBox">
                            <div style="font-size: 17px;">{{ row.name }}</div>
                            <div style="font-size: 12px;">{{ row.phone }}</div>
                            <div style="font-size: 12px;">{{ row.email }}</div>
                            <!-- 删除按钮 -->
                            <div style="display: flex;justify-content: center;align-items: center;">
                                <el-button v-if="!isEndSign" round size="mini" style="margin-top: 5px;width: 45%;">服务未开始</el-button>
                                <el-button v-else-if="isEndSign && row.status===0" type="primary" round size="mini" @click="editUser(row.id)" style="margin-top: 5px;width: 45%;">服务完成</el-button>
                                <el-button v-else round size="mini" style="margin-top: 5px;width: 45%;" @click="editUser1(row.id)">服务已完成</el-button>
                                <el-button round size="mini" type="primary" style="margin-top: 5px;width: 45%;">联系志愿者</el-button>
                            </div>
                        </div>
                    </div>
                </el-card>
            </div>
        </ul>
      </div>
    </div>
</template>
  
<script>
import request from '@/utils/request';

export default {
    name: 'IdActivityOld',
    data() {
        return {
            // 弹窗
            dialogVisible: false,
            value1: null,
            colors: ['#99A9BF', '#F7BA2A', '#FF9900'], 
            form: {},
            id: null,
            pageSize1: 1,
            currentPage1: 1,
            deadline:'',
            isEndSign:false,  
            // 选择器
            options: [
                { value: 1, label: '未审核活动' },
                { value: 2, label: '审核同意活动' },
                { value: 3, label: '审核不同意活动' },
                { value: 4, label: '过期活动' },
            ],
            pickerOptionsofform: {
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
            // 卡片
            originalData: [],
            pageSize: 5, // 每页显示的条目数量
            totalItems: 0, // 总条目数量
            currentPage: 1, // 当前页码
            tableData: [], // 表格数据
            // 无限滚动
            busy: false,
        };
    },
    created() {
        // 从查询参数中获取数据
        this.id = parseInt(this.$route.query.id);
        this.search();
        this.search1();
        this.getIsEndSign();
    },
    computed: {
        statusLabel() {
            const selectedOption = this.options.find(option => option.value === this.form.status);
            return selectedOption ? selectedOption.label : '未知状态';
        },
        
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
        calculateDeadline() {
            if (this.form.deadline) {
                this.deadline = new Date(this.form.deadline);
            }
        },
        getIsEndSign() {
            const now = new Date();
            if (this.deadline < now) {
                this.isEndSign = true;
            }
        },
        async search() {
            try {
                // 创建 URLSearchParams 对象
                const params = new URLSearchParams();
                // 添加搜索条件到 URLSearchParams 对象中
                params.append('pageSize', this.pageSize1);
                params.append('page', this.currentPage1);
                params.append('id', this.id);
                // 将 URLSearchParams 对象转换为查询字符串
                const queryString = params.toString();

                // 发起请求时将查询字符串添加到URL中
                const response = await request.get(`users/volold/activity?${queryString}`);
                if (response.code === 1) {
                    this.form = response.data;
                    this.calculateDeadline();
                } else {
                    this.$message.error(response.msg);
                }
            } catch (error) {
                console.error('获取数据失败:', error);
            }
        },
        search1() {
            return new Promise((resolve, reject) => {
                // 创建 URLSearchParams 对象
                const params = new URLSearchParams();
                // 添加搜索条件到 URLSearchParams 对象中
                params.append('pageSize', this.pageSize);
                params.append('page', this.currentPage);
                params.append('id',this.id);
                // 将 URLSearchParams 对象转换为查询字符串
                const queryString = params.toString();
                // 发起请求时将查询字符串添加到URL中
                request.get(`/users/old/status?${queryString}`)
                    .then(response => {
                        if (response.code === 1) {
                            this.totalItems = response.data.total;
                            this.originalData = response.data.rows;
                            this.tableData = [];
                            // 合并原始数据到 tableData 数组中
                            this.tableData = [...this.tableData, ...this.originalData];
                            this.calculateDeadline();
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
        editUser(id) {
            let data = {
                userId: id,
                activityId:this.id,
                status: 1,
            };
            request.put(`/users/old/status`,data)
                .then(response => {
                    if (response.code === 1) {
                        this.$message.success("该用户已完成服务");
                        this.search1();
                        this.dialogVisible = true;
                    } else {
                        this.$message.error(response.msg);
                    }
                })
                .catch(error => {
                    console.error('There was a problem with the request:', error);
                });
        },
        editUser1(id) {
            let data = {
                userId: id,
                activityId:this.id,
                status: 0,
            };
            request.put(`/users/old/status`,data)
                .then(response => {
                    if (response.code === 1) {
                        this.$message.success("取消服务完成成功");
                        this.search1();
                    } else {
                        this.$message.error(response.msg);
                    }
                })
                .catch(error => {
                    console.error('There was a problem with the request:', error);
                });
        },
        handleCancel() {
            this.$router.push('/activityOld');
        },
        onSubmitForm() {
            this.$refs.form.validate(valid => {
                if (valid) {
                const data = this.form;
                
                request.put(`/users/old`,data)
                    .then(response => {
                        if (response.code === 1) {
                            this.$message.success(response.msg);
                        } else {
                            this.$message.error(response.msg);
                        }
                    })
                    .catch(error => {
                        console.error('修改失败:', error);
                    });
                }
            });
        },
    }
}
</script>

<style lang="scss" scoped>
.pageBox {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  padding: 10px;
  .image {
    width: 95%;
    height: auto;
  }
  .content{
    margin: 10px;
    backdrop-filter: blur(10px);
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    height: auto;
    width: 90%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 20px;
  }
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
                    margin-left: 20px;
                    padding: 8px;
                    width: 60%;
                }
            }
        } 
    } 
}
</style>