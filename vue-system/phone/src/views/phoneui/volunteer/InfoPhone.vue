<template>
    <div>
        <el-dialog
            title="个人信息"
            :visible.sync="dialogVisible"
            width="80%"
            :append-to-body="true"
            :modal-append-to-body="false">
            <el-descriptions :model="form" :column="1" border>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user"></i>
                        用户名
                    </template>
                    <el-input v-model="form.username" readonly></el-input>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-user-solid"></i>
                        姓名
                    </template>
                    <el-input v-model="form.name" readonly></el-input>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-message"></i>
                        邮箱
                    </template>
                    <el-input v-model="form.email" readonly></el-input>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-time"></i>
                        年龄
                    </template>
                    <el-input v-model="form.age" readonly></el-input>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-phone"></i>
                        电话
                    </template>
                    <el-input v-model="form.phone" readonly></el-input>
                </el-descriptions-item>
                <el-descriptions-item>
                    <template slot="label">
                        <i class="el-icon-location"></i>地址
                    </template>
                    <el-input v-model="form.address" readonly></el-input>
                </el-descriptions-item>
            </el-descriptions>
            <div  style="display: flex; justify-content: space-between; margin-top: 10px;">
                <el-button @click="handleCancel" round style="width: 100%;">取 消</el-button>
            </div>
        </el-dialog>
        <el-dialog
            title="修改头像"
            :visible.sync="dialogVisible1"
            width="80%"
            :append-to-body="true"
            :modal-append-to-body="false">
            <div class="avatar-wrapper" style="display: flex; justify-content: center;">
                <el-upload
                    class="avatar-uploader"
                    :action="baseUrl + '/info'"
                    :show-file-list="false"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload">
                    <img v-if="imageUrl" :src="imageUrl" class="avatar">
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
            </div>
            <div>
                <el-button @click="handleCancel" type="primary" round style="width: 100%;margin-top: 10px;">确 定</el-button>
            </div>
        </el-dialog>
        <div class="mainBox">
            <el-menu v-model="form" style="width: 100%;">
                <div @click="editAvatar">
                    <el-menu-item index="1">
                        <span>用户头像</span> 
                        <div>
                            <img v-if="form.image" class="avatar1" :src="form.image" alt="">
                            <span v-else class="username1">{{ form.username?.charAt(0) }}</span>
                            <i class="el-icon-arrow-right"></i>
                        </div>
                    </el-menu-item>
                </div>
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
    </div>
</template>

<script>
import { removeToken } from '@/utils/auth';
import request from '@/utils/request';

export default {
    name: 'InfoPhone',
    data() {
        return{
            baseUrl: "http://10.134.116.30:8080",
            form: {},
            dialogVisible: false,
            dialogVisible1: false,
            imageUrl: '',
        }
    },
    created() {
        this.search();
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
            this.dialogVisible1 = false;
        },
        query() {
            // 打开
            this.dialogVisible = true;
        },
        editAvatar() {
            // 打开
            this.dialogVisible1 = true;
        },
        handleAvatarSuccess(res) {
            const imageUrl = this.baseUrl + res.data; // 假设返回的响应中包含了文件的 URL 地址
            console.log(imageUrl);
            // 将文件 URL 赋值给 imageUrl
            this.imageUrl = imageUrl;
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            let image = new FormData();
            image.append('file', file); // 将文件添加到 FormData 中

            if (isJPG && isLt2M) {
                request.post(`/info`, image)
                    .then(response => {
                        if (response.code === 1) {
                            // 处理成功响应
                            this.handleAvatarSuccess(response);
                            this.$message.success('上传头像成功!');
                        } else {
                            this.$message.error(response.msg);
                        }
                    })
                    .catch(error => {
                        console.error('获取数据失败:', error);
                    });
            }
            return false;
        }
    }
}
</script>

<style lang="scss" scoped>
.avatar-wrapper {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #737576;
        width: 178px;
        height: 178px;
        line-height: 178px;
        text-align: center;
    }
    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
}
.mainBox{
    padding: 0px;
    border: none;
    .el-menu-item{
        display: flex;
        justify-content: space-between;
        align-items: center;
        .avatar1 {
            display: inline-block;
            width: 48px;
            height: 48px;
            border-radius: 12px;
            background-color: #d9d9d9;
            line-height: 48px;
            text-align: center;
        }
        .username1 {
            display: inline-block;
            width: 30px;
            height: 30px;
            text-align: center;
            line-height: 30px;
            border-radius: 50%;
            background: #04c9be;
            color: #fff;
            margin-right: 4px;
        }
    }
}
</style>