package org.example.timecoinweb.interceptor;


import com.alibaba.fastjson.JSONObject;
<<<<<<< HEAD
import com.sun.jdi.IntegerValue;
import io.jsonwebtoken.Claims;
=======
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.example.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception{
        //解决跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //1.获取url
        String url =request.getRequestURL().toString();
        log.info("请求的url:{}",url);

<<<<<<< HEAD
        //2.判断请求的url是否包含login,如果包含有登录或注册，放行
=======
        //2.判断请求的url是否包含login,如果包含有登录，放行
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f
        if(url.contains("login")){
            log.info("登录操作，放行..");
            return true;
        }
<<<<<<< HEAD
        if(url.contains("register")){
            log.info("注册操作，放行..");
            return true;
        }
        if(url.contains("image")){
            log.info("查看图片，放行..");
            return true;
        }
=======
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f

        //3.请求头当中获取token
        String jwt = request.getHeader("token");

        //4.判断令牌是否存在
        if(!StringUtils.hasLength(jwt)){
            log.info("token为空，返回未登录的信息");

            Result error=Result.error("NOT_LOGIN");
            //转换对象为json，用阿里巴巴的fastJSON
            String notLogin = JSONObject.toJSONString(error);

            response.getWriter().write(notLogin);

            return false;
        }

        //5.解析token，如果解析失败，返回错误结果
        try {
<<<<<<< HEAD
            Claims claims=JwtUtils.parseJWT(jwt);
            Integer role= (Integer) claims.get("role");

            if(url.contains("info")){
                return true;
            }

            //当用户不是对应角色，想进入对应页面，进行拦截
            if((role==3&&(!url.contains("administrator")))
                    ||(role==1&&(!url.contains("old")))
                    ||(role==2&&(!url.contains("vol")))){
                Result error=Result.error("ACCESS_RESTRICTED");
                //转换成json对象，并传回去
                String refuse=JSONObject.toJSONString(error);
                response.getWriter().write(refuse);

                return false;
            }

=======
            JwtUtils.parseJWT(jwt);
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f
        }catch (Exception e){
            e.printStackTrace();
            log.info("解析令牌失败，返回未登录错误信息");

            Result error=Result.error("NOT_LOGIN");
            //转换为json对象
            String notLogin = JSONObject.toJSONString(error);
            response.getWriter().write(notLogin);

            return false;
        }

        //6.放行
        log.info("令牌合法，放行");
        return true;

    }
}
