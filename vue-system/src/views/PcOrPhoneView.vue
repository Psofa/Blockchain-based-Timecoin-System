<template>
  <div>
    <!-- 尝试直接使用组件来排除错误 -->
    <MobileComponent v-if="isMobile" />
    <DesktopComponent v-else />
  </div>
</template>

<script>
import MobileComponent from '@/views/phoneui/LoginPhone.vue';
import DesktopComponent from '@/views/elementui/LoginForm.vue';

export default {
  data() {
    return {
      isMobile: false // 默认为电脑界面
    };
  },
  mounted() {
    // 检测屏幕宽度，根据媒体查询的结果确定当前是否为手机界面
    this.isMobile = window.matchMedia('(max-width: 768px)').matches;
    
    // 监听屏幕宽度变化，动态更新界面
    window.addEventListener('resize', this.handleResize);
  },
  beforeDestroy() {
    // 组件销毁时移除事件监听
    window.removeEventListener('resize', this.handleResize);
  },
  methods: {
    handleResize() {
      // 当屏幕宽度变化时，重新检测是否为手机界面
      this.isMobile = window.matchMedia('(max-width: 768px)').matches;
    }
  },
  components: {
    MobileComponent,
    DesktopComponent
  }
};
</script>

<style scoped>
/* 全局样式 */
</style>
