<template>
    <div class="mobile-app">
      <!-- 顶部导航栏 -->
      <div class="navbar">
        <span class="page-title">用户信息</span>
        <el-button class="profile-button" type="primary">个人资料</el-button>
      </div>
  
      <!-- 用户个人信息模块 -->
      <div class="user-info">
        <el-avatar class="avatar" size="large">用户头像</el-avatar>
        <div class="details">
          <p>用户名：{{ userInfo.username }}</p>
          <p>时间币余额：{{ userInfo.coinBalance }}</p>
          <p>信誉分数：{{ userInfo.reputationScore }}</p>
        </div>
      </div>
  
      <!-- 交易记录模块 -->
      <div class="transaction-history">
        <h2>交易记录</h2>
        <el-card v-for="transaction in transactions" :key="transaction.id" class="transaction-card">
          <p>{{ transaction.time }} {{ transaction.type }}  {{ transaction.amount }}</p>
          <el-descriptions :bordered="false" size="small">
            <el-descriptions-item label="详情">{{ transaction.details }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </div>
  
      <!-- 信誉分数模块 -->
      <div class="reputation-score">
        <h2>信誉分数</h2>
        <p>当前分数：{{ userInfo.reputationScore }}</p>
        <!-- 可以在这里添加信誉分数的历史变化趋势图表 -->
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        userInfo: {
          username: "stone",
          coinBalance: 8,
          reputationScore: 0, // 假设当前信誉分数为10
        },
        transactions: [
          { id: 1, time: "2024-04-12 21:59", type: "服务成功", amount: "+4", details: "完成服务打扫房间" },
          { id: 2, time: "2024-04-14 22:19", type: "服务成功", amount: "+4", details: "完成服务清洗房间" },
          // 更多交易记录...
        ],
      };
    },
    methods: {
      
    },
  };
  </script>
  
  <style scoped>
  .mobile-app {
    padding: 10px;
  }
  
  .navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 10px;
    background-color: #333;
    color: white;
  }
  
  .page-title {
    font-size: 18px;
  }
  
  .profile-button {
    margin-right: 20px;
  }
  
  .user-info {
    display: flex;
    align-items: center;
    padding: 20px;
    background-color: #f5f5f5;
  }
  
  .avatar {
    margin-right: 20px;
  }
  
  .transaction-history {
    margin-top: 20px;
  }
  
  .transaction-card {
    margin-bottom: 10px;
  }
  
  .reputation-score {
    margin-top: 20px;
  }
  </style>
  