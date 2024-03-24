import Vue from 'vue'
import VueRouter from 'vue-router'
import RegisterPhone from '@/views/phoneui/RegisterPhone.vue'
import HomePhone from '@/views/phoneui/HomePhone.vue'
import LoginPhone from '@/views/phoneui/LoginPhone.vue'
import InfoOfUserPhone from '@/views/phoneui/InfoOfUserPhone.vue'
import AddActivityPhone from '@/views/phoneui/AddActivityPhone.vue'
import InfoPhone from '@/views/phoneui/InfoPhone.vue'
import TargetPage from '@/views/phoneui/TargetPage.vue'
import ActivityOfUser from '@/views/phoneui/activityOfUser.vue'
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
  {
    path: '/infoOfUserPhone',
    name: 'InfoOfUserPhone',
    component: InfoOfUserPhone,
    beforeEnter: requireAuth,
  },
  {
    path: '/addActivityPhone',
    name: 'AddActivityPhone',
    component: AddActivityPhone,
    beforeEnter: requireAuth,
  },
  {
    path: '/infoPhone',
    name: 'InfoPhone',
    component: InfoPhone,
    beforeEnter: requireAuth,
  },
  {
    path: '/targetPage',
    name: 'TargetPage',
    component: TargetPage,
    beforeEnter: requireAuth,
  },
  {
    path: '/activityOfUser',
    name: 'ActivityOfUser',
    component: ActivityOfUser,
    beforeEnter: requireAuth,
  },
  
]

const router = new VueRouter({
  routes
})

export default router