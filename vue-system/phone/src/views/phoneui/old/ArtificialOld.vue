<template>
    <div></div>
  </template>
  
  <script>
    export default {
      data() {
        return {
          fullscreenLoading: false
        }
      },
      created() {
        this.openFullScreen2();
      },
      methods: {
        openFullScreen2() {
          const loading = this.$loading({
            lock: true,
            text: 'Loading',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });
          setTimeout(() => {
            loading.close();
          }, 4000);
        }
      }
    }
  </script>