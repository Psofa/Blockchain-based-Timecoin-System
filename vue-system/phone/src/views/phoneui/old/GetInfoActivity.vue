<template>
    <div>
        <el-form ref="form" :model="form" label-width="0" :rules="rules">
          <el-form-item prop="title">
            <el-input v-model="form.title" placeholder="活动标题"></el-input>
          </el-form-item>
          <el-form-item prop="quota">
            <el-input-number v-model="form.quota" :min="1" placeholder="活动名额"></el-input-number>
          </el-form-item>
          <el-form-item prop="deadline">
            <el-date-picker
              v-model="form.deadline"
              type="datetime"
              placeholder="报名截止时间"
              align="right"
              :picker-options="pickerOptionsofform">
            </el-date-picker>
          </el-form-item>
          <el-form-item prop="date">
            <el-date-picker type="date" v-model="form.date" placeholder="活动日期"></el-date-picker>
          </el-form-item>
          <el-form-item prop="begin">
              <el-time-picker
                  v-model="form.begin"
                  placeholder="活动开始时间"
                  style="vertical-align: middle;margin-right: 20px;width: auto;">
              </el-time-picker>
          </el-form-item>
          <el-form-item prop="description">
            <el-input type="textarea" v-model="form.description" placeholder="活动描述"></el-input>
          </el-form-item>
          <el-form-item style="display: flex; justify-content: space-between; align-items: center;">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="onSubmitForm">确 定</el-button>
          </el-form-item>
        </el-form>
    </div>
</template>

<script>
import { format } from 'date-fns-tz';

export default {
    name: 'AddActivityOld',
    data() {
        return {
            form: {},
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
            rules: {
                title: [{ required: true, message: '请输入活动标题', trigger: 'blur' }],
                quota: [{ required: true, message: '请输入活动名额', trigger: 'blur' }],
                deadline: [{ required: true, message: '请选择报名截止时间', trigger: 'change' }],
                date: [{ required: true, message: '请选择活动日期', trigger: 'change' }],
                begin: [
                    { required: true, message: '请输入活动开始时间', trigger: 'blur' },
                    { validator: (rule, value, callback) => this.validateBeginAfterDeadline(rule, value, callback), trigger: 'blur' }
                ],
                end: [
                    { required: true, message: '请输入活动结束时间', trigger: 'blur' },
                    { validator: (rule, value, callback) => this.validateEndAfterBegin(rule, value, callback), trigger: 'blur' }
                ],
                phone: [
                    { required: true, message: '请输入发布人电话', trigger: 'blur' },
                    { pattern: /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/, message: '请输入正确的电话号码', trigger: 'blur' }
                ],
                description: [{ required: true, message: '请输入活动描述', trigger: 'blur' }],
            }
        };
    },
    methods: {
        validateBeginAfterDeadline(rule, value, callback) {
            // 格式化时间
            const formatTimeString = (dateString) => {
                if (!dateString) return '';
                let hours = dateString.getHours().toString().padStart(2, '0');
                let minutes = dateString.getMinutes().toString().padStart(2, '0');
                let seconds = dateString.getSeconds().toString().padStart(2, '0');
                let formattedTime = `${hours}:${minutes}:${seconds}`;
                return formattedTime;
            };
            // 格式化日期
            const formatDateString = (dateString) => {
                if (!dateString) return '';
                const date = new Date(dateString);
                const year = date.getFullYear();
                const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
                const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
                return `${year}-${month}-${day}`;
            };
            const { form } = this; // 表单模型
            const beginDate = new Date(formatDateString(form.date) + ' ' + formatTimeString(value)); // 构造开始日期时间对象
            if (beginDate <= form.deadline) {
                callback(new Error('活动开始时间必须晚于报名截止时间'));
            } else {
                callback();
            }
        },
        validateEndAfterBegin(rule, value, callback) {
            if (!value) {
                callback(new Error('结束时间不能为空'));
                return;
            }

            const { form } = this;
            // 假设你的开始时间保存在 this.begin 中
            if (!form.begin) {
                callback(new Error('开始时间不能为空'));
                return;
            }

            const begin = new Date(form.begin);
            const end = new Date(value);

            if (end <= begin) {
                callback(new Error('结束时间必须在开始时间之后'));
            } else {
                callback();
            }
        },
        onSubmitForm() {
            this.$refs.form.validate(valid => {
                if (valid) {
                // 格式化时间
                const formatTimeString = (dateString) => {
                    if (!dateString) return '';
                    let hours = dateString.getHours().toString().padStart(2, '0');
                    let minutes = dateString.getMinutes().toString().padStart(2, '0');
                    let seconds = dateString.getSeconds().toString().padStart(2, '0');
                    let formattedTime = `${hours}:${minutes}:${seconds}`;
                    return formattedTime;
                };
                // 格式化日期
                const formatDateString = (dateString) => {
                    if (!dateString) return '';
                    const date = new Date(dateString);
                    const year = date.getFullYear();
                    const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
                    const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
                    return `${year}-${month}-${day}`;
                };
                const data = this.form;
                data['status'] = 5;
                data.begin = formatTimeString(data.begin);
                data.end = '23:49:49';
                data.date = formatDateString(data.date);
                data.deadline = format(data.deadline, "yyyy-MM-dd'T'HH:mm:ss", { timeZone: 'Asia/BeiJing' });
                data.phone = "15245678901";
                // 存储表单数据
                localStorage.setItem('form', JSON.stringify(data));
                this.$emit('next');
                this.$router.push('/locationGet');
                this.resetForm();
                }
            });
        },
        handleCancel() {
            this.$router.push('/serverOld');
        },
        resetForm() {
            this.$refs.form.resetFields();
            this.form = {};
        },
    }
};
</script>

<style lang="scss" scoped>
.mobile-form {
    padding: 20px;

    .header {
        margin: 10px;
    }
    .el-main {
        backdrop-filter: blur(40px); /* 添加毛玻璃效果 */
        border-radius: 20px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
        background-color: rgba(255, 255, 255, 0.4);
        margin-top: 30px;
    }
}
</style>