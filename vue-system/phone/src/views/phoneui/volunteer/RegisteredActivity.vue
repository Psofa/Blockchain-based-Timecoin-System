<template>
    <div class="pageBox">
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
        <div>
            <div v-if="isActivityEnd">
                <el-button round>活动结束</el-button>
            </div>
            <div v-if="!isActivityEnd" style="display: flex;align-items: center;width: 100%;">
                <el-button round v-if="!isSignUp">报名结束</el-button>
                <el-button type="primary" round @click= "quit" v-if="isSignUp">取消报名</el-button>
                <el-button round v-if="isSignIn && this.isEndSign">已签到</el-button>
                <el-button type="primary" round @click= "signInUser" v-else-if="isSignIn && !this.isEndSign">签到</el-button>
                <el-button round v-else>签到未开始</el-button>
            </div>
        </div>
      </div>
      <div class="content">
        <el-form ref="form" :model="form" label-width="100px" style="width: 100%;">
            <el-form-item label="活动标题">
                <el-input v-model="form.title" readonly prefix-icon="el-icon-edit"></el-input>
            </el-form-item>
            <el-form-item label="活动名额">
                <el-input v-model="form.quota" readonly prefix-icon="el-icon-user"></el-input>
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
                        readonly
                    >
                    </el-date-picker>
                </div>
            </el-form-item>
            <el-form-item label="活动日期">
                <el-date-picker type="date" v-model="form.date" style="width: 100%;" readonly></el-date-picker>
            </el-form-item>
            <el-form-item label="活动开始时间">
                <el-input v-model="form.begin" readonly prefix-icon="el-icon-time"></el-input>
            </el-form-item>
            <el-form-item label="活动结束时间">
                <el-input v-model="form.end" readonly prefix-icon="el-icon-time"></el-input>
            </el-form-item>
            <el-form-item label="活动地址">
                <el-input v-model="form.address" readonly prefix-icon="el-icon-location"></el-input>
            </el-form-item>
            <el-form-item label="发布人电话">
                <el-input v-model="form.phone" readonly prefix-icon="el-icon-phone"></el-input>
            </el-form-item>
            <el-form-item label="活动描述">
                <el-input type="textarea" v-model="form.description" readonly></el-input>
            </el-form-item>
            <el-form-item label="活动状态">
                <el-input v-model="statusLabel" readonly prefix-icon="el-icon-info"></el-input>
            </el-form-item>
        </el-form>
      </div>
    </div>
</template>
  
<script>
import request from '@/utils/request';

export default {
    name: 'RegisteredActivity',
    data() {
        return {
            form: {},
            id: null,
            pageSize: 1,
            currentPage: 1,
            deadline:'',
            isEndSign:false,
            // 日期表
            pickerOptionsofsearch: {
                disabledDate(time) {
                return time.getTime() > Date.now();
                },
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
        };
    },
    created() {
        // 从查询参数中获取数据
        this.id = parseInt(this.$route.query.id);
        this.search();
        this.getIsEndSign();
    },
    computed: {
        statusLabel() {
            const selectedOption = this.options.find(option => option.value === this.form.status);
            return selectedOption ? selectedOption.label : '未知状态';
        },
        isSignUp() {
            // 获取当前时间
            const now = new Date();

            // 将截止时间字符串转换为日期对象
            const deadlineDate = new Date(this.form.deadline);

            // 比较当前时间和截止时间
            if (deadlineDate > now) {
                // 截止时间在当前时间之后
                return true;
            } else {
                // 截止时间在当前时间之前
                return false;
            }
        },
        isSignIn() {
            // 获取当前日期和时间
            const now = new Date();

            // 将表单中的活动日期、开始时间和结束时间转换为日期对象
            const startTime = new Date(this.form.date + 'T' + this.form.begin);
            const endTime = new Date(this.form.date + 'T' + this.form.end);

            // 检查 startTime 和 endTime 是否为有效日期
            if (!isNaN(startTime) && !isNaN(endTime)) {
                // 检查当前日期是否是活动日期，并且在开始时间和结束时间之间
                if (now >= startTime && now <= endTime) {
                    return true;
                }
                return false;
            } else {
                // 如果其中任何一个是无效的日期，则返回 false
                return false;
            }
        },
        isActivityEnd() {
            // 获取当前日期和时间
            const now = new Date();

            // 将表单中的活动日期、开始时间和结束时间转换为日期对象
            const activityDate = new Date(this.form.date);
            const endTime = new Date(this.form.date + 'T' + this.form.end);

            // 检查当前日期是否是活动日期，并且在开始时间和结束时间之间
            if (now.toDateString() === activityDate.toDateString() && now > endTime) {
                return true;
            } else {
                return false;
            }
        }
    },
    methods: {
        async search() {
            try {
                // 创建 URLSearchParams 对象
                const params = new URLSearchParams();
                // 添加搜索条件到 URLSearchParams 对象中
                params.append('pageSize', this.pageSize);
                params.append('page', this.currentPage);
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
        getIsEndSign() {
            // 创建 URLSearchParams 对象
            const params = new URLSearchParams();
            params.append('id', this.id);
            // 将 URLSearchParams 对象转换为查询字符串
            const queryString = params.toString();

            // 发起请求时将查询字符串添加到URL中
            request.get(`user/vol/sign?${queryString}`).then(response => {
                if (response.code === 1) {
                    if (response.sign === 1) {
                        // 在这里处理获取到的结果
                        this.isEndSign = true;
                    }
                } else {
                    this.$message.error(response.msg);
                }
            }).catch(error => {
                console.error('获取数据失败:', error);
            });
        },
        calculateDeadline() {
            if (this.form.deadline) {
                this.deadline = new Date(this.form.deadline);
            }
        },
        quit() {
            const data = {
                id: this.id,
            };
            request.put(`users/vol/cancel`,data)
                .then(response => {
                if (response.code === 1) {
                    this.$message.success(response.msg);
                    setTimeout(() => {
                        this.$router.push({ 
                            name: 'ActivityOfUser',
                        });
                    }, 1000);
                } else {
                    this.$message.error(response.msg);
                }
                })
                .catch(error => {
                    console.error('取消失败:', error);
                });
        },
        signInUser() {
            // 在发送路由跳转时将数据作为查询参数传递
            this.$router.push({ 
                name: 'SignInUser', 
                query: { 
                    id: this.id
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
    width: 100%;
    height: auto;
  }
  .content{
    margin: 10px;
    backdrop-filter: blur(10px);
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    height: auto;
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 20px;
  }

}
</style>