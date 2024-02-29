package org.example.timecoinweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Pair;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.timecoinweb.service.RegisterService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class LoginController {

    /**
     * 登录功能
     */
    @Autowired
    private RegisterService registerService;


    @PostMapping("/login")
    public Result login(@RequestBody User user){
        //用service检查是否在数据库
        log.info("登录:{}",user);
        User u=registerService.login(user);

        //登录成功,生成令牌
        if(u!=null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", u.getId());
            claims.put("name", u.getName());
            claims.put("username", u.getUsername());

            String jwt = JwtUtils.generateJwt(claims);

            log.info(jwt);

            Pair pair=new Pair(jwt,u.getRole());

            return Result.success(pair);
        }

        //登录失败
        return Result.error("用户名或密码错误");
    }
}
