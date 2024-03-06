<template>
  <div class="form-container">
    <form>
      <h1>注册</h1>
      <div class="txtb">
        <input type="text" v-model="signUpUsername" placeholder="Useranme">
      </div>
      <div class="txtb">
        <input type="text" v-model="signUpName" placeholder="Name">
      </div>
      <div class="txtb">
        <input type="email" v-model="signUpEmail" placeholder="Email">
      </div>
      <div class="txtb">
        <input type="text" v-model="signUpPhone" placeholder="Phone">
      </div>
      <div class="txtb">
        <input type="text" v-model="age" placeholder="Age">
      </div>
      <div class="txtb">
        <input type="password" v-model="signUpPassword" placeholder="Password">
      </div>
      <div class="txtb">
        <input type="password" v-model="confirmPassword" placeholder="Confirm Password">
      </div>
      <div >
        <el-radio v-model="radio" label="1">老人</el-radio>
        <el-radio v-model="radio" label="2">志愿者</el-radio>
        <el-radio v-model="radio" label="3">管理员</el-radio>
      </div>
      <el-button  @click="signUp">注册</el-button>
    </form>
  </div>
</template>

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
      registerForm: {
        signUpUsername: '',
        signUpEmail: '',
        signUpPassword: '',
        confirmPassword: '',
        signUpName:'',
        signUpPhone:'',
        age:'',
        radio: '1',
      }
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
  }
};
</script>

<style scoped>
.form-container {
  background: #f0f2f5;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
}

.form-container h1 {
  text-align: center;
  margin-bottom: 20px;
}

.txtb {
  margin-bottom: 20px;
}

.txtb .el-input,
.el-radio-group {
  width: 100%;
}

.el-radio-group {
  display: flex;
  justify-content: space-around;
  margin: 20px 0;
}

.el-button {
  width: 100%;
  border: none;
  border-radius: 5px;
}

/* 响应式设计: 对于较小屏幕的手机，调整间距和布局 */
@media (max-width: 768px) {
  .form-container {
    padding: 15px;
    margin: 20px auto;
  }

  .form-container h1 {
    font-size: 18px; /* 调整小屏幕上的标题字体大小 */
  }

  .txtb .el-input,
  .el-radio-group {
    flex-direction: column;
  }

  .el-radio-group .el-radio {
    margin-bottom: 10px; /* 增加单选按钮之间的间距 */
  }
  
  .el-button {
    padding: 12px 20px; /* 增加按钮的填充，使其在小屏幕上更容易点击 */
  }
}

/* 更小的屏幕尺寸 */
@media (max-width: 480px) {
  .form-container {
    padding: 10px;
    margin: 15px auto;
  }

  .form-container h1 {
    font-size: 16px; /* 更小屏幕上的标题字体大小 */
  }
  
  /* 可以添加更多的样式来适应更小屏幕的布局 */
}
</style>
