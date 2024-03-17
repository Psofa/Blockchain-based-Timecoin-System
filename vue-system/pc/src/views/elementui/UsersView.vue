<template>
  <div class="container">
    <el-dialog
      title="编辑"
      :visible.sync="dialogVisible"
      :before-close="handleClose"
      width="45%"
      :append-to-body="true"
      :modal-append-to-body="false">
      <el-form ref="form" :model="form" v-if="isEdit">
        <el-form-item label="id">
          <el-input v-model="form.id"></el-input>
        </el-form-item>
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
          {{ roleMap[form.role] || '未知' }}
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
        <el-button type="primary" @click="editUser" v-if="isEdit">提 交</el-button>
      </div>
    </el-dialog>
    <div class="app-container">
      <div class="left">
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
        <!-- 搜索 -->
        <div class="search">
          <el-input type="text" v-model="usename" prefix-icon="el-icon-search" style="margin-right: 10px;width: auto;" placeholder="请输入用户名"></el-input>
          <el-input type="text" v-model="address" prefix-icon="el-icon-search" style="margin-right: 10px;width: auto;" placeholder="请输入地址"></el-input>
          <el-select v-model="role" clearable placeholder="请选择身份" style="width: auto;margin-right: 10px;">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
          <el-tag style="margin-right: 5px;">最小年龄</el-tag>
          <el-input-number v-model="minage" :min="0" prefix-icon="el-icon-search" style="margin-right: 10px;width: auto;" placeholder="请输入最小年龄"></el-input-number>
          <el-tag style="margin-right: 5px;">最大年龄</el-tag>
          <el-input-number v-model="maxage" :min="0" prefix-icon="el-icon-search" style="width: auto;" placeholder="请输入最大年龄"></el-input-number>
        </div>

        <div class="opeate-tools">
          <div class="left-tools">
            <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
            <el-button type="primary" icon="el-icon-refresh" @click="clearsRearch">重置</el-button>
          </div>
          <div class="right-tools">
            <el-button size="small" type="primary" icon="el-icon-circle-plus" @click="addUser">添加</el-button>
            <el-button size="small" type="primary" icon="el-icon-delete" @click="deleteUsrs">删除</el-button>
            <el-button size="small">excel导入</el-button>
            <el-button size="small">excel导出</el-button>
          </div>
        </div>
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
      // 搜索表单
      username: '',
      address: '',
      role:'',
      minage:0,
      maxage:100,
      // 表格数据
      originalData: {},
      pageSize: 5, // 每页显示的条目数量
      totalItems: 0, // 总条目数量
      currentPage: 1, // 当前页码
      tableData: [], // 存储员工列表数据
      // 表单
      dialogVisible: false,
      isEdit: false,
      roleMap: {
        1: '老人',
        2: '志愿者',
        3: '管理员'
      },
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
      // 重新搜索获取对应页的数据
      this.search();
    },
    handleSizeChange(newSize) {
      // 更新页面大小
      this.pageSize = newSize;
      // 重新搜索获取对应页的数据
      this.search();
    },
    search() {
      // 创建 URLSearchParams 对象
      const params = new URLSearchParams();

      // 将搜索数据添加到 URLSearchParams 对象中
      params.append('username', this.username);
      params.append('address', this.address);
      params.append('role', this.role);
      params.append('minAge', this.minage);
      params.append('maxAge', this.maxage);

      // 添加 pageSize 和 page 参数
      params.append('pageSize', this.pageSize);
      params.append('page', this.currentPage);

      // 将 URLSearchParams 对象转换为查询字符串
      const queryString = params.toString();
      console.log(queryString);
      // 发起请求时将查询字符串添加到URL中
      request.get(`/administrator/users?${queryString}`)
        .then(response => {
          if (response.code === 1) {
            this.totalItems = response.data.total;
            this.originalData = response.data.rows;
            this.tableData = [];
            // 合并原始数据到 tableData 数组中
            this.tableData = [...this.tableData, ...this.originalData];
            this.$message.success("数据获取成功");
          } else {
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
    editUser(){
      const data = this.form;
      request.put('/administrator/users', data)
        .then(response => {
          if (response.code === 1) {
            this.dialogVisible = false;
            this.$message.success(response.msg);
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
    addUser() {
      // 使用 $router.push() 方法进行路由跳转
      this.$router.push('/addUserView');
    },
    // 删除
    deleteUsrs(){
      const ids = this.selectedIds.join(',');
      if(ids === ''){
        this.$message.error("请选择要删除的用户");
        return;
      }
      request.delete(`/administrator/users/${this.selectedIds.join(',')}`)
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
    // 表格选择
    handleSelectionChange(selection) {
      this.selectedIds = selection.map(item => item.id);
    },
    clearsRearch(){
      this.username ='';
      this.address = '';
      this.role='';
      this.minage=18;
      this.maxage=100;
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
  }
  .right {
    padding: 20px;
    display: flex;
    align-items: center;
    flex-direction: column;
    .search {
      padding: 10px;
      display: flex;
      align-items: center;
    }
    .opeate-tools {
      margin:15px;
      width: 98%;
      display: flex;
      align-items: center;
      justify-content: space-between;
      .left-tools {
        display: flex;
      }

      .right-tools {
        display: flex;
      }
    }
    
  }
}

</style>
