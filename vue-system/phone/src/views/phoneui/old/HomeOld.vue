<template>
    <div class="infoBox">
        <el-header>
            <el-button type="text" @click="redirectToInfo" style="margin-left: auto;">
                <i class="el-icon-setting" style="font-size:x-large;"></i>
            </el-button>
        </el-header>
        <div class="mainBox">
            <div class="titleBox">
                <div>
                    <img v-if="squareUrl" class="avatar1" :src="squareUrl">
                    <span v-else class="username1">
                        <div style="font-size: 30px;">
                            {{ infoData.username?.charAt(0) }}
                        </div>
                    </span>
                </div>
                <div>
                    {{infoData.username}}
                </div>
                <el-row :gutter="20" style="display: flex;
                justify-content: space-between;
                align-items: center;">
                    <div>
                        <el-statistic title="时间币总数">
                        <template slot="formatter">
                            456
                        </template>
                        </el-statistic>
                    </div>
                </el-row>
            </div>
            <div class="contentBox">
                <el-menu style="width: 100%;">
                    <el-menu-item index="1">
                        <div>
                            <i class="el-icon-coin"></i>
                            <span>时间币详情</span>
                        </div>
                        <div>
                            <i class="el-icon-arrow-right"></i>
                        </div>
                    </el-menu-item>
                    <div @click="queryActivity">
                        <el-menu-item index="2">
                            <div>
                                <i class="el-icon-s-flag"></i>
                                <el-button style="background: none; border: none; padding: 0; cursor: pointer;">我发布的活动</el-button>
                            </div>
                            <div>
                                <i class="el-icon-arrow-right"></i>
                            </div>
                        </el-menu-item>
                    </div>
                </el-menu>
            </div>
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
import router from '@/router';
import request from '@/utils/request';

export default {
    name: 'HomeOld',
    data() {
        return{
            infoData: {},
            squareUrl: '',
        }
    },
    created() {
        this.search();
    },
    methods: {
        redirectToInfo() {
            router.push('infoPhone');
        },
        search() {    
            request.get(`/info`)
                .then(response => {
                if (response.code === 1) {
                    this.infoData = response.data;
                    this.squareUrl = response.data.image;
                } else {
                    this.$message.error(response.msg);
                }
                })
                .catch(error => {
                    console.error('获取数据失败:', error);
                });
        },
        queryActivity() {
            this.$router.push('activityOld');
        },
    }
}
</script>

<style lang="scss" scoped>
.infoBox {
    .el-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .mainBox{
        display: flex;
        align-items: center;
        flex-direction: column;
        height: 100%;
        .titleBox{
            width: 90%;
            height: 150px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            flex-shrink: 0; /* 防止底部内容被压缩 */
            .avatar1 {
                display: inline-block;
                width: 60px;
                height: 60px;
                border-radius: 12px;
                background-color: #d9d9d9;
                line-height: 48px;
                text-align: center;
            }
            .username1 {
                display: inline-block;
                width: 60px;
                height: 60px;
                text-align: center;
                line-height: 60px;
                border-radius: 50%;
                background: #04c9be;
                color: #fff;
                margin-right: 4px;
            }
        }
        .contentBox{
            width: 100%;
            height: auto;
            display: flex;
            align-items: center;
            flex-direction: column;
            flex-shrink: 0; /* 防止底部内容被压缩 */
            margin: 10px;
            .el-menu{
                width: 100%;
                .el-menu-item{
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                }
            }
        }
    }
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
}
</style>