<template>
    <div class="pageBox">
      <img :src="$activityImagePath" class="image">
      <div class="content">
        <div>
            {{ form.title }}
        </div>
        <el-divider content-position="center">报名截止</el-divider>
        <div style="width: 100%; display: inline-block; margin-bottom: 10px;" v-if="deadline">
            <el-statistic
              format="DD天HH小时mm分钟ss秒"
              :value="deadline"
              time-indices
            >
            </el-statistic>
        </div>
        <el-button round style="width: 80%;" v-if="!isSignUp">报名结束</el-button>
        <el-button type="primary" round style="width: 80%;" @click= "signUp" v-if="isSignUp">点击报名</el-button>
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
    name: 'TargetPage',
    data() {
        return {
            form: {},
            id: null,
            pageSize: 1,
            currentPage: 1,
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
    },
    computed: {
        deadline() {
            // 确保 form.deadline 有值并且是有效的日期格式
            if (this.form.deadline && !isNaN(Date.parse(this.form.deadline))) {
                // 计算 deadline 并将结果保存到一个计算属性中
                return new Date(this.form.deadline);
            } else {
                return null;
            }
        },
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
        }
    },
    methods: {
        search() {
            // 创建 URLSearchParams 对象
            const params = new URLSearchParams();
            // 添加搜索条件到 URLSearchParams 对象中
            params.append('pageSize', this.pageSize);
            params.append('page', this.currentPage);
            params.append('id', this.id);
            // 将 URLSearchParams 对象转换为查询字符串
            const queryString = params.toString();

            // 发起请求时将查询字符串添加到URL中
            request.get(`users/volold/activity?${queryString}`)
                .then(response => {
                if (response.code === 1) {
                    this.form = response.data;
                } else {
                    this.$message.error(response.msg);
                }
                })
                .catch(error => {
                    console.error('获取数据失败:', error);
                });
        },
        signUp() {
            const data = {
                id: this.id,
            };
            request.put(`/users/vol`,data)
                .then(response => {
                if (response.code === 1) {
                    this.$message.success("报名成功");
                    setTimeout(() => {
                        this.$router.push({ 
                            name: 'HomePhone',
                        });
                    }, 1000);
                } else {
                    this.$message.error(response.msg);
                }
                })
                .catch(error => {
                    console.error('报名失败:', error);
                });
        }
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