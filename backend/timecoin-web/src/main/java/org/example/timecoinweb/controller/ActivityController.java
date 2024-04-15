package org.example.timecoinweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Activity;
import org.example.pojo.PageBean;
import org.example.pojo.Result;
import org.example.timecoinweb.service.ActivityService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

////////////////////管理员管理活动
////////////////////管理员管理活动
////////////////////管理员管理活动
////////////////////管理员管理活动
////////////////////管理员管理活动

@Slf4j
@RestController
@RequestMapping("/administrator")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    /**
     * 用Get型查询所有活动数据
     */
    @GetMapping
    public Result selectPageActivity(@RequestParam(defaultValue ="1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     String title, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                     @DateTimeFormat(pattern = "HH:mm:ss") LocalTime begin,
                                     @DateTimeFormat(pattern = "HH:mm:ss") LocalTime end,
                                     Short status,String address
                                     ){
        log.info("分页查询，参数：{},{},{},{},{},{},{},{}",page,pageSize,title,date,begin,end,status,address);
        PageBean pagebean=activityService.selectPageActivity(page,pageSize,title,date,begin,end,status,address);


        return  Result.success(pagebean);
    }

    /**
     * 删除活动
     */
    @DeleteMapping("/{ids}")
    public Result deleteById(@PathVariable List<Integer> ids){
        log.info("批量删除活动，ids{}:",ids);

        activityService.deleteById(ids);

        return Result.success();
    }

    /**
     * 修改活动
     */
    @PutMapping
    public Result update(@RequestBody Activity activity,@RequestHeader("token") String token){
        log.info("更新活动信息：{}",activity);

        //获取志愿者用户id和活动id
        Integer userId=(Integer) JwtUtils.parseJWT(token).get("id");

        activityService.update(activity,userId);
        return Result.success();
    }

    /**
     * 管理员新增加活动
     * @param activity
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Activity activity,@RequestHeader("token") String token){
        log.info("新增活动，{}",activity);

        //获取志愿者用户id和活动id
        Integer userId=(Integer) JwtUtils.parseJWT(token).get("id");

        //调用service中
        activityService.add(activity,userId);

        return Result.success();
    }

}
