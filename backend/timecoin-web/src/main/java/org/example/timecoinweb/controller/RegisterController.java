package org.example.timecoinweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.timecoinweb.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     * 注册
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        //用service保存到数据库
        String msg=registerService.register(user);
        log.info("注册新用户");

        if(msg.equals("1")){
            return Result.success();
        }

        return Result.error(msg);
    }

    


}
