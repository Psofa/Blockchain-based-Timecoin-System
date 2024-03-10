<template>
  <div>
    <el-table :data="tableData" style="width: 100%" show-selection @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="name" label="Name"></el-table-column>
      <el-table-column prop="age" label="Age"></el-table-column>
      <!-- 其他列 -->
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tableData: [
        { id: 1, name: 'John', age: 30 },
        { id: 2, name: 'Doe', age: 25 },
        // 其他数据
      ],
      selectedItems: [] // 用于存储选中的项
    };
  },
  methods: {
    handleSelectionChange(selection) {
      this.selectedItems = selection;
    }
  }
};
</script>
