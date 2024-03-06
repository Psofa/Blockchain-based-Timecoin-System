<template>
   <el-container>
    <el-header class="first-header">
      <div class="header-left">
        <span class="header-logo"></span>
        <span style=" font-size: 20px; font-weight: bold;">System</span>
      </div>
      <div class="header-right">
        <!-- Add your header items here -->
        <el-dropdown>
          <span class="el-dropdown-link">
            <el-avatar icon="el-icon-user-solid"></el-avatar>
            管理员<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>个人中心</el-dropdown-item>
            <el-dropdown-item>
              <button @click="logout" 
              style="background: none;
                    border: none;
                    padding: 0;
                    cursor: pointer;">退出登录</button>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <el-container style="height: 835px; border: 1px solid #eee">
      <el-aside :width="asideWidth" style="background-color: #575656;">
        <el-menu
          default-active="1"
          class="el-menu-vertical-demo"
          background-color="#575656"
          text-color="#fff"
          active-text-color="#ffd04b"
          :collapse="isCollapse"
        >
          <router-link to="/homeView" class="RouterLink">
            <el-menu-item index="1">
              <i class="el-icon-s-home"></i>
              <span v-if="!isCollapse" slot="title">Home</span> 
            </el-menu-item>
          </router-link>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-setting"></i>
              <span v-if="!isCollapse" slot="title">系统管理</span>
            </template>
            <el-menu-item-group>
              <router-link to="/usersView" class="RouterLink">
                <el-menu-item>用户管理</el-menu-item>
              </router-link>
              <el-menu-item>选项2</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="3">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span v-if="!isCollapse" slot="title">Option 2</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="2-1">选项1</el-menu-item>
              <el-menu-item index="2-2">选项2</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item>
            <i class="el-icon-message"></i>
            <span v-if="!isCollapse" slot="title">Option 3</span>
          </el-menu-item>
        </el-menu>
        <div class="collapse-toggle" @click="toggleCollapse">
          <i class="el-icon-arrow-left" v-if="!isCollapse"></i>
          <i class="el-icon-arrow-right" v-if="isCollapse"></i>
        </div>
      </el-aside>
      
      <el-container>
        <el-header class="second-header">
          <div class="tabs-container">
            <el-tabs v-model="activeTab" @tab-remove="removeTab"  @tab-click="handleTabClick" type="card" closable>
              <el-tab-pane
                v-for="tab in openedTabs"
                :key="tab.path"
                :label="tab.title"
                :name="tab.path"
                closable>
              </el-tab-pane>
            </el-tabs>
          </div>
          <div class="user-settings">
            <el-dropdown>
              <i class="el-icon-setting" style="margin-right: 15px"></i>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>查看</el-dropdown-item>
                <el-dropdown-item>新增</el-dropdown-item>
                <el-dropdown-item>删除</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <span>王小虎</span>
          </div>
        </el-header>
        
        <el-main style="border: 1px solid #ccc;border-radius: 5px; /* 设置边框圆角 */">
          <router-view></router-view>  <!-- 使用 <router-view> 渲染整个应用程序的内容 -->
        </el-main>
        <el-footer style="background-color: rgb(243, 239, 239);height: 40px;"></el-footer>
      </el-container>
    </el-container>
   </el-container>
  
</template>
<style>
/* 设置header */
.first-header  {
  background-color: #409EFF; /* Adjust the color to match your design */
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
}

.second-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  text-align: right;
  background-color: white;
  color: #333;
  height: 30px;
  border: 1px solid #ccc; /* 设置边框为 1px 实线 */
  border-radius: 5px; /* 设置边框圆角 */
}

.tabs-container {
  flex-grow: 1; /* 让标签部分占据大部分空间 */
}

.user-settings {
  display: flex;
  align-items: center; /* 确保王小虎部分垂直居中 */
}

.header-left {
  display: flex;
  align-items: center;
}

.header-logo {
  background-image: url('../../assets/logo.png'); /* 替换为图像路径 */
  background-size: contain; /* 控制图像大小，可以使用 cover、contain 或具体的尺寸值 */
  background-repeat: no-repeat; /* 防止图像重复 */
  display: inline-block; /* 设置为内联块元素，以便可以设置宽度和高度 */
  width: 40px; /* 图像宽度 */
  height: 30px; /* 图像高度 */
 
}

.header-right {
  display: flex;
  align-items: center;
}

.el-dropdown-link {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.el-avatar {
  margin-right: 5px;
}

/* 设置sider */
.collapse-toggle {
  position: absolute;
  bottom: 0px; /* 距离底部的距离 */
  left: 50%; /* 水平居中 */
  transform: translateX(-50%); /* 结合 left 50% 使用，确保完全居中 */
  width: 400px;
  height: 40px;
  background-color: #4f4c4c;
  color: white;
  text-align: center;
  line-height: 40px;
  cursor: pointer;
}

.el-aside {
  position: relative; /* 添加这一行 */
  transition: width .3s;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}

.el-menu--collapse {
  width: 64px;
  min-height: 400px;
}

/* 设置链接 */
.RouterLink{
  color: white; /* 设置字体颜色为白色 */
  text-decoration: none; /* 移除下划线 */
 
}
</style>

<script>
import { 
    Container, Aside,Menu, Submenu, MenuItemGroup, MenuItem, Header,
    Main, Dropdown, DropdownMenu, DropdownItem,Footer,Avatar,Tabs,TabPane
} from 'element-ui';
import request from '@/utils/request';

export default {
    name: 'IndexView',
    components: {
        'el-container': Container,
        'el-header': Header,
        'el-main': Main,
        'el-aside': Aside,
        'el-menu': Menu,
        'el-submenu': Submenu,
        'el-menu-item-group': MenuItemGroup,
        'el-menu-item': MenuItem,
        'el-dropdown': Dropdown,
        'el-dropdown-menu': DropdownMenu,
        'el-dropdown-item': DropdownItem,
        'el-footer': Footer,
        'el-avatar': Avatar,
        'el-tab-pane': TabPane,
        'el-tabs': Tabs,
    },
    data() {

      return {
          // 折叠
          isCollapse: false,
          // 打开的标签页列表
          openedTabs: [
            { title: 'Home', path: '/homeView', closable: false }, // Home标签默认打开且不可关闭
          ],
          activeTab: '/homeView',
          
      }
    },
    created() {
      // 在页面加载时调用获取个人信息的方法
      this.fetchUserInfo();
      this.initializeTabFromCurrentRoute();
    },
    watch: {
      '$route'(to) {
        let tabExists = this.openedTabs.some(tab => tab.path === to.path);
        if (!tabExists) {
          this.openedTabs.push({
            title: to.name, // 假设路由名称用作标签标题
            path: to.path,
            closable: true,
          });
        }
        this.activeTab = to.path;
      },
    },
    computed: {
      
      asideWidth() {
        return this.isCollapse ? '64px' : '200px';
      },

    },
    methods: {
      // 获取个人信息
      fetchUserInfo() {
        // 使用 Axios 实例发送请求获取个人信息
        request.get('/info')
          .then(response => {
            console.log(response.data); // 打印返回的数据
          })
          .catch(error => {
            console.error('获取个人信息失败:', error);
          });
      },
      // 折叠菜单
      toggleCollapse() {
        this.isCollapse = !this.isCollapse; // 切换折叠状态
      },
      logout() {
        // 执行退出登录的操作，清除本地存储中的 token
        localStorage.removeItem('token');
        
        // 可选的，执行其他清理工作
        
        // 重定向到登录页面
        this.$router.push('/');
      },
      removeTab(targetPath) {
        const targetTab = this.openedTabs.find(tab => tab.path === targetPath);
        // 如果标签不可关闭，则直接返回
        if (targetTab && !targetTab.closable) {
          return;
        }

        let nextTab = null;
        const currentIndex = this.openedTabs.findIndex(tab => tab.path === targetPath);
        if (currentIndex !== -1) {
          // 查找下一个要激活的标签
          nextTab = this.openedTabs[currentIndex + 1] || this.openedTabs[currentIndex - 1];
          // 从数组中移除当前标签
          this.openedTabs.splice(currentIndex, 1);
        }

        // 如果当前标签是激活状态，或者移除后没有标签了，则尝试激活其他标签
        if (this.activeTab === targetPath || this.openedTabs.length === 0) {
          if (nextTab) {
            this.activeTab = nextTab.path;
            this.$router.push(nextTab.path).catch(err => {
              if (err.name !== 'NavigationDuplicated') {
                throw err;
              }
            });
          } else {
            // 如果没有其他标签可以激活，可以选择重定向到一个默认页面，比如首页
            this.$router.push('/defaultPage').catch(err => {
              if (err.name !== 'NavigationDuplicated') {
                throw err;
              }
            });
          }
        }
      },
      handleTabClick(tab) {
        if (this.$route.path !== tab.name) {
          this.$router.push(tab.name).catch(err => {
            if (err.name !== 'NavigationDuplicated') {
              console.error(err);
            }
          });
        }
      },
      // 刷新之后变化
      initializeTabFromCurrentRoute() {
        const currentPath = this.$route.path;
        const currentRouteName = this.$route.name;

        // 如果当前路由已经是打开的标签之一，则不执行任何操作
        const tabExists = this.openedTabs.some(tab => tab.path === currentPath);
        if (!tabExists) {
          // 除非是默认的 homeView 路由，否则添加到 openedTabs
          if (currentPath !== '/homeView') {
            this.openedTabs.push({
              title: currentRouteName || 'No Name', // 使用路由名称或备用标题
              path: currentPath,
              closable: true, // 除 homeView 外的标签应该是可关闭的
            });
          }
          this.activeTab = currentPath; // 更新当前激活的标签
        }
      },
  },

};
</script>