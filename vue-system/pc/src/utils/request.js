// api/request.js
import axios from 'axios';
import router from '@/router';
import store from '@/store';
import { removeToken, getToken } from './auth';

// 创建 axios 实例
const request = axios.create({
  baseURL: 'http://10.134.112.37:8080', // 设置基础 URL
  timeout: 5000 // 设置超时时间
});

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 这里可以根据需要添加一些逻辑，比如添加 token 等
    const token = getToken();
    if (token) {
      //config.headers['Authorization'] = `Bearer ${token}`;
      config.headers.token = token; // 将token存储在请求头的token字段中
    }
    return config;
  },
  error => {
    // 对请求错误做些什么
    return Promise.reject(error);
  }
);

// 响应拦截器
request.interceptors.response.use(
  response => {
    // 对响应数据做些什么
    if (response.status === 404) {
      router.push('/404'); // 跳转到404页面
    } else if (response.data.code === 0 && router.currentRoute.path !== '/' && response.data.msg === "NOT_LOGIN") {
      alert('身份验证失败，请重新登录。');
      store.commit('setIsLoggedIn', false);
      router.push('/'); // 重定向到登录页面
      removeToken('token'); // 清除本地存储的 token
    }
    return response.data;
  },
  error => {
    return Promise.reject(error);
  }
);

export default request;