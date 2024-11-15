import Vue from 'vue'
import VueRouter from 'vue-router'
import IndexView from '../views/elementui/IndexView.vue'
import HomeView from '../views/elementui/HomeView.vue'
import UsersView from '../views/elementui/UsersView.vue'
import AdminView from '../views/elementui/AdminView.vue'
import LoginView from '../views/elementui/LoginView.vue'
import AddUserView from '../views/elementui/AddUserView.vue'
import AddActivityView from '@/views/elementui/AddActivityView.vue'
import SystemView from '@/views/elementui/SystemView.vue'
import CoinView from '@/views/elementui/CoinView.vue'

import { requireAuth, loginrequireAuth } from '../utils/permission'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'LoginView',
    component: LoginView,
    beforeEnter: loginrequireAuth,
  },
  {
    path: '*',// 未定义路径
    component: () => import('@/views/404'),
    hidden: true
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
        
      },
      {
        path: '/adminView',
        name: 'AdminView',
        component: AdminView,
        
      },
      {
        path: '/addUserView',
        name: 'AddUserView',
        component: AddUserView,
        
      },
      {
        path: '/addActivityView',
        name: 'AddActivityView',
        component: AddActivityView,
        
      },
      {
        path: '/systemView',
        name: 'SystemView',
        component: SystemView,
        
      },
      {
        path: '/coinView',
        name: 'CoinView',
        component: CoinView,
        
      },
    ]
  },
]

const router = new VueRouter({
  routes
})

export default router