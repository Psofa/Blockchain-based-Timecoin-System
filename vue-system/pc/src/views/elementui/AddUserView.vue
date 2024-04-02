<template>
  <div>
    <el-form ref="form" :model="form" :rules="formRules">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="form.username"></el-input>
      </el-form-item>
      <el-form-item label="用户类型" prop="role">
        <el-select v-model="form.role" clearable placeholder="请选择" style="margin-right: 20px;">
          <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email"></el-input>
      </el-form-item>
      <el-form-item label="年龄" prop="age">
        <el-input-number v-model="form.age" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phone"></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input v-model="form.address"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input v-model="form.name"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="form.password" autocomplete="new-password"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input type="password" v-model="form.confirmPassword" autocomplete="new-password"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="footer">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="addUser">提交</el-button>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request';

export default {
  name: 'AddUserView',
  data() {
    return {
      form: {}, // 定义 form 对象
      options: [
        { value: 1, label: '老人' },
        { value: 2, label: '志愿者' },
        { value: 3, label: '管理员' },
      ],
      formRules: { // 表单校验规则
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        role: [{ required: true, message: '请选择用户类型', trigger: 'change' }],
        email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
        age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
        phone: [{ required: true, message: '请输入电话', trigger: 'blur' }],
        address: [{ required: true, message: '请输入地址', trigger: 'blur' }],
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码长度至少为6位', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: this.validateConfirmPassword, trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    addUser() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const data = this.form;
          request.post('/administrator/users', data)
            .then(response => {
              if (response.code === 1) {
                this.$message.success(response.msg);
                this.resetForm();
              } else {
                this.$message.error(response.msg);
              }
            })
            .catch(error => {
              console.error('添加用户失败:', error);
            });
        }
      });
    },
    resetForm() {
      this.$refs.form.resetFields(); // 重置表单
    },
    validateConfirmPassword(rule, value, callback) {
      if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致'));
      } else {
        callback();
      }
    },
    handleCancel() {
      this.$router.push('/usersView'); // 取消按钮跳转
    }
  }
}
</script>

<style lang="scss" scoped>
.el-form {
  width: 40%;
}
.footer {
  width: 40%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}
</style>
