// auth.js

// 这里假设你的登录页面路由为 '/login'
const LOGIN_PAGE_URL = '/';

// 检查是否存在 token 的函数
function checkToken() {
    const token = localStorage.getItem('token'); // 假设 token 存储在 localStorage 中
    return !!token; // 如果 token 存在返回 true，否则返回 false
}

// 路由守卫，用于检查 token 是否存在，不存在则重定向到登录页面
export function requireAuth(to, from, next) {
    if (!checkToken()) {
        // 如果 token 不存在，则重定向到登录页面
        next({
            path: LOGIN_PAGE_URL,
            // query: { redirect: to.fullPath } // 可选的，如果需要，可以传递重定向后的路由路径
        });
    } else {
        // 如果 token 存在，则允许访问当前路由
        next();
    }
}
