<template>
  <div class="mobile-form">
    <div class="header">
      <el-steps :active="active" finish-status="success">
        <el-step title="填写活动详情"></el-step>
        <el-step title="获取详细地址"></el-step>
        <el-step title="提交"></el-step>
      </el-steps>
    </div>
    
    <el-main>
      <router-view @next="next"></router-view>
    </el-main>
    
  </div>
</template>
  
<script>

export default {
    name: 'AddActivityOld',
    data() {
        return {
          // 步骤条
          active: 1,
        };
    },
    methods: {
      next() {
        if (this.active < 2) this.active++;
      },
    }
};
</script>
  
<style lang="scss" scoped>
.mobile-form {
  padding: 20px;

  .header {
      margin: 10px;
  }
  .el-main {
      backdrop-filter: blur(40px); /* 添加毛玻璃效果 */
      border-radius: 20px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.2); /* 添加阴影效果 */
      background-color: rgba(255, 255, 255, 0.4);
      margin-top: 30px;
  }
}
</style>
  