<template>
  <div class="container">
    <el-dialog
      title="添加用户"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      width="45%"
      :append-to-body="true"
      :modal-append-to-body="false">
      <el-form ref="form" :model="form" v-if="isEdit">
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
      <el-descriptions title="用户信息" :column="2" border v-model="form" v-if="!isEdit">
        <!-- 用户名 -->
        <el-descriptions-item label="用户名">
          <template slot="label">
            <i class="el-icon-user"></i> 用户名
          </template>
          {{ form.username }}
        </el-descriptions-item>

        <!-- 用户类型 -->
        <el-descriptions-item label="用户类型">
          <template slot="label">
            <i class="el-icon-s-custom"></i> 用户类型
          </template>
          {{ form.role }}
        </el-descriptions-item>

        <!-- 邮箱 -->
        <el-descriptions-item label="邮箱">
          <template slot="label">
            <i class="el-icon-message"></i> 邮箱
          </template>
          {{ form.email }}
        </el-descriptions-item>

        <!-- 年龄 -->
        <el-descriptions-item label="年龄">
          <template slot="label">
            <i class="el-icon-time"></i> 年龄
          </template>
          {{ form.age }}
        </el-descriptions-item>

        <!-- 电话 -->
        <el-descriptions-item label="电话">
          <template slot="label">
            <i class="el-icon-phone"></i> 电话
          </template>
          {{ form.phone }}
        </el-descriptions-item>

        <!-- 地址 -->
        <el-descriptions-item label="地址">
          <template slot="label">
            <i class="el-icon-location"></i> 地址
          </template>
          {{ form.address }}
        </el-descriptions-item>

        <!-- 姓名 -->
        <el-descriptions-item label="姓名">
          <template slot="label">
            <i class="el-icon-user-solid"></i> 姓名
          </template>
          {{ form.name }}
        </el-descriptions-item>

        <!-- 密码 -->
        <el-descriptions-item label="密码">
          <template slot="label">
            <i class="el-icon-lock"></i> 密码
          </template>
          {{ form.password }}
        </el-descriptions-item>

        <!-- 确认密码 -->
        <el-descriptions-item label="确认密码">
          <template slot="label">
            <i class="el-icon-lock"></i> 确认密码
          </template>
          {{ form.confirmPassword }}
        </el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleCancel">取 消</el-button>
        <el-button type="primary" @click="addUser" v-if="isEdit">提 交</el-button>
      </div>
    </el-dialog>
    <div class="app-container">
      <div class="left">
        <!-- 搜索 -->
        <el-container class="search" v-model="searchData">
          <el-input type="text" v-model="searchData.usename" prefix-icon="el-icon-search" style="margin-bottom: 5px;" placeholder="请输入用户名"></el-input>
          <el-input type="text" v-model="searchData.address" prefix-icon="el-icon-search" style="margin-bottom: 5px;" placeholder="请输入地址"></el-input>
          <el-select v-model="searchData.role" clearable placeholder="请选择身份" style="width: 250px;">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-input-number v-model="searchData.minage" :min="0" prefix-icon="el-icon-search" style="margin-top: 5px; width: auto;" placeholder="请输入最小年龄"></el-input-number>
          <el-input-number v-model="searchData.maxage" :min="0" prefix-icon="el-icon-search" style="margin-top: 5px; margin-bottom: 10px; width: auto;" placeholder="请输入最大年龄"></el-input-number>
          <div style="margin-top: 5px; display: flex;">
            <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            <el-button type="primary" icon="el-icon-refresh" @click="clearsRearch">重置</el-button>
          </div>
        </el-container>
        <!-- 树形组件 -->
        <el-tree
          ref="deptTree"
          node-key="id"
          :data="depts"
          :props="defaultProps"
          default-expand-all
          :expand-on-click-node="false"
          highlight-current
          @current-change="selectNode"
        />
      </div>
      <div class="right">
        <el-row class="opeate-tools" type="flex" justify="end">
          <el-button size="small" type="primary" icon="el-icon-circle-plus" @click="dialogVisible = true">添加</el-button>
          <el-button size="small" type="primary" icon="el-icon-delete" @click="deleteUsrs">删除</el-button>
          <el-button size="small">excel导入</el-button>
          <el-button size="small">excel导出</el-button>
        </el-row>
        <!-- 表格组件 -->
        <el-table :data="tableData"
          style="width: 1100px;border: 1px solid #ccc;"
          show-selection @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="45"></el-table-column>
          <el-table-column fixed prop="id" label="用户id" width="80"/>
          <el-table-column prop="username" label="用户名" width="80"/>
          <el-table-column prop="password" label="密码" width="200"/>
          <el-table-column prop="role" label="用户类型"/>
          <el-table-column prop="email" label="邮箱" width="200"/>
          <el-table-column prop="age" label="年龄" sortable/>
          <el-table-column prop="phone" label="手机号" width="150"/>
          <el-table-column prop="address" label="地址" width="200"/>
          <el-table-column prop="name" label="姓名" width="80"/>
          <el-table-column prop="createTime" label="创建时间" sortable width="200"/>
          <el-table-column fixed="right" label="操作" width="200px">
            <template slot-scope="scope">
              <div style="display: flex; justify-content: center;">
                <el-button size="mini" @click="query(scope.row)">查看</el-button>
                <el-button size="mini" @click="edit(scope.row)">编辑</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页 -->
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
    </div>
  </div>
</template>

<script>
import request from '@/utils/request';

export default {
  name: 'UsersView',
  data() {
    return {
      depts: [], // 组织数据
      defaultProps: {
        label: 'name',
        children: 'children'
      },
      searchData: {}, // 搜索表单
      // 表格数据
      originalData: {},
      pageSize: 10, // 每页显示的条目数量
      totalItems: 0, // 总条目数量
      currentPage: 1, // 当前页码
      tableData: [], // 存储员工列表数据
      // 表单
      dialogVisible: false,
      isEdit: false,
      options: [
        { value: 1, label: '老人' },
        { value: 2, label: '志愿者' },
        { value: 3, label: '管理员' },
      ],
      form: {},

    }
  },
  mounted() {
    // 初始化时计算当前页的数据
    this.search();
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
    calculateCurrentPageData(){
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
    search(){
      // 在这个方法中获取搜索框中的数据
      const searchData = this.searchData;
      // 发起搜索请求
      request.get('/administrator/users',searchData)
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
    // 弹窗
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(() => {
          setTimeout(() => {
            this.isEdit = false;
          }, 150);
          done();
        })
        .catch(() => {});
    },
    handleCancel(){
      this.dialogVisible = false;
      setTimeout(() => {
        this.isEdit = false;
      }, 150);
    },
    query(rowData) {
      this.form = { ...rowData};
      // 打开
      this.dialogVisible = true;
    },
    edit(rowData){
      this.form = { ...rowData};
      this.isEdit = true;
      // 打开
      this.dialogVisible = true;
    },
    addUser(){
      const data = {
        username: this.form.username,
        email: this.form.email,
        phone: this.form.phone,
        role: this.form.role,
        password: this.form.password,
        age: this.form.age,
        name: this.form.name,
        address: this.form.address
      };
      request.post('/administrator/users', data)
        .then(response => {
          if (response.code === 1) {
            this.dialogVisible = false;
            this.$message.success("添加成功");
            this.search();
          }
          else {
            this.$message.error(response.msg);
          }
        })
        .catch(error => {
          console.error('添加用户失败:', error);
        })
        .finally(() => {
          setTimeout(() => {
            this.isEdit = false;
          }, 150); // 使用 setTimeout 延迟执行，确保关闭操作完成后再执行状态更新
        });
    },
    // 删除
    deleteUsrs(){
      const ids = this.selectedIds.join(',');
      if(ids === ''){
        this.$message.error("请选择要删除的用户");
        return;
      }
      request.delete('/administrator/users/${this.selectedIds.join(',')}', {
        ids: ids
      })
        .then(response => {
          if(response.code === 1){
            this.$message.success(response.msg);
            this.search();
          }
          else{
            this.$message.error(response.msg);
          }
        })
        .catch(error => {
          console.error('There was a problem with the request:', error);
        });
    },
    // 修改
    updateUser(id) {
      request.get('/administrator/users/' + id)
        .then(response => {
          if (response.code === 1) {
            this.form = response.data;
            this.dialogVisible = true;
          }
          else {
            this.$message.error(response.msg);
          }
        })
        .catch(error => {
          console.error('删除用户失败:', error);
        }
      );
    },
    // 表格选择
    handleSelectionChange(selection) {
      this.selectedIds = selection.map(item => item.id);
    },
    clearsRearch(){
      this.searchData = {};
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  backdrop-filter: blur(40px); /* 添加毛玻璃效果 */
  border-radius: 20px;
  display: flex;
  .left {
    width: 300px;
    padding: 20px;
    border-right: 1px solid #d0d0d3;

    .search {
      margin-bottom: 20px;
      padding: 10px;
      display: flex;
      align-items: center;
      flex-direction: column;
      backdrop-filter: blur(40px); /* 添加毛玻璃效果 */
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    }
  }
  .right {
    padding: 20px;
    .opeate-tools {
      margin:10px ;
    }
    
  }
}

</style>
