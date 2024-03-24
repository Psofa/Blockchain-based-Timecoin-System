import Vue from 'vue';
import Vuex from 'vuex';
import { getToken, setToken, removeToken} from '@/utils/auth';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: getToken(),
    userInfo: {},
  },
  getters: {
    getToken: (state) => state.token,
    getUserInfo: (state) => state.userInfo,
  },
  mutations: {
    setToken(state, token) {
      state.token = token;
      //同步到缓存
      setToken(token);
    },
    setUserInfo(state, userInfo) {
      state.userInfo = userInfo;
    },
    removeToken(state) {
      // 删除Vuex的token
      state.token = null
      removeToken()
    },
  },
  actions: {
    // 在这里定义异步操作，比如登录成功后保存用户信息到Vuex中
    saveUserInfo({ commit }, userInfo) {
      commit('setUserInfo', userInfo);
    },
    // 退出登录的action
    logout(context) {
      context.commit('removeToken') // 删除token
      context.commit('setUserInfo', {}) // 设置用户信息为空对象
    },  
  },
  modules: {},
});
