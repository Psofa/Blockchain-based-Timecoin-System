import Vue from 'vue'
import VueRouter from 'vue-router'
import LoginForm from '../views/elementui/LoginForm.vue'
import IndexView from '../views/elementui/IndexView.vue'
import HomeView from '../views/elementui/HomeView.vue'
import UsersView from '../views/elementui/UsersView.vue'
import { requireAuth } from '../utils/auth.js'; // 导入 requireAuth 函数

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'LoginForm',
    component: LoginForm
  },
  {
    path: '/indexView',
    name: 'IndexView',
    component: IndexView,
    // 使用 requireAuth 路由守卫检查 token 是否存在
    beforeEnter: requireAuth,
    children: [
      {
        path: '/homeView',
        name: 'HomeView',
        component: HomeView,
      },
      {
        path: '/usersView',
        name: 'UsersView',
        component: UsersView,
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
