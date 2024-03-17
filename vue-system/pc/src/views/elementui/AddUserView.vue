<template>
    <div>
        <el-form ref="form" :model="form">
            <el-form-item label="用户名">
            <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="用户类型">
            <el-select v-model="form.role" clearable placeholder="请选择" style="margin-right: 20px;">
                <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
                </el-option>
            </el-select>
            </el-form-item>
            <el-form-item label="邮箱">
            <el-input v-model="form.email"></el-input>
            </el-form-item>
            <el-form-item label="年龄">
            <el-input-number v-model="form.age" :min="0"></el-input-number>
            </el-form-item>
            <el-form-item label="电话">
            <el-input v-model="form.phone"></el-input>
            </el-form-item>
            <el-form-item label="地址">
            <el-input v-model="form.address"></el-input>
            </el-form-item>
            <el-form-item label="姓名">
            <el-input v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="密码">
            <el-input v-model="form.password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码">
            <el-input v-model="form.confirmPassword"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="footer">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="addUser">提 交</el-button>
        </div>
    </div>
</template>

<script>
import request from '@/utils/request';

export default {
  name: 'AddUserView',
  data() {
    return {
      form: {}, // 定义 form 对象
      options: [
        { value: 1, label: '老人' },
        { value: 2, label: '志愿者' },
        { value: 3, label: '管理员' },
      ],
    };
  },
  methods: {
    addUser() {
      const data = this.form;
      request.post('/administrator/users', data)
        .then(response => {
          if (response.code === 1) {
            this.dialogVisible = false;
            this.$message.success(response.msg);
            this.form = {};
          }
          else {
            this.$message.error(response.msg);
          }
        })
        .catch(error => {
          console.error('添加用户失败:', error);
        });
    },
    handleCancel() {
        // 使用 $router.push() 方法进行路由跳转
        this.$router.push('/usersView');
    }
  }
}
</script>

<style lang="scss" scoped>
.el-form{
  width: 40%;
}
.footer{
  width: 40%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 20px;
}
</style>