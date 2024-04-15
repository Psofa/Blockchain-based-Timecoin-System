package org.example.timecoinweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.PageBean;
import org.example.pojo.Result;
import org.example.pojo.User;
import org.example.timecoinweb.service.ManageUserService;
import org.example.timecoinweb.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/administrator/users")
public class ManageUserController {

    @Autowired
    ManageUserService manageUserService;
    @Autowired
    RegisterService registerService;

    //条件查询用户
    @GetMapping
    public Result selectUsers(@RequestParam(defaultValue ="1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize,Short role,
                              String username,String address,Short minAge,Short maxAge){
        log.info("分页查询用户，参数为{},{}，角色为{}，地址为{}，用户名为{}，年龄为{}到{}",page,pageSize,role,address,username,minAge,maxAge);

        PageBean pageBean=manageUserService.selectUsers(page,pageSize,role,address,username,minAge,maxAge);

        return Result.success(pageBean);
    }

    /**
     * 批量删除用户
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result deleteById(@PathVariable List<Integer> ids){
        log.info("批量删除用户，ids{}:",ids);

        manageUserService.deleteById(ids);

        return Result.success();
    }

    /**
     * 修改或更新用户
     */
    @PutMapping
    public Result update(@RequestBody User user){
        log.info("更新用户信息：{}",user);

        try {
            manageUserService.update(user);
        }catch (Exception e){
            log.info("修改的用户名已存在");

            return Result.error("修改的用户名已存在");
        }

        return Result.success();
    }

    /**
     * 新增加用户
     * @param user
     * @return
     */
    @PostMapping
    public Result add(@RequestBody User user){
        //用service保存到数据库
        String msg=registerService.register(user);
        log.info("新增用户：{}",user);

        if(msg.equals("1")){
            return Result.success();
        }

        return Result.error(msg);
    }
}
