import Vue from 'vue'
import VueRouter from 'vue-router'
import RegisterPhone from '@/views/phoneui/RegisterPhone.vue'
import HomePhone from '@/views/phoneui/HomePhone.vue'
import LoginPhone from '@/views/phoneui/LoginPhone.vue'
import { requireAuth, loginrequireAuth } from '../utils/permission'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'LoginPhone',
    component: LoginPhone,
    beforeEnter: loginrequireAuth,
  },
  {
    path: '/registerPhone',
    name: 'RegisterPhone',
    component: RegisterPhone
  },
  {
    path: '/homePhone',
    name: 'HomePhone',
    component: HomePhone,
    // 使用 requireAuth 路由守卫检查 token 是否存在
    beforeEnter: requireAuth,
  },
]

const router = new VueRouter({
  routes
})

export default router