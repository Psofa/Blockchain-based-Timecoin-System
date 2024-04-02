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
          placeholder="活动截止时间"
          align="right"
          :picker-options="pickerOptionsofform">
        </el-date-picker>
      </el-form-item>
      <el-form-item prop="date">
        <el-date-picker type="date" v-model="form.date" placeholder="活动日期"></el-date-picker>
      </el-form-item>
      <el-form-item prop="begin">
        <el-input v-model="form.begin" placeholder="活动开始时间"></el-input>
      </el-form-item>
      <el-form-item prop="end">
        <el-input v-model="form.end" placeholder="活动结束时间"></el-input>
      </el-form-item>
      <el-form-item prop="address">
        <el-input v-model="form.address" placeholder="活动地址"></el-input>
      </el-form-item>
      <el-form-item prop="oldId">
        <el-input v-model="form.oldId" placeholder="老人ID"></el-input>
      </el-form-item>
      <el-form-item prop="phone">
        <el-input v-model="form.phone" placeholder="发布人电话"></el-input>
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
      <el-form-item style="display: flex; justify-content: space-between; align-items: center;">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="onSubmitForm">提交</el-button>
      </el-form-item>
    </el-form>
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
        date: [{ required: true, message: '请选择活动日期', trigger: 'change' }],
        begin: [{ required: true, message: '请输入活动开始时间', trigger: 'blur' }],
        end: [{ required: true, message: '请输入活动结束时间', trigger: 'blur' }],
        address: [{ required: true, message: '请输入活动地址', trigger: 'blur' }],
        oldId: [{ required: true, message: '请输入老人ID', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入发布人电话', trigger: 'blur' }],
        description: [{ required: true, message: '请输入活动描述', trigger: 'blur' }],
        status: [{ required: true, message: '请选择活动状态', trigger: 'change' }],
        message: [{ required: false, message: '请输入管理员建议', trigger: 'blur' }]
      }
    };
  },
  methods: {
    onSubmitForm() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const data = this.form;
          // 格式化截止时间
          data.deadline = format(data.deadline, "yyyy-MM-dd'T'HH:mm:ss", { timeZone: 'Asia/Shanghai' });
            
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
.el-form {
  width: 40%;
}
</style>
