<template>
    <div>
        <el-form ref="form" :model="form" label-width="100px">
            <el-form-item label="活动标题">
                <el-input v-model="form.title"></el-input>
            </el-form-item>
                <el-form-item label="活动名额">
            <el-input-number v-model="form.quota" :min="1"></el-input-number>
            </el-form-item>
            <el-form-item label="报名截止时间">
                <div class="block">
                    <el-date-picker
                    v-model="form.deadline"
                    type="datetime"
                    placeholder="选择日期时间"
                    align="right"
                    :picker-options="pickerOptionsofform">
                    </el-date-picker>
                </div>
            </el-form-item>
            <el-form-item label="活动日期">
                <el-date-picker type="date" v-model="form.date" style="width: 100%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="活动开始时间">
                <el-input v-model="form.begin"></el-input>
            </el-form-item>
            <el-form-item label="活动结束时间">
                <el-input v-model="form.end"></el-input>
            </el-form-item>
            <el-form-item label="活动地址">
                <el-input v-model="form.address"></el-input>
            </el-form-item>
            <el-form-item label="老人ID">
                <el-input v-model="form.oldId"></el-input>
            </el-form-item>
            <el-form-item label="发布人电话">
                <el-input v-model="form.phone"></el-input>
            </el-form-item>
            <el-form-item label="活动描述">
                <el-input type="textarea" v-model="form.description"></el-input>
            </el-form-item>
            <el-form-item label="活动状态">
                <el-select v-model="form.status" clearable placeholder="请选择" style="margin-right: 20px;">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="管理员建议">
                <el-input type="textarea" v-model="form.message"></el-input>
            </el-form-item>
            <el-form-item style="display: flex;justify-content: space-between;align-items: center;">
                <el-button @click="handleCancel">取 消</el-button>
                <el-button type="primary" @click="onSubmitForm">提交</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import request from '@/utils/request';
import store from '@/store';

export default {
  name: 'AdminView',
  data() {
    return {
        // 日期表
        pickerOptionsofsearch: {
            disabledDate(time) {
            return time.getTime() > Date.now();
            },
            shortcuts: [{
            text: '今天',
            onClick(picker) {
                picker.$emit('pick', new Date());
            }
            }, {
            text: '昨天',
            onClick(picker) {
                const date = new Date();
                date.setTime(date.getTime() - 3600 * 1000 * 24);
                picker.$emit('pick', date);
            }
            }, {
            text: '一周前',
            onClick(picker) {
                const date = new Date();
                date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                picker.$emit('pick', date);
            }
            }]
        },
        // 选择器
        options: [
            { value: 1, label: '未审核活动' },
            { value: 2, label: '审核同意活动' },
            { value: 3, label: '审核不同意活动' },
            { value: 4, label: '过期活动' },
        ],
        form: {},
        pickerOptionsofform: {
            shortcuts: [{
            text: '今天',
            onClick(picker) {
                picker.$emit('pick', new Date());
            }
            }, {
            text: '昨天',
            onClick(picker) {
                const date = new Date();
                date.setTime(date.getTime() - 3600 * 1000 * 24);
                picker.$emit('pick', date);
            }
            }, {
            text: '一周前',
            onClick(picker) {
                const date = new Date();
                date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
                picker.$emit('pick', date);
            }
            }]
        },
    }
  },
  methods: {
    onSubmitForm(){
      // 正则表达式，用于匹配时分秒形式（HH:mm:ss）
      const timePattern = /^(?:[01]\d|2[0-3]):[0-5]\d:[0-5]\d$/;
      if(!timePattern.test(this.form.begin)){
        this.$message.warning("输入的活动开始时间格式错误");
        return;
      }
      if(!timePattern.test(this.form.end)){
        this.$message.warning("输入的活动结束时间格式错误");
        return;
      }
      // 在这个方法中获取搜索框中的数据
      const data = this.form;
      data['administratorId'] = store.getters.getUserInfo.id;
      console.log(data);
      request.post('/administrator',data)
        .then(response => {
          if(response.code === 1){
            this.$message.success(response.msg);
            this.form = {};
          }
          else{
            this.$message.error(response.msg);
          }
        })
        .catch(error => {
          console.error('There was a problem with the request:', error);
        });
    },
    handleCancel() {
        // 使用 $router.push() 方法进行路由跳转
        this.$router.push('/adminView');
    }
  }
}
</script>

<style lang="scss" scoped>
.el-form{
    width: 40%;
}
</style>