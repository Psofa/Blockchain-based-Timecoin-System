<template>
  <div class="main-container">
    <div class="image-container">
      <div class="system-title">时间币服务系统</div>
    </div>
    <div class="login-container">
      <div id="login-box" class="container" :class="{ 'right-panel-active': isRightPanelActive }">
        <div class="form-container sign-up-container">
          <form>
            <h1>注册</h1>
            <div class="txtb">
              <input v-model="signUpUsername" type="text" placeholder="Username">
            </div>
            <div class="txtb">
              <input v-model="signUpName" type="text" placeholder="Name">
            </div>
            <div class="txtb">
              <input v-model="signUpEmail" type="email" placeholder="Email">
            </div>
            <div class="txtb">
              <input v-model="signUpPhone" type="text" placeholder="Phone">
            </div>
            <div class="txtb">
              <input v-model="age" type="text" placeholder="Age">
            </div>
            <div class="txtb">
              <input v-model="signUpPassword" type="password" placeholder="Password">
            </div>
            <div class="txtb">
              <input v-model="confirmPassword" type="password" placeholder="Confirm Password">
            </div>
            <div>
              <el-radio v-model="radio" label="1">老人</el-radio>
              <el-radio v-model="radio" label="2">志愿者</el-radio>
            </div>
            <el-button @click="signUp">注册</el-button>
            <div class="loginIcon">
              <div class="login-icon wechat"/>
              <div class="login-icon QQ"/>
              <div class="login-icon alipay"/>
            </div>
          </form>
        </div>
        <div class="form-container sign-in-container">
          <form>
            <h1>登录</h1>
            <div style="margin-top: 20px;margin-bottom: 20px;">登陆方式</div>
            <div class="loginIcon">
              <div class="login-icon wechat"/>
              <div class="login-icon QQ"/>
              <div class="login-icon alipay"/>
            </div>
            <div class="txtb">
              <input v-model="signInUsername" type="text" placeholder="Username">
            </div>
            <div class="txtb" prop="signInPassword">
              <input v-model="signInPassword" type="password" placeholder="Password">
            </div>
            <a href="#">忘记密码？</a>
            <el-button :plain="true" @click="signIn">登录</el-button>
          </form>
        </div>
        <div class="overlay-container">
          <div class="overlay">
            <div class="overlay-panel overlay-left">
              <h1>已有账号？</h1>
              <p>请使用您的账号进行登录</p>
              <button class="ghost" @click="togglePanel('signIn')">登录</button>
            </div>
            <div class="overlay-panel overlay-right">
              <h1>没有账号?</h1>
              <p>立即注册加入我们，和我们一起开始旅程吧</p>
              <button class="ghost" @click="togglePanel('signUp')">注册</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { setToken } from '@/utils/auth';
import request from '@/utils/request';

export default {
  name: 'LoginView',
  data() {
    return {
      isRightPanelActive: false,
      signUpUsername: '',
      signUpEmail: '',
      signUpPassword: '',
      confirmPassword: '',
      signUpName: '',
      signUpPhone: '',
      age: '',
      radio: '1',
      signInUsername: '',
      signInPassword: '',
      isLoggedIn: false // 标记用户登录状态
    };
  },
  methods: {
    clearAll() {
      this.signUpUsername = '';
      this.signUpEmail = '';
      this.signUpPassword = '';
      this.confirmPassword = '';
      this.signUpName = '';
      this.signUpPhone = '';
      this.age = '';
      this.radio = '1';
      this.signInUsername = '';
      this.signInPassword = '';
    },
    togglePanel(panel) {
      this.isRightPanelActive = panel === 'signUp';
      this.clearAll();
    },
    signUp() {
      const userData = {
        username: this.signUpUsername,
        name: this.signUpName,
        password: this.signUpPassword,
        role: parseInt(this.radio),
        email: this.signUpEmail,
        age: parseInt(this.age),
        phone: this.signUpPhone,
      };
      if (
        userData.username &&
        userData.name &&
        userData.password &&
        userData.role &&
        userData.email &&
        userData.age &&
        userData.phone
      ) {
        if (this.signUpPassword === this.confirmPassword) {
          request.post('/register', userData)
            .then(response => {
              if (response.code === 1) {
                this.clearAll();
                this.$message({
                  message: '注册成功',
                  type: 'success'
                });
              }
              else {
                this.$message.error('注册失败:' + response.msg);
              }
            })
            .catch(error => {
              console.error('Registration failed:', error);
              alert('Registration failed. Please try again later.');
            });
        } else {
          this.$message.error('请验证密码是否正确');
        }
      } else {
        this.$message.error('请完善信息');
      }
    },
    signIn() {
      const userData = {
        username: this.signInUsername,
        password: this.signInPassword,
        role: 3, // 管理员
      };

      if (userData.username === '' || userData.password === '') {
        this.$message.error('请输入账号或密码');
        return;
      }

      request.post('/login', userData)
        .then(response => {
          if (response.code === 1) {
            this.$message({
              message: '登录成功',
              type: 'success'
            });
            setToken(response.data.token);
            this.$store.commit('setToken', response.data.token);
            this.isLoggedIn = true;
            setTimeout(() => {
              this.$router.push({ name: 'HomeView' });
            }, 1500);
          }
          else {
            this.$message.error(response.msg);
          }
        })
        .catch(error => {
          console.error('Login request failed:', error);
          alert('Login request failed. Please try again later.');
        });
    }
  }
};
</script>

<style scoped>
* {
    box-sizing: border-box;
}
body {
    font-family: 'Montserrat',sans-serif;
    background-image: linear-gradient(120deg,#3498db,#8e44ad);;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: -20px 0 50px;
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
    background-image: url('~@/assets/myResource/wechat.png');
  }
  &.QQ {
    background-image: url('~@/assets/myResource/QQ.png');
  }
  &.alipay {
    background-image: url('~@/assets/myResource/alipay.png');
  }
}
h1 {
    font-weight: bold;
    margin: 0;
}
p {
    font-size: 14px;
    line-height: 20px;
    letter-spacing: .5px;
    margin: 20px 0 30px;
}
span {
    font-size: 12px;
}
a {
    color: #333;
    font-size: 14px;
    text-decoration: none;
    margin: 15px 0;
}
.main-container {
  display: flex;
  height: 100vh;
}
.image-container {
  width: 45%; /* 左边留 60% 的空间 */
  background: url("@/assets/myResource/login2.jpg") center center/cover;
  text-align: center;
}
.login-container {
  background: url("@/assets/myResource/login1.jpg") center center/cover;
  flex: 1;
  background-size: cover;
  background-position: center;
  position: relative;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.container {
    backdrop-filter: blur(10px);
    border-radius: 40px;
    box-shadow: 0 14px 28px rgba(0, 0, 0, .25), 0 10px 10px rgba(0, 0, 0, .22);
    position: relative;
    overflow: hidden;
    width: 800px;
    max-width: 100%;
    min-height: 600px;
    text-align: center;

}

.form-container form {
    
    backdrop-filter: blur(10px);
    display: flex;
    flex-direction: column;
    padding: 0 50px;
    height: 100%;
    justify-content: center;
    text-align: center;
}
.social-container {
    margin: 20px 0;
}

.social-container a {
    border: 1px solid #ddd;
    border-radius: 50%;
    display: inline-flex;
    justify-content: center;
    align-items: center;
    margin: 0 5px;
    height: 40px;
    width: 40px;
}

.social-container a:hover {
    background-color: #eee;

}

.txtb {
    border-bottom: 2px solid #adadad;
    position: relative;
    margin: 10px 0;
}

.txtb input {
    font-size: 15px;
    color: #333;
    border: none;
    width: 100%;
    outline: none;
    background: none;
    padding: 0 3px;
    height: 35px;
}

.txtb span::before {
    content: attr(data-placeholder);
    position: absolute;
    top: 50%;
    left: 5px;
    color: #adadad;
    transform: translateY(-50%);
    transition: .5s;
}
.txtb span::after {
    content: '';
    position: absolute;
    left: 0%;
    top: 100%;
    width: 0%;
    height: 2px;
    background-image: linear-gradient(120deg,#3498db,#8e44ad);
    transition: .5s;
}

.focus + span::before {
    top: -5px;
}

.focus + span::after {
    width: 100%;
}

button {
    border-radius: 20px;
    border: 1px solid #ff4b2b;
    background: #ff4b2b;
    color: #fff;
    font-size: 12px;
    font-weight: bold;
    padding: 12px 45px;
    letter-spacing: 1px;
    text-transform: uppercase;
    transition: transform 80ms ease-in;
    cursor: pointer;
}

button:active {
    transform: scale(.95);
}

button:focus {
    outline: none;
}

button.ghost {
    background: transparent;
    border-color: #fff;
}

.form-container {
    position: absolute;
    top: 0;
    height: 100%;
    transition: all .6s ease-in-out;
}

.form-container button {
    background: linear-gradient(120deg, #3498db, #8e44ad);
    border: none;
    background-size: 200%;
    color: #fff;
    transition: .5s;
}

.form-container button:hover {
    background-position: right;
}

.sign-in-container {
    left: 0;
    width: 50%;
    z-index: 2;
}
.sign-in-container form a {
    position: relative;
    left: 100px;
}
.sign-up-container {
    left: 0;
    width: 50%;
    z-index: 1;
    opacity: 0;
}
.sign-up-container button {
    margin-top: 20px;
}
.overlay-container {
    position:absolute;
    top: 0;
    left: 50%;
    width: 50%;
    height: 100%;
    overflow: hidden;
    transition: transform .6s ease-in-out;
    z-index: 100;
}
.overlay {
    background-image: linear-gradient(120deg,#3498db,#8e44ad);
    color: #fff;
    position: relative;
    left: -100%;
    height: 100%;
    width: 200%;
    transform: translateY(0);
    transition: transform .6s ease-in-out;
}
.overlay-panel {
    position: absolute;
    top: 0;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 0 40px;
    height: 100%;
    width: 50%;
    text-align: center;
    transform: translateY(0);
    transition: transform .6s ease-in-out;
}
.overlay-right {
    right: 0;
    transform: translateY(0);
    
}

.overlay-left {
    transform: translateY(-20%);
}

.container.right-panel-active .sign-in-container {
    transform: translateY(100%);
}

.container.container.right-panel-active .overlay-container {
    transform: translateX(-100%);
}
.container.right-panel-active .sign-up-container {
    transform: translateX(100%);
    opacity: 1;
    z-index: 5;
}
.container.container.right-panel-active .overlay {
    transform: translateX(50%);
}
.container.container.right-panel-active .overlay-left {
    transform: translateY(0);
}
.container.container.right-panel-active .overlay-right {
    transform: translateY(20%);
}
.system-title {
  color: #6190bf; /* 设置文字颜色为 Element UI 的主题色 */
  font-size: 3em; /* 设置文字大小为 3em */
  font-weight: bold; /* 设置文字为粗体 */
  text-align: center; /* 设置文字居中对齐 */
  text-shadow: 10px 10px 40px rgba(0, 0, 0, 0.5); /* 添加文字阴影 */
  font-family: 'Arial', sans-serif; /* 改变字体 */
  padding: 10px; /* 添加内边距 */
  position: relative;
  font-style: italic; /* 设置文字为斜体 */
  top: 15%;
}
</style>
