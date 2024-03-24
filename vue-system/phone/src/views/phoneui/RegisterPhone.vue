<template>
  <el-container class="signup-container">
    <el-header>
      <el-button class="goBackButton" @click="goBack">
        <i class="el-icon-arrow-left"></i>
      </el-button>
    </el-header>
    <el-main class="signup-box">
      <h1>注册</h1>
      <form>
        <div class="form-group">
          <input type="text" v-model="signUpUsername" placeholder="用户名" class="form-control">
        </div>
        <div class="form-group">
          <input type="text" v-model="signUpName" placeholder="姓名" class="form-control">
        </div>
        <div class="form-group">
          <input type="email" v-model="signUpEmail" placeholder="邮箱" class="form-control">
        </div>
        <div class="form-group">
          <input type="text" v-model="signUpPhone" placeholder="电话" class="form-control">
        </div>
        <div class="form-group">
          <input type="text" v-model="age" placeholder="年龄" class="form-control">
        </div>
        <div class="form-group">
          <input type="password" v-model="signUpPassword" placeholder="密码" class="form-control">
        </div>
        <div class="form-group">
          <input type="password" v-model="confirmPassword" placeholder="确认密码" class="form-control">
        </div>
        <div class="form-group" style="display: flex; justify-content: center;">
          <el-radio v-model="radio" label="1">老人</el-radio>
          <el-radio v-model="radio" label="2">志愿者</el-radio>
          <el-radio v-model="radio" label="3">管理员</el-radio>
        </div>
        <el-button type="primary" @click="signUp" class="register-btn">注册</el-button>
      </form>
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
  background-image: url('C:\Users\31744\Pictures\2.jpg');;
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

.form-group {
  margin-bottom: 20px;
}

.form-control {
  width: 92.5%;
  padding: 12px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.register-btn {
  width: 100%;
}
</style>

<script>
import axios from 'axios';
import { Radio,Button } from 'element-ui';

// 定义基本 URL
const baseURL = 'http://localhost:3000/';

export default {
  name: 'RegisterPhone',
  components: {
    'el-radio': Radio,
    'el-button': Button,
  },
  data() {
    return {
      signUpUsername: '',
      signUpEmail: '',
      signUpPassword: '',
      confirmPassword: '',
      signUpName:'',
      signUpPhone:'',
      age:'',
      radio: '1',
    };
  },
  methods: {
    clearall(){
      // 清空登录和注册表单数据
      this.signUpUsername = '';
      this.signUpEmail = '';
      this.signUpPassword = '';
      this.confirmPassword = '';
      this.signUpName='';
      this.signUpPhone='';
      this.age='';
      this.radio='1';
    },
    signUp() {
      // 处理注册逻辑
      const userData = {
          username: this.signUpUsername,
          name:this.signUpName,
          password: this.signUpPassword,
          role: parseInt(this.radio),
          email: this.signUpEmail,
          age:parseInt(this.age),
          phone:this.signUpPhone,
        };

        // 检查是否所有属性都有值
      if (
          userData.username &&
          userData.name &&
          userData.password &&
          userData.role &&
          userData.email &&
          userData.age &&
          userData.phone
      ) {
          if(this.signUpPassword===this.confirmPassword){
            // 发起 POST 请求
            axios.post(baseURL+'register', userData)
              .then(response => {
                  // 处理成功响应
                if (response.data.code===1) {
                  // 进行页面跳转或其他操作
                  this.clearall();
                  this.$message({
                    message: '注册成功',
                    type: 'success'
                  });
                  setTimeout(() => {
                    this.$router.push('/');
                  }, 2000); // 2000 毫秒后执行
                } 
                else {
                  this.$message.error('注册失败:',response.data.msg);
                }    
              })
              .catch(error => {
                  // 处理请求失败的情况
                  console.error('Registration failed:', error);
                  alert('Registration failed. Please try again later.');
              });
          } else{
            this.$message.error('请验证密码是否正确');
          }
      } else {
        this.$message.error('请完善信息');
      }
    },
    goBack() {
      this.$router.push('/');
    },
  }
};
</script>
