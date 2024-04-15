package org.example.timecoinweb.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.example.pojo.Activity;
import org.example.pojo.PageBean;
import org.example.pojo.Result;
import org.example.pojo.VolActivity;
import org.example.timecoinweb.service.ActivityService;
import org.example.timecoinweb.service.UserService;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ActivityService activityService;

    /**
     * 将用户（老人）自己的所有活动展现出来
     * @param page
     * @param pageSize
     * @param token
     * @return
     */
    @GetMapping("/old")
    public Result oldSelectActivity(@RequestParam(defaultValue ="1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestHeader("token")String token){
        log.info("分页查询，参数：{},{}",page,pageSize);

        //把用户id搞到手
        Integer userId=(Integer) JwtUtils.parseJWT(token).get("id");
        PageBean pagebean=userService.oldSelectActivity(page,pageSize,userId);

        return Result.success(pagebean);
    }


    /**
     * 用户（老人）新增加活动
     * @param activity
     * @param token
     * @return
     */
    @PostMapping("/old")
    public Result oldAddActivity(@RequestBody Activity activity, @RequestHeader("token")String token){

        log.info("老人添加活动：{}",activity);
        //解析token
        Integer userId=(Integer) JwtUtils.parseJWT(token).get("id");

        userService.oldAddActivity(activity,userId);

        return Result.success();
    }


    /**
     * 用户（老人）对志愿活动的编辑（默认status为5，代表未发布）
     * 如果老人发布任务就把，status改为1（代表未审核）
     * @param activity
     * @return
     */
    @PutMapping("/old")
    public Result oldModify(@RequestBody Activity activity){
        userService.update(activity);

        return Result.success();
    }

    /**
     * 用户（老人）删除活动，做法：根据id删除活动，
     * 因为只会让老人看到自己的活动，不用怕他乱删
     * @param ids
     * @return
     */
    @DeleteMapping("/old/{ids}")
    public Result oldDeleteActivity(@PathVariable List<Integer> ids){
        log.info("老人批量删除活动，ids{}",ids);

        userService.oldDeleteActivity(ids);

        return Result.success();
    }


    /**
     * 老人查询一个活动的所有志愿者的完成和签到信息
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/old/status")
    public Result oldSelectVolStatus(@RequestParam(defaultValue ="1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     Integer id
                                     ){
        log.info("老人查询活动id为{}志愿者的信息",id);

        PageBean pageBean=userService.oldSelectVolStatus(id,page,pageSize);

        return Result.success(pageBean);
    }


    /**
     * 用户（志愿者）接受该志愿活动，做法：
     * 1.将剩余人数remain减少1，假如人数满了，拒绝
     * 2.将中间表volunteer_activity维护
     * @param activity
     * @param token
     * @return
     */
    @PutMapping("/vol")
    public Result volAcceptActivity(@RequestBody Activity activity,@RequestHeader("token") String token){
        log.info("用户参加活动");
        //获取用户id
        Integer userId=(Integer) JwtUtils.parseJWT(token).get("id");

        String msg=userService.volAcceptActivity(activity.getId(),userId);

        if(Objects.equals(msg, "1")){
            return Result.success();
        }

        return Result.error(msg);
    }


    /**
     *
     * 用户查看所有通过的活动，做法：查询status某值的活动,且志愿者没有报名的活动
     * 用户搜索标题或描述符合的活动
     * @param page
     * @param pageSize
     * @param title
     * @param date
     * @param begin
     * @param end
     * @param address
     * @return
     */
    @GetMapping("/vol")
    public Result volSelectActivity(@RequestParam(defaultValue ="1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    String title, @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date,
                                    @DateTimeFormat(pattern = "HH:mm:ss") LocalTime begin,
                                    @DateTimeFormat(pattern = "HH:mm:ss") LocalTime end,
                                    Short status,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime deadline,
                                    String address, @RequestHeader("token")String token){
        log.info("为用户检索出可以参加的活动参数为{},{},{},{},{},{},{},{},{}",page,pageSize,title,date,begin,end,address,status,deadline);

        //获取志愿者用户id
        Integer id=(Integer) JwtUtils.parseJWT(token).get("id");

        PageBean pagebean=userService.volSelectActivity(id,page,pageSize,title,date,begin,end,address,status,deadline);

        return Result.success(pagebean);
    }

    /**
     * 志愿者用户查询自己报名的活动
     * 做法：用中间表查出活动ids，再用活动ids查出活动信息
     * @param page
     * @param pageSize
     * @param token
     * @param title
     * @param date
     * @param begin
     * @param end
     * @param address
     * @param status 一个活动状态
     * @return
     */
    @GetMapping("/vol/activity")
    public Result volSelectMine(@RequestParam(defaultValue ="1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestHeader("token")String token,String title,
                                @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date,
                                @DateTimeFormat(pattern = "HH:mm:ss") LocalTime begin,
                                @DateTimeFormat(pattern = "HH:mm:ss") LocalTime end,
                                String address,Short status){
        log.info("为用户条件检索出报名的活动，参数为{},{},{},{},{},{},{},{}",page,pageSize,title,date,begin,end,address,status);

        //获取志愿者用户id
        Integer id=(Integer) JwtUtils.parseJWT(token).get("id");

        PageBean pageBean=userService.volSelectMine(id,page,pageSize,title,date,begin,end,address,status);

        return Result.success(pageBean);
    }


    /**
     * 志愿者取消报名
     * @param token
     * @param activity
     * @return
     */
    @PutMapping("/vol/cancel")
    public Result volCancel(@RequestHeader("token")String token,@RequestBody Activity activity){
        //获取志愿者用户id和活动id
        Integer userId=(Integer) JwtUtils.parseJWT(token).get("id");
        Integer activityId=activity.getId();

        log.info("用户退出活动，活动id:{}，用户id:{}");

        //前端与活动报名截止时间相比较，如果活动报名截止，不能退出
        try {
            userService.volCancel(userId,activityId);
        }catch (DataAccessException e){
            return Result.error("退出活动失败");
        }

        return Result.success();
    }


    /**
     * 用活动id查询活动信息
     * @param id
     * @return
     */
    @GetMapping("/volold/activity")
    public Result selectActById(Integer id){
        log.info("查询活动信息，id：{}",id);

        Activity activity=activityService.selectById(id);

        return Result.success(activity);
    }

    /**
     * 老人更新报名志愿者的完成和签到状态
     * 做法：将sign和status修改成对应的值
     */
    @PutMapping("/old/status")
    public Result oldUpdateVolSignAndStatus(@RequestBody VolActivity volActivity){

        log.info("更新中间表{}",volActivity);

        userService.updateVolActivity(volActivity);

        return Result.success();
    }

    /**
     * 志愿者用户进行定位签到
     * @param activity
     * @param token
     * @return
     */
    @PutMapping("/vol/sign")
    public Result volSignIn(@RequestBody Activity activity,@RequestHeader("token") String token){
        //获取志愿者用户id和活动id
        Integer userId=(Integer) JwtUtils.parseJWT(token).get("id");

        log.info("进行签到，{}，用户id，{}",activity,userId);

        //利用活动id、用户id和活动的地址进行匹配
        Boolean judge=userService.volSignIn(activity,userId);

        if(judge.equals(true)){
            return Result.success();
        }

        return Result.error("签到失败，地址错误");
    }

    /**
     * 志愿者用户用用户id和活动id，查询当前活动的完成信息和签到信息
     * @param activityId
     * @return
     */
    @GetMapping("/vol/sign")
    public Result volCheckMine(Integer activityId,@RequestHeader("token") String token){
        //获取志愿者用户id和活动id
        Integer userId=(Integer) JwtUtils.parseJWT(token).get("id");

        log.info("用户查看完成和签到，活动id，{}，用户id，{}",activityId,userId);

        //根据活动id和用户id，查询活动状态信息
        VolActivity volActivity=userService.volCheckMine(activityId,userId);

        if(volActivity==null){
            return Result.error("查询不到该活动");
        }

        return Result.success(volActivity);
    }

}
