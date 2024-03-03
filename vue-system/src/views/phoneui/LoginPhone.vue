<template>
  <div class="login-container">
    <div class="logo">
      <!-- Logo goes here -->
      <img src="path/to/your/logo.png" alt="Logo">
    </div>
    <h2>Welcome Back!</h2>
    <form @submit.prevent="login">
      <div class="input-group">
        <input type="text" v-model="credentials.username" placeholder="Username or Email" required>
      </div>
      <div class="input-group">
        <input type="password" v-model="credentials.password" placeholder="Password" required>
      </div>
      <button type="submit">Login</button>
    </form>
    <a href="#" @click="forgotPassword">Forgot password?</a>
    <router-link to="/register">Create New Account</router-link>
  </div>
</template>

<script>
export default {
  name: 'LoginView',
  data() {
    return {
      credentials: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    login() {
      // Implement your login logic here
      console.log('Login credentials', this.credentials);
    },
    forgotPassword() {
      // Navigate to forgot password view
      console.log('Navigate to forgot password view');
    }
  }
}
</script>

<style>
.login-container {
  max-width: 400px;
  margin: 50px auto;
  padding: 20px;
  text-align: center;
}

.logo img {
  max-width: 100px;
  margin-bottom: 20px;
}

.input-group {
  margin-bottom: 20px;
}

input[type="text"], input[type="password"] {
  width: 100%;
  padding: 10px;
  margin: 5px 0;
  display: inline-block;
  border: 1px solid #ccc;
  box-sizing: border-box;
}

button[type="submit"] {
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 100%;
}

button[type="submit"]:hover {
  opacity: 0.8;
}

a, .router-link {
  display: block;
  margin: 10px 0;
}
</style>
