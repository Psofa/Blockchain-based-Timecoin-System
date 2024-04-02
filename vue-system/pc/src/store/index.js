import Vue from 'vue';
import Vuex from 'vuex';
import { getToken, setToken, removeToken} from '@/utils/auth';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    token: getToken(),
  },
  getters: {
    getToken: (state) => state.token,
  },
  mutations: {
    setToken(state, token) {
      state.token = token;
      //同步到缓存
      setToken(token);
    },
    removeToken(state) {
      // 删除Vuex的token
      state.token = null
      removeToken()
    },
  },
  actions: {
    // 退出登录的action
    logout(context) {
      context.commit('removeToken') // 删除token
    },  
  },
  modules: {},
});
