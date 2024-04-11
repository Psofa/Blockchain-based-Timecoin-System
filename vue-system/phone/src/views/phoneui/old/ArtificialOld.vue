<template>
    <div class="phone-main">
        <div class="phone-content">
            
        </div>
        <el-footer>
            <span>
                <router-link to="/serverOld" class="RouterLink">
                    <i class="el-icon-menu"></i>服务中心
                </router-link>
            </span>
            <span>
                <router-link to="/artificialOld" class="RouterLink">
                    <i class="el-icon-s-comment"></i>人工服务
                </router-link>
            </span>
            <span>
                <router-link to="/homeOld" class="RouterLink">
                    <i class="el-icon-user-solid"></i>个人中心
                </router-link>
            </span>
        </el-footer>
    </div>
</template>

<script>
</script>

<style scoped>
.el-footer{
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-top: 10px;
    backdrop-filter: blur(10px);
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    flex-shrink: 0; /* 防止底部内容被压缩 */
    position: fixed; /* 将底部组件固定在页面底部 */
    bottom: 0;
    width: 100%; /* 设置宽度为 100% */
    .RouterLink {
        text-decoration: none;
    }
}
</style>