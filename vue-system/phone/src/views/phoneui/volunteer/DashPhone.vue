<template>
    <div class="Box">
        <div>
            <router-view></router-view>
        </div>
        <el-footer>
            <span class="link-container" style="margin-left: 35px;">
                <router-link to="/homePhone" class="RouterLink">
                    <i class="el-icon-house icon"></i>
                    <div class="text">用户管理</div>
                </router-link>
            </span>
            <span class="link-container">
                <router-link to="/addActivityPhone" class="RouterLink">
                    <i class="el-icon-circle-plus icon"></i>
                    <div class="text">报名活动</div>
                </router-link>
            </span>
            <span class="link-container" style="margin-right: 35px;">
                <router-link to="/infoOfUserPhone" class="RouterLink">
                    <i class="el-icon-user-solid icon"></i>
                    <div class="text">个人中心</div>
                </router-link>
            </span>
        </el-footer>
    </div>
</template>

<script>
</script>

<style lang="scss" scoped>
.Box {
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
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            text-align: center;
            .text {
                font-size: 10px; /* 设置文字的大小 */
            }
            .icon {
                font-size: 24px; /* 设置图标的大小 */
            }
        }
    }
}
</style>