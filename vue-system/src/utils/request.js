// api/request.js
import axios from 'axios';

// 创建 axios 实例
const request = axios.create({
  baseURL: 'http://localhost:3000', // 设置基础 URL
  timeout: 5000 // 设置超时时间
});

// 请求拦截器
request.interceptors.request.use(
  config => {
    // 在请求发送之前可以做一些处理，比如添加请求头等
    // 这里可以根据需要添加一些逻辑，比如添加 token 等
    const token = getItem('token');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
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
    return response.data;
  },
  error => {
    if (error.response && error.response.status === 401) {
      // 如果响应状态为 401，表示身份验证失败
      // 在这里可以进行一些处理，比如清除本地存储的 token，并重定向到登录页面
      alert('身份验证失败，请重新登录。');
      localStorage.removeItem('token'); // 清除本地存储的 token
      router.push('/'); // 重定向到登录页面
    }
    return Promise.reject(error);
  }
);

export default request;