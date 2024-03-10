<template>
  <div>
    <!-- 抽屉 -->
    <el-drawer
      title="活动信息"
      :visible.sync="drawer"
      direction="ltr">
      <el-form ref="form" :model="form" :disabled="formDisabled" label-width="100px">
        <el-form-item label="活动ID">
          <el-input v-model="form.id" :disabled='isFormIdDisabled'></el-input>
        </el-form-item>
        <el-form-item label="活动标题">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="活动名额">
          <el-input v-model="form.quota"></el-input>
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
        <el-form-item label="管理员ID">
          <el-input v-model="form.administratorId" :disabled=true></el-input>
        </el-form-item>
        <el-form-item label="活动创建时间">
          <div class="block">
            <el-date-picker
              v-model="form.createTime"
              type="datetime"
              placeholder="选择日期时间"
              align="right"
              :picker-options="pickerOptionsofform"
              :disabled=true>
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="活动更新时间">
          <div class="block">
            <el-date-picker
              v-model="form.updateTime"
              type="datetime"
              placeholder="选择日期时间"
              align="right"
              :picker-options="pickerOptionsofform"
              :disabled=true>
            </el-date-picker>
          </div>
        </el-form-item>
        <el-form-item label="活动剩余名额">
          <el-input v-model="form.remain" :disabled='isFormRemainDisabled'></el-input>
        </el-form-item>
        <el-form-item label="管理员建议">
          <el-input type="textarea" v-model="form.message"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmitForm">提交</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>
    <!-- 查询 -->
    <div class="search">
      <div class="searchContainer">
        <el-tag style="margin-right: 5px; margin-left: 10px;">活动标题</el-tag>
        <el-input type="text" v-model="searchTitle" style="vertical-align: middle;margin-right: 20px;"></el-input>
        <el-tag style="margin-right: 5px;">活动地点</el-tag>
        <el-input type="text" v-model="searchAddress" style="vertical-align: middle;margin-right: 20px;"></el-input>
        <div class="block">
          <el-date-picker
            v-model="searchDate"
            align="right"
            type="date"
            placeholder="活动日期"
            :picker-options="pickerOptionsofsearch"
            style="vertical-align: middle;margin-right: 20px;">
          </el-date-picker>
        </div>
      </div>
      <div class="searchContainer">
        <el-tag style="margin-right: 5px; margin-left: 10px;">活动开始时间</el-tag>
        <el-time-picker
          v-model="searchBegin"
          style="vertical-align: middle;margin-right: 20px;">
        </el-time-picker>
        <el-tag style="margin-right: 5px;">活动结束时间</el-tag>
        <el-time-picker
          v-model="searchEnd"
          style="vertical-align: middle;margin-right: 20px;">
        </el-time-picker>
        <el-tag style="margin-right: 5px;">活动状态</el-tag>
        <el-select v-model="searchStatus" clearable placeholder="请选择" style="margin-right: 20px;">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-button type="primary" round @click="search" style="height: 35px; margin-left: auto;">搜索</el-button>
        <el-button type="primary" round @click="clearSearch" style="height: 35px;">重置</el-button>
        <el-button type="primary" round @click="addTable" style="height: 35px;">添加</el-button>
        <el-button type="primary" round @click="deleteTable" style="height: 35px; margin-right: 20px;">删除</el-button>
      </div>
    </div>
    <el-table
      :data="tableData"
      border
      style="width: 100%;border: 1px solid #ccc;"
      show-selection @selection-change="handleSelectionChange">
      <!-- 表格列定义 -->
      <el-table-column type="selection" width="45"></el-table-column>
      <el-table-column fixed prop="id" label="id" width="100"></el-table-column>
      <el-table-column prop="title" label="活动标题" width="100"></el-table-column>
      <el-table-column prop="quota" label="活动名额" width="100"></el-table-column>
      <el-table-column prop="date" label="活动时期" width="100"></el-table-column>
      <el-table-column prop="begin" label="活动开始时间" width="150"></el-table-column>
      <el-table-column prop="end" label="活动结束时间" width="150"></el-table-column>
      <el-table-column prop="address" label="地址" width="300"></el-table-column>
      <el-table-column prop="oldId" label="老人ID" width="100"></el-table-column>
      <el-table-column prop="phone" label="发布人电话" width="100"></el-table-column>
      <el-table-column prop="status" label="活动状态" width="100">
        <template slot-scope="scope">
          <el-tag :type="getTagType(scope.row.status)">
            {{ getTagText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="活动活动描述" width="150">
        <template slot-scope="scope">
          <!-- 点击按钮触发打开弹窗事件 -->
          <el-button type="text" @click="openDialog(scope.row.description)">点击查看描述</el-button>
        </template>
      </el-table-column>
      <!-- 操作列 -->
      <el-table-column
        fixed="right"
        label="操作"
        width="180">
        <template slot-scope="scope">
          <div style="display: flex; justify-content: center;">
            <el-button size = "small" @click="queryForm(scope.row)" style="margin-right: 10px;">查看</el-button>
            <el-button size = "small" @click="editForm(scope.row)">编辑</el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <div class="pagination-container">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20, 30]" 
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalItems">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { MessageBox } from 'element-ui';// 需要单独导入
import request from '@/utils/request';

export default {
  name: 'UsersView',
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
      // 搜索数据
      searchTitle: '', // 活动标题
      searchAddress: '', // 活动地点
      searchDate: '', // 活动日期
      searchBegin: '',
      searchEnd: '',
      searchStatus: '', // 活动状态
      // 表格数据
      originalData: {},
      pageSize: 5, // 每页显示的条目数量
      totalItems: 0, // 总条目数量
      currentPage: 1, // 当前页码
      tableData: [], // 表格数据
      selectedItems: [], // 用于存储选中的项
      // 表单
      drawer: false,
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
      formDisabled: true, // 禁用整个表单
      isFormIdDisabled: false,
      isFormRemainDisabled: false,
    };
  },
  created(){
    
  },
  mounted() {
    // 初始化时计算当前页的数据
    this.search();
    // 交表的时候使用
    this.isFormItemDisabled();
  },
  methods: {
    handleCurrentChange(newPage) {
      // 更新当前页码
      this.currentPage = newPage;
      // 重新计算当前页的数据
      this.calculateCurrentPageData();
    },
    handleSizeChange(newSize) {
      // 更新页面大小并重新计算当前页的数据
      this.pageSize = newSize;
      this.calculateCurrentPageData();
    },
    calculateCurrentPageData() {
      this.tableData = [];
      // 合并原始数据到 tableData 数组中
      this.tableData = [...this.tableData, ...this.originalData];
      const startIdx = (this.currentPage - 1) * this.pageSize;
      const endIdx = Math.min(startIdx + this.pageSize, this.totalItems);
      // 选择当前页的数据
      const currentPageData = this.tableData.slice(startIdx, endIdx);
      // 将当前页的数据赋值给 tableData 数组
      this.tableData = currentPageData;
    },
    openDialog(description) {
      // 打开弹窗显示活动描述
      MessageBox.alert(description, '活动描述', {
        confirmButtonText: '确定',
        type: 'info'
      });
    },
    search(){
      // 在这个方法中获取搜索框中的数据
      const searchData = {
        title: this.searchTitle,
        address: this.searchAddress,
        date: this.searchDate,
        begin: this.searchBegin,
        end: this.searchEnd,
        status: this.searchStatus,
        pageSize: this.pageSize,
        page: this.currentPage
      };
      request.get('/administrator',searchData)
        .then(response => {
          if (response.code === 1) {
            this.totalItems = response.data.total;
            this.originalData = response.data.rows;
            this.calculateCurrentPageData();
            this.$message.success("数据获取成功");
          }
          else {
            this.$message.error(response.msg);
          }
        })
        .catch(error => {
          console.error('获取个人信息失败:', error);
        });
    },
    clearSearch() {
      // 在这个方法中清空搜索框中的数据
      this.searchTitle = '';
      this.searchAddress = '';
      this.searchDate = '';
      this.searchBegin = '',
      this.searchEnd = '',
      this.searchStatus = '';
    },
    // 不同标签
    getTagType(status) {
      switch (status) {
        case 1:
          return 'warning';
        case 2:
          return 'success';
        case 3:
          return 'danger';
        case 4:
          return 'info';
        default:
          return '';
      }
    },
    getTagText(status) {
      switch (status) {
        case 1:
          return '未审核';
        case 2:
          return '审核通过';
        case 3:
          return '审核不通过';
        case 4:
          return '过期活动';
        default:
          return '';
      }
    },
    // 表单
    queryForm(rowData) {
      this.form = { ...rowData};
      // 打开抽屉
      this.drawer = true;
    },
    editForm(rowData){
      this.form = { ...rowData};
      this.formDisabled = false;
      // 打开抽屉
      this.drawer = true;
    },
    isFormItemDisabled(){
      // 检查表单项是否禁用
      return this.formDisabled;
    },
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
      const data = {};
      for (const key in this.form) {
        if (!this.isFormItemDisabled(key)) {
          data[key] = this.form[key];
        }
      }
      request.get('/administrator',data)
        .then(response => {
          if(response.code === 1){
            this.$message.success(response.msg);
          }
          else{
            this.$message.error(response.msg);
          }
        })
        .catch(error => {
          console.error('There was a problem with the request:', error);
        });
    },
    addTable(){
      this.isFormRemainDisabled=true;
      this.isFormIdDisabled=true;
      this.formDisabled = false;
      // 打开抽屉
      this.drawer = true;
    },
    // 表格选择
    handleSelectionChange(selection) {
      this.selectedIds = selection.map(item => item.id);
    },
    // 删除
    deleteTable(){
      request.delete('/administrator/${this.selectedIds.join(',')}', {
        ids: this.selectedIds
      })
        .then(response => {
          if(response.code === 1){
            this.$message.success(response.msg);
          }
          else{
            this.$message.error(response.msg);
          }
        })
        .catch(error => {
          console.error('There was a problem with the request:', error);
        });
    }
  }
};
</script>

<style>
.search{
  height: 110px;
  border: 1px solid #ccc;
  margin-bottom: 10px;
}
.searchContainer{
  height: 50px;
  display: flex;
  align-items: center;
  
}
.pagination-container {
  display: flex;
  justify-content: center;
}
.el-form-item {
  margin-right: 20px;
  margin-left: 20px;
}

</style>
