// 这里假设你的登录页面路由为 '/'
const LOGIN_PAGE_URL = '/';
import store from '@/store'

// 检查是否存在 token 的函数
function checkToken() {
    const token = store.getters.getToken; 
    return !!token; // 如果 token 存在返回 true，否则返回 false
}

export function requireAuth(to, from, next) {
    if (!checkToken()) {
        // 如果 token 不存在，则重定向到登录页面
        next({
            path: LOGIN_PAGE_URL,
        });
    } 
    else if(checkToken()){
        // 如果 token 存在，则允许访问当前路由
        next();
    }
}

export function loginrequireAuth(to, from, next) {
    // 如果存在 token 并且目标路径是根路径并且不是登录状态，则重定向
    if (checkToken() && to.path === '/') {
        next('/homePhone');
    } else {
        next();
    }
}