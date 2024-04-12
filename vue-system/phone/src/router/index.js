import Vue from 'vue'
import VueRouter from 'vue-router'
import RegisterPhone from '@/views/phoneui/volunteer/RegisterPhone.vue'
import HomePhone from '@/views/phoneui/volunteer/HomePhone.vue'
import LoginPhone from '@/views/phoneui/volunteer/LoginPhone.vue'
import InfoOfUserPhone from '@/views/phoneui/volunteer/InfoOfUserPhone.vue'
import AddActivityPhone from '@/views/phoneui/volunteer/AddActivityPhone.vue'
import InfoPhone from '@/views/phoneui/volunteer/InfoPhone.vue'
import TargetPage from '@/views/phoneui/volunteer/TargetPage.vue'
import ActivityOfUser from '@/views/phoneui/volunteer/ActivityOfUser.vue'
import RegisteredActivity from '@/views/phoneui/volunteer/RegisteredActivity.vue'
import SignInUser from '@/views/phoneui/volunteer/SignInUser.vue'
import HomeOld from '@/views/phoneui/old/HomeOld.vue'
import ActivityOld from '@/views/phoneui/old/ActivityOld.vue'
import AddActivityOld from '@/views/phoneui/old/AddActivityOld.vue'
import LocationGet from '@/views/phoneui/old/LocationGet.vue'
import GetInfoActivity from '@/views/phoneui/old/GetInfoActivity.vue'
import EndAddActivity from '@/views/phoneui/old/EndAddActivity.vue'
import ServerOld from '@/views/phoneui/old/ServerOld.vue'
import ArtificialOld from '@/views/phoneui/old/ArtificialOld.vue'
import IdActivityOld from '@/views/phoneui/old/IdActivityOld.vue'
import DashOld from '@/views/phoneui/old/DashOld.vue'
import CoinInfo from '@/views/phoneui/CoinInfo.vue'
import DashPhone from '@/views/phoneui/volunteer/DashPhone.vue'
import { requireAuth, loginrequireAuth } from '../utils/permission'

Vue.use(VueRouter)

const routes = [
  // 登录
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
    path: '*',// 未定义路径
    component: () => import('@/views/phoneui/404'),
    hidden: true
  },
  // 志愿者
  {
    path: '/dashPhone',
    name: 'DashPhone',
    component: DashPhone,
    // 使用 requireAuth 路由守卫检查 token 是否存在
    beforeEnter: requireAuth,
    children: [
      {
        path: '/homePhone',
        name: 'HomePhone',
        component: HomePhone,
        // 使用 requireAuth 路由守卫检查 token 是否存在
        beforeEnter: requireAuth,
      },
      {
        path: '/addActivityPhone',
        name: 'AddActivityPhone',
        component: AddActivityPhone,
        beforeEnter: requireAuth,
      },
      {
        path: '/infoOfUserPhone',
        name: 'InfoOfUserPhone',
        component: InfoOfUserPhone,
        beforeEnter: requireAuth,
      },
    ]
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
  {
    path: '/registeredActivity',
    name: 'RegisteredActivity',
    component: RegisteredActivity,
    beforeEnter: requireAuth,
  },
  {
    path: '/signInUser',
    name: 'SignInUser',
    component: SignInUser,
    beforeEnter: requireAuth,
  },
  // 老人
  {
    path: '/dashOld',
    name: 'DashOld',
    component: DashOld,
    beforeEnter: requireAuth,
    children: [
      {
        path: '/homeOld',
        name: 'HomeOld',
        component: HomeOld,
        beforeEnter: requireAuth,
      },
      {
        path: '/serverOld',
        name: 'ServerOld',
        component: ServerOld,
        beforeEnter: requireAuth,
      },
      {
        path: '/artificialOld',
        name: 'ArtificialOld',
        component: ArtificialOld,
        beforeEnter: requireAuth,
      },
      {
        path: '/activityOld',
        name: 'ActivityOld',
        component: ActivityOld,
        beforeEnter: requireAuth,
      },
    ] 
  },
  // 添加活动
  {
    path: '/addActivityOld',
    name: 'AddActivityOld',
    component: AddActivityOld,
    beforeEnter: requireAuth,
    children: [
      {
        path: '/locationGet',
        name: 'LocationGet',
        component: LocationGet,
      },
      {
        path: '/getInfoActivity',
        name: 'GetInfoActivity',
        component: GetInfoActivity,
      },
      {
        path: '/endAddActivity',
        name: 'EndAddActivity',
        component: EndAddActivity,
      }
    ]
  },
  // 查看活动信息
  {
    path: '/idActivityOld',
    name: 'IdActivityOld',
    component: IdActivityOld,
    beforeEnter: requireAuth,
  },
  // 查看时间币信息
  {
    path: '/coinInfo',
    name: 'CoinInfo',
    component: CoinInfo,
    beforeEnter: requireAuth,
  },
]

const router = new VueRouter({
  routes
})

export default router