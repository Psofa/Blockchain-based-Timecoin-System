  <template>
    <div class="system-info">
      <div style="display: flex;
        justify-content: center;
        align-items: center;">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
            <span>CPU</span>
            </div>
            <div>
            <el-table :data="cpuData" style="width: 100%">
                <el-table-column prop="name" label="属性"></el-table-column>
                <el-table-column prop="value" label="值"></el-table-column>
            </el-table>
            </div>
        </el-card>
    
        <el-card class="box-card">
            <div slot="header" class="clearfix">
            <span>内存</span>
            </div>
            <div>
            <el-table :data="memoryData" style="width: 100%">
                <el-table-column prop="name" label="属性"></el-table-column>
                <el-table-column prop="value" label="值"></el-table-column>
            </el-table>
            </div>
        </el-card>
      </div>
      <div>
        <el-card class="box-card" style="width:1240px">
            <div slot="header" class="clearfix">
            <span>服务器信息</span>
            </div>
            <div>
            <el-descriptions title="" :column="2" border>
                <el-descriptions-item label="服务器名称">yangz</el-descriptions-item>
                <el-descriptions-item label="服务器IP">172.31.187.226</el-descriptions-item>
                <el-descriptions-item label="操作系统">Windows Server 2016</el-descriptions-item>
                <el-descriptions-item label="系统架构">amd64</el-descriptions-item>
            </el-descriptions>
            </div>
        </el-card>
        <el-card class="box-card" style="width:1240px">
            <div slot="header" class="clearfix">
                <span>磁盘状态</span>
                <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
            </div>
            <div>
                <el-table :data="tableData" style="width: 100%">
                <el-table-column prop="磁盘符号" label="磁盘符号" width="180"></el-table-column>
                <el-table-column prop="文件系统" label="文件系统" width="180"></el-table-column>
                <el-table-column prop="磁盘类型" label="磁盘类型"></el-table-column>
                <el-table-column prop="总大小" label="总大小"></el-table-column>
                <el-table-column prop="可用大小" label="可用大小"></el-table-column>
                <el-table-column prop="已用大小" label="已用大小"></el-table-column>
                <el-table-column prop="已用百分比" label="已用百分比"></el-table-column>
                </el-table>
            </div>
        </el-card>
      </div>
      
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        cpuData: [
          { name: '核心数', value: 2 },
          { name: '用户使用率', value: '3.1%' },
          { name: '系统使用率', value: '1.55%' },
          { name: '空闲率', value: '95.35%' },
        ],
        memoryData: [
          { name: '总内存', value: '1.92G' },
          { name: '已用内存', value: '1.48G' },
          { name: '剩余内存', value: '0.43G' },
          { name: '使用率', value: '77.76%' },
        ],
        tableData: [{
            磁盘符号: 'C:',
            文件系统: 'NTFS',
            磁盘类型: '本地固定磁盘 (C:)',
            总大小: '40.0 GB',
            可用大小: '19.9 GB',
            已用大小: '20.1 GB',
            已用百分比: '50.24%',
        }],
      };
    },
  };
  </script>
  
  <style scoped>
  .system-info {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  
  .box-card {
    width: 600px;
    margin: 20px;
  }
  </style>