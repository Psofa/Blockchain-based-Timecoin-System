<template>
  <body>
    <div id="app">
      <router-view></router-view>  <!-- 使用 <router-view> 渲染整个应用程序的内容 -->
    </div>
  </body>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
/* 样式 */
body{
	margin:0;
	padding:0;
	border:0
}

</style>
