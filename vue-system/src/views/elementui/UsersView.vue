<template>
  <div>
    <el-table
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        fixed
        prop="date"
        label="日期"
        width="150">
      </el-table-column>
      <el-table-column
        prop="name"
        label="姓名"
        width="120">
      </el-table-column>
      <el-table-column
        prop="province"
        label="省份"
        width="120">
      </el-table-column>
      <el-table-column
        prop="city"
        label="市区"
        width="120">
      </el-table-column>
      <el-table-column
        prop="address"
        label="地址"
        width="300">
      </el-table-column>
      <el-table-column
        prop="zip"
        label="邮编"
        width="120">
      </el-table-column>
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
          <el-button type="text" size="small">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
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
</template>

<script>
import { Button, TableColumn, Table, Pagination } from 'element-ui';

export default {
  name: 'UsersView',
  components: {
    'el-button': Button,
    'el-table': Table,
    'el-table-column': TableColumn,
    'el-pagination': Pagination
  },
  data() {
    return {
      originalData: {
        date: '2016-05-02',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1518 弄',
        zip: 200333
      },
      pageSize: 5, // 每页显示的条目数量
      totalItems: 100, // 总条目数量
      currentPage: 1, // 当前页码
      tableData: [] // 表格数据
    };
  },
  mounted() {
    // 初始化时计算当前页的数据
    this.calculateCurrentPageData();
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
      const startIdx = (this.currentPage - 1) * this.pageSize;
      const endIdx = Math.min(startIdx + this.pageSize, this.totalItems);
      this.tableData = [];
      for (let i = startIdx; i < endIdx; i++) {
        this.tableData.push({ ...this.originalData });
      }
    },
    // 其他方法
  }
};
</script>


<style>
</style>