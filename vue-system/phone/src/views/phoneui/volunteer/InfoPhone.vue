<template>
    <div>
        <el-dialog
            title="个人信息"
            :visible.sync="dialogVisible"
            :before-close="handleClose"
            width="80%"
            :append-to-body="true"
            :modal-append-to-body="false">
            <el-form ref="form" :model="form">
                <el-form-item label="用户id">
                    <el-input v-model="form.id" readonly></el-input>
                </el-form-item>
                <el-form-item label="用户名">
                    <el-input v-model="form.username" readonly></el-input>
                </el-form-item>
                <el-form-item label="用户类型">
                    <el-input v-model="statusLabel" readonly></el-input>
                </el-form-item>
                <el-form-item label="邮箱">
                    <el-input v-model="form.email" readonly></el-input>
                </el-form-item>
                <el-form-item label="年龄">
                    <el-input v-model="form.age" readonly></el-input>
                </el-form-item>
                <el-form-item label="电话">
                    <el-input v-model="form.phone" readonly></el-input>
                </el-form-item>
                <el-form-item label="地址">
                    <el-input v-model="form.address" readonly></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="form.name" readonly></el-input>
                </el-form-item>
                <el-form-item label="密码">
                    <el-input v-model="form.password" readonly></el-input>
                </el-form-item>
            </el-form>
            <div  style="display: flex; justify-content: space-between; margin-top: 10px;">
                
                <el-button @click="handleCancel" round style="width: 100%;">取 消</el-button>
            </div>
        </el-dialog>
        <div class="mainBox">
            <el-menu v-model="form" style="width: 100%;">
                <el-menu-item index="1">
                    <span>用户头像</span> 
                    <div>
                        <el-avatar shape="square" :size="50" :src="squareUrl"></el-avatar>
                        <i class="el-icon-arrow-right"></i>
                    </div>
                </el-menu-item>
                <el-menu-item index="2">
                    <span>用户名</span>
                    <div>
                        {{ form.username }}
                        <i class="el-icon-arrow-right"></i>
                    </div>
                </el-menu-item>
                <el-menu-item index="3">
                    <span>手机号</span>
                    <div>
                        {{ form.phone }}
                        <i class="el-icon-arrow-right"></i>
                    </div>
                </el-menu-item>
                <el-menu-item index="4">
                    <span>邮件</span>
                    <div>
                        {{ form.email }}
                        <i class="el-icon-arrow-right"></i>
                    </div>
                </el-menu-item>
                <div @click="query">
                    <el-menu-item index="5">
                        <el-button style="background: none; border: none; padding: 0; cursor: pointer;">个人信息</el-button>
                        <div>
                            <i class="el-icon-arrow-right"></i>
                        </div>
                    </el-menu-item>
                </div>
                <div @click="logout">
                    <el-menu-item index="6">
                        <el-button @click="logout" 
                        style="background: none;border: none;padding: 0;cursor: pointer;">退出登录</el-button>
                        <div>
                            <i class="el-icon-arrow-right"></i>
                        </div>
                    </el-menu-item>
                </div>
            </el-menu>
        </div>
        <el-footer>
            <span>
            <router-link to="/homePhone" class="RouterLink">
                <i class="el-icon-house"></i>用户管理
            </router-link>
            </span>
            <span>
            <router-link to="/addActivityPhone" class="RouterLink">
                <i class="el-icon-circle-plus"></i>报名活动
            </router-link>
            </span>
            <span>
            <router-link to="/infoOfUserPhone" class="RouterLink">
                <i class="el-icon-user-solid"></i>个人中心
            </router-link>
            </span>
        </el-footer>
    </div>
</template>

<script>
import { removeToken } from '@/utils/auth';
import request from '@/utils/request';

export default {
    name: 'InfoPhone',
    data() {
        return{
            form: {},
            squareUrl:'',
            dialogVisible: false,
            options: [
                { value: 1, label: '老人' },
                { value: 2, label: '志愿者' },
                { value: 3, label: '管理员' },
            ],
        }
    },
    created() {
        this.search();
        this.statusLabel();
    },
    methods: {
        // 退出登录
        async logout() {
            // 调用action
            await this.$store.dispatch('logout');
            // 执行退出登录的操作，清除本地存储中的 token
            removeToken('token');
            // 重定向到登录页面
            this.$router.push('/');
        },
        search() {    
            request.get(`/info`)
                .then(response => {
                if (response.code === 1) {
                    this.form = response.data;
                    this.squareUrl = response.data.image;
                    
                } else {
                    this.$message.error(response.msg);
                }
                })
                .catch(error => {
                    console.error('获取数据失败:', error);
                });
        },
        handleCancel(){
            this.dialogVisible = false;
        },
        query() {
            // 打开
            this.dialogVisible = true;
        },
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(() => {
                done();
                })
                .catch(() => {});
        },
        statusLabel() {
            const selectedOption = this.options.find(option => option.value === this.form.role);
            return selectedOption ? selectedOption.label : '未知状态';
        },
    }
}
</script>

<style lang="scss" scoped>
.mainBox{
    padding: 0px;
    border: none;
    .el-menu-item{
        display: flex;
        justify-content: space-between;
        align-items: center;
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
</style>