<template>
  <el-container class="signup-container">
    <el-header>
      <el-button class="goBackButton" @click="goBack">
        <i class="el-icon-arrow-left" style="font-size: 2em;"></i>
      </el-button>
    </el-header>
    <el-main class="signup-box">
      <h1>注册</h1>
      <el-form ref="form" :model="form" :rules="formRules">
        <el-form-item prop="username">
          <el-input v-model="form.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="IdCard">
          <el-input v-model="form.idCard" placeholder="身份证号"></el-input>
        </el-form-item>
        <el-form-item prop="name">
          <el-input v-model="form.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="form.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="age">
          <el-input-number v-model="form.age" :min="0" placeholder="年龄" :style="{ width: '100%' }"></el-input-number>
        </el-form-item>
        <el-form-item prop="phone">
          <el-input v-model="form.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="form.password" placeholder="密码" autocomplete="new-password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input type="password" v-model="form.confirmPassword" placeholder="确认密码" autocomplete="new-password"></el-input>
        </el-form-item>
        <el-form-item prop="role">
          <el-select v-model="form.role" clearable placeholder="请选择用户类型" style="width:100%">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="signUp" class="register-btn">注册</el-button>
        </el-form-item>
        <div class="loginIcon">
          <div class="login-icon wechat"/>
          <div class="login-icon QQ"/>
          <div class="login-icon alipay"/>
        </div>
      </el-form>
    </el-main>
  </el-container>
</template>

<style scoped>
.signup-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 98vh;
  background-color: #f7f7f7;
  background-image: url('@/assets/common/login.jpg');
  background-size: cover; /* 或者 "contain" */
  background-position: center center;
}

.signup-box {
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 4px 10px rgba(240, 234, 234, 0.1);
  width: 90%;
  max-width: 400px;
  backdrop-filter: blur(5px); /* 设置背景虚化效果 */
}

.goBackButton {
  position: absolute;
  left: 0;
  background-color: transparent !important;
  border: none !important;
}

h1 {
  text-align: center;
  margin-bottom: 20px;
}

.register-btn {
  width: 100%;
}

.loginIcon {
  display: flex;
  justify-content: center;
}
.login-icon {
  width: 50px;
  height: 50px;
  border-radius: 20px;
  background: #f5f6f8;
  background-size: cover;
  margin: 10px;
  &.wechat {
    background-image: url('~@/assets/common/wechat.png');
  }
  &.QQ {
    background-image: url('~@/assets/common/QQ.png');
  }
  &.alipay {
    background-image: url('~@/assets/common/alipay.png');
  }
}
</style>

<script>
import request from '@/utils/request';

export default {
  name: 'RegisterPhone',
  data() {
    return {
      form: {}, // 定义 form 对象
      options: [
        { value: 1, label: '老人' },
        { value: 2, label: '志愿者' },
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
        ],
        idCard: [
          { required: true, message: '请输入身份证号', trigger: 'blur' },
          { pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '请输入正确的身份证号', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    clearall(){
      // 清空登录和注册表单数据
      this.form = {};
    },
    validateConfirmPassword(rule, value, callback) {
      if (value !== this.form.password) {
        callback(new Error('两次输入密码不一致'));
      } else {
        callback();
      }
    },
    signUp() {
      this.$refs.form.validate(valid => {
        if (valid) {
          const userData = this.form;
          request.post('/register', userData)
            .then(response => {
                // 处理成功响应
              if (response.code===1) {
                // 进行页面跳转或其他操作
                this.clearall();
                this.$message({
                  message: '用户审核中，请耐心等待！',
                  type: 'success'
                });
                setTimeout(() => {
                  this.$router.push('/');
                }, 2000); // 2000 毫秒后执行
              } 
              else {
                this.$message({
                  message: '注册失败:'+response.msg,
                  type: 'error'
                });
              }    
            })
            .catch(error => {
                // 处理请求失败的情况
                console.error('Registration failed:', error);
                alert('Registration failed. Please try again later.');
            });
        }
        else {
          this.$message.error('请完善信息');
          return false;
        }
      });
    },
    goBack() {
      this.$router.push('/');
    },
  }
};
</script>