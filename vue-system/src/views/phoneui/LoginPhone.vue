<template>
  <div class="login-container">
    <div class="login-box">
      <h1>登录</h1>
      <form @submit.prevent="onSubmit">
        <div class="input-group">
          <label for="username">用户名</label>
          <input type="text" id="username" v-model="loginForm.username" placeholder="请输入用户名" required>
        </div>
        <div class="input-group">
          <label for="password">密码</label>
          <input type="password" id="password" v-model="loginForm.password" placeholder="请输入密码" required>
        </div>
        <button type="submit" class="login-btn">登录</button>
        <div class="register-link" style="margin-top: 20px;">
          <span style="color: white;">没有账户？</span>
          <router-link to="/registerPhone" style="color: blue; text-decoration: none;">立即注册</router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MobileLogin',
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
      }
    };
  },
  methods: {
    onSubmit() {
      // 执行登录逻辑
      console.log('登录信息', this.loginForm);
      // 这里可以加入登录验证逻辑，并处理成功或失败的情况
    }
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 92.5vh;
  background-color: #f7f7f7;
  padding: 20px;
  background-image: url('C:\Users\31744\Pictures\2.jpg');
}

.login-box {
  padding: 20px;
  border-radius: 8px;
  width: 100%;
  max-width: 400px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(5px); /* 设置背景虚化效果 */
  
}

h1 {
  text-align: center;
  margin-bottom: 2rem;
}

.input-group {
  margin-bottom: 1rem;
}

.input-group label {
  display: block;
  margin-bottom: .5rem;
}

.input-group input {
  width: 95%;
  padding: .5rem;
  font-size: 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.login-btn {
  background-color: #409EFF;
  color: white;
  border: none;
  padding: 10px;
  font-size: 1rem;
  border-radius: 4px;
  width: 100%;
  cursor: pointer;
  transition: background-color .3s;
  margin-top: 30px; /* 按钮之间的上边距 */
}

.login-btn:hover {
  background-color: #3679ec;
}
</style>
