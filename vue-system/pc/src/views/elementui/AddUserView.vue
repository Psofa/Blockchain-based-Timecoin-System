<template>
  <div class="AddBox">
    <div class="BOX">
      <h2>添加用户</h2>
      <el-form ref="form" :model="form" :rules="formRules">
        <el-form-item label="用户名" prop="username" label-width="100px">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="用户类型" prop="role" label-width="100px">
          <el-select v-model="form.role" clearable placeholder="请选择" style="margin-right: 20px;">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱" prop="email" label-width="100px">
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item label="年龄" prop="age" label-width="100px">
          <el-input-number v-model="form.age" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="电话" prop="phone" label-width="100px">
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name" label-width="100px">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" label-width="100px">
          <el-input type="password" v-model="form.password" autocomplete="new-password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword" label-width="100px">
          <el-input type="password" v-model="form.confirmPassword" autocomplete="new-password"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleCancel" style="width: 48%;">取消</el-button>
          <el-button type="primary" @click="addUser" style="width: 48%;margin-left:4%;">提交</el-button>
        </el-form-item>
      </el-form>
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
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur,change' }
        ],
        age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
        phone: [
          { required: true, message: '请输入电话', trigger: 'blur' },
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的11位电话号码', trigger: 'blur' }
        ],
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
        else {
          this.$message.error('请完善信息');
          return false;
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
.AddBox {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 80%;
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
