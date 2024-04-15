<template>
    <div class="pageBox">
        <img :src="$activityImagePath" class="image">
        <div class="content">
            <div>
                {{ form.title }}
            </div>
        </div>
        <div class="content">
            <el-form ref="form" :model="form" label-width="100px" style="width: 100%;" :rules="rules">
                <el-form-item label="活动标题">
                    <el-input v-model="form.title"  prefix-icon="el-icon-edit"></el-input>
                </el-form-item>
                <el-form-item label="活动名额">
                    <el-input v-model="form.quota"  prefix-icon="el-icon-user"></el-input>
                </el-form-item>
                <el-form-item label="剩余名额">
                    <el-input v-model="form.remain"  prefix-icon="el-icon-sell"></el-input>
                </el-form-item>
                <el-form-item label="报名截止时间">
                    <div class="block">
                        <el-date-picker
                            v-model="form.deadline"
                            type="datetime"
                            placeholder="选择日期时间"
                            align="right"
                            :picker-options="pickerOptionsofform"
                        >
                        </el-date-picker>
                    </div>
                </el-form-item>
                <el-form-item label="活动日期">
                    <el-date-picker type="date" v-model="form.date" style="width: 100%;" ></el-date-picker>
                </el-form-item>
                <el-form-item label="活动开始时间">
                    <el-input v-model="form.begin"  prefix-icon="el-icon-time"></el-input>
                </el-form-item>
                <el-form-item label="活动结束时间">
                    <el-input v-model="form.end"  prefix-icon="el-icon-time"></el-input>
                </el-form-item>
                <el-form-item label="活动地址">
                    <el-input v-model="form.address"  prefix-icon="el-icon-location"></el-input>
                </el-form-item>
                <el-form-item label="发布人电话">
                    <el-input v-model="form.phone"  prefix-icon="el-icon-phone"></el-input>
                </el-form-item>
                <el-form-item label="活动描述">
                    <el-input type="textarea" v-model="form.description" ></el-input>
                </el-form-item>
                <el-form-item label="活动状态">
                    <el-input v-model="statusLabel"  prefix-icon="el-icon-info"></el-input>
                </el-form-item>
                <el-form-item label="活动建议">
                    <el-input type="textarea" readonly v-model="form.message"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-buttom style="margin-right: 10px;" @click="handleCancel">取 消</el-buttom>
                    <el-buttom type="primary" @click="onSubmitForm">确 定</el-buttom>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
  
<script>
import request from '@/utils/request';
import { format } from 'date-fns-tz';

export default {
    name: 'InfoActivityOld',
    data() {
        return {
            form: {},
            id: null,
            pageSize: 1,
            currentPage: 1,
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
            rules: {
                title: [{ required: true, message: '请输入活动标题', trigger: 'blur' }],
                quota: [{ required: true, message: '请输入活动名额', trigger: 'blur' }],
                deadline: [{ required: true, message: '请选择报名截止时间', trigger: 'change' }],
                date: [{ required: true, message: '请选择活动日期', trigger: 'change' }],
                begin: [{ required: true, message: '请输入活动开始时间', trigger: 'blur' }],
                end: [{ required: true, message: '请输入活动结束时间', trigger: 'blur' }],
                phone: [{ required: true, message: '请输入发布人电话', trigger: 'blur' }],
                description: [{ required: true, message: '请输入活动描述', trigger: 'blur' }],
            }
        };
    },
    created() {
        // 从查询参数中获取数据
        this.id = parseInt(this.$route.query.id);
        this.search();
    },
    computed: {
        statusLabel() {
            const selectedOption = this.options.find(option => option.value === this.form.status);
            return selectedOption ? selectedOption.label : '未知状态';
        },
    },
    methods: {
        search() {
            // 创建 URLSearchParams 对象
            const params = new URLSearchParams();
            // 添加搜索条件到 URLSearchParams 对象中
            params.append('pageSize', this.pageSize);
            params.append('page', this.currentPage);
            params.append('id', this.id);
            // 将 URLSearchParams 对象转换为查询字符串
            const queryString = params.toString();

            // 发起请求时将查询字符串添加到URL中
            request.get(`users/volold/activity?${queryString}`)
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
        handleCancel() {
            this.$router.push('/addActivityOld');
        },
        onSubmitForm() {
            this.$refs.form.validate(valid => {
                if (valid) {
                // 格式化时间
                const formatTimeString = (dateString) => {
                    if (!dateString) return '';
                    let hours = dateString.getHours().toString().padStart(2, '0');
                    let minutes = dateString.getMinutes().toString().padStart(2, '0');
                    let seconds = dateString.getSeconds().toString().padStart(2, '0');
                    let formattedTime = `${hours}:${minutes}:${seconds}`;
                    return formattedTime;
                };
                // 格式化日期
                const formatDateString = (dateString) => {
                    if (!dateString) return '';
                    const date = new Date(dateString);
                    const year = date.getFullYear();
                    const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1;
                    const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
                    return `${year}-${month}-${day}`;
                };
                const data = this.form;
                data['status'] = 5;
                data.begin = formatTimeString(data.begin);
                data.end = formatTimeString(data.end);
                data.date = formatDateString(data.date);
                // 格式化截止时间
                data.deadline = format(data.deadline, "yyyy-MM-dd'T'HH:mm:ss", { timeZone: 'Asia/BeiJing' });
                
                request.get(`/users/old`,data)
                    .then(response => {
                        if (response.code === 1) {
                            this.$message.success(response.msg);
                        } else {
                            this.$message.error(response.msg);
                        }
                    })
                    .catch(error => {
                        console.error('修改失败:', error);
                    });
                }
            });
        },
    }
}
</script>

<style lang="scss" scoped>
.pageBox {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  padding: 10px;
  .image {
    width: 100%;
    height: auto;
  }
  .content{
    margin: 10px;
    backdrop-filter: blur(10px);
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    height: auto;
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 20px;
  }

}
</style>