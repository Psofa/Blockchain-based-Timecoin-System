<template>
  <div class="AddBox">
    <div class="BOX">
      <h2>添加活动</h2>
      <el-form ref="form" :model="form" label-width="0" :rules="rules">
        <el-form-item prop="title">
          <el-input v-model="form.title" placeholder="活动标题"></el-input>
        </el-form-item>
        <el-form-item prop="quota">
          <el-input-number v-model="form.quota" :min="1" placeholder="活动名额"></el-input-number>
        </el-form-item>
        <el-form-item prop="oldId">
          <el-input v-model="form.oldId" placeholder="老人ID"></el-input>
        </el-form-item>
        <el-form-item prop="deadline">
          <el-date-picker
            v-model="form.deadline"
            type="datetime"
            placeholder="活动截止时间"
            align="right"
            :picker-options="pickerOptionsofform">
          </el-date-picker>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input v-model="form.phone" placeholder="发布人电话"></el-input>
        </el-form-item>
        <el-form-item prop="date">
          <el-date-picker type="date" v-model="form.date" placeholder="活动日期"></el-date-picker>
        </el-form-item>
        <el-form-item prop="address">
          <el-input v-model="form.address" placeholder="活动地址"></el-input>
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
        <el-form-item prop="status">
          <el-select v-model="form.status" clearable placeholder="活动状态" style="margin-right: 20px;">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="message">
          <el-input type="textarea" v-model="form.message" placeholder="管理员建议"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleCancel" style="width: 48%;">取 消</el-button>
          <el-button type="primary" @click="onSubmitForm" style="width: 48%;margin-left:4%;">提 交</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request';
import { format } from 'date-fns-tz';

export default {
  name: 'AdminView',
  data() {
    return {
      form: {},
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
      rules: {
        title: [{ required: true, message: '请输入活动标题', trigger: 'blur' }],
        quota: [{ required: true, message: '请输入活动名额', trigger: 'blur' }],
        deadline: [{ required: true, message: '请选择报名截止时间', trigger: 'change' }],
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
        address: [{ required: true, message: '请输入活动地址', trigger: 'blur' }],
        oldId: [{ required: true, message: '请输入老人ID', trigger: 'blur' }],
        description: [{ required: true, message: '请输入活动描述', trigger: 'blur' }],
        status: [{ required: true, message: '请选择活动状态', trigger: 'change' }],
        message: [{ required: false, message: '请输入管理员建议', trigger: 'blur' }]
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
          this.form.end = '23:59:59';
          const data = this.form;
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
          // 格式化截止时间
          data.deadline = format(data.deadline, "yyyy-MM-dd'T'HH:mm:ss", { timeZone: 'Asia/Shanghai' });
          data.date = formatDateString(data.date);
          data.begin = formatTimeString(data.begin);
          
          request.post('/administrator', data)
            .then(response => {
              if (response.code === 1) {
                this.$message.success(response.msg);
                this.resetForm();
              } else {
                this.$message.error(response.msg);
              }
            })
            .catch(error => {
              console.error('There was a problem with the request:', error);
            });
        } else {
          return false;
        }
      });
    },
    handleCancel() {
      this.$router.push('/adminView');
    },
    resetForm() {
      this.$refs.form.resetFields();
      this.form = {};
    }
  }
};
</script>

<style lang="scss" scoped>
.AddBox {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  .BOX {
    background-color: white;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 80%;
    .el-form {
      width: auto;
      padding: 20px;
      display: flex;
      flex-wrap: wrap;
      justify-content: center;
      align-items: center;
      margin: auto;
      .el-form-item {
        flex: 0 0 calc(40% - 30px); /* 减去你想要的间距 */
        margin-right: 30px; /* 添加你想要的间距 */
      }
    }
  }
}


</style>
