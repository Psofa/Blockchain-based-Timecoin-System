package org.example.timecoinweb.service;

import org.example.pojo.Activity;
import org.example.pojo.PageBean;
import org.example.pojo.VolActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface UserService {
    /**
     * 老人添加活动
     * @param activity
     * @param userId
     */
    void oldAddActivity(Activity activity,Integer userId);

    /**
     * 老人更新或修改活动数据
     * @param activity
     */
    void update(Activity activity);

    /**
     * 老人删除活动
     * @param ids
     */
    void oldDeleteActivity(List<Integer> ids);

    /**
     * 老人搜寻活动
     * @param page
     * @param pageSize
     * @return
     */
    PageBean oldSelectActivity(Integer page, Integer pageSize,Integer userId);

    /**
     * 用户（志愿者）接受活动，找到志愿者id并维护中间表，让remain-1
     * @param activityId
     * @param userId
     */
    String volAcceptActivity(Integer activityId, Integer userId);

    /**
     * 志愿者查询自己报名过的活动
     * @param page
     * @param pageSize
     * @param title
     * @param date
     * @param begin
     * @param end
     * @param address
     */
    PageBean volSelectMine(Integer id,Integer page, Integer pageSize, String title, LocalDate date, LocalTime begin, LocalTime end, String address,Short status);

    /**
     * 志愿者用户退出活动
     * @param userId
     * @param activityId
     */
    void volCancel(Integer userId, Integer activityId);

    /**
     * 志愿查询能报名的活动，也就是没有报名的活动且状态为2
     * @param id
     * @param page
     * @param pageSize
     * @param title
     * @param date
     * @param begin
     * @param end
     * @param address
     * @return
     */
    PageBean volSelectActivity(Integer id, Integer page, Integer pageSize, String title, LocalDate date, LocalTime begin, LocalTime end, String address, Short status, LocalDateTime deadline);

    /**
     * 活动id查询所有报名的志愿者
     * @param id 活动id
     * @param page
     * @param pageSize
     * @return
     */
    PageBean oldSelectVolStatus(Integer id, Integer page, Integer pageSize);

    /**
     * 更新修改volAct中间表
     * @param volActivity
     */
    void updateVolActivity(VolActivity volActivity);

    /**
     * 将志愿者用户的活动地址
     * 与数据库中的活动地址比较，
     * 然后
     * @param activity
     * @param userId
     * @return
     */
    Boolean volSignIn(Activity activity, Integer userId);

    /**
     * 用活动id和用户id查询志愿者已报名的一个活动的状态
     * @param activityId
     * @param userId
     * @return
     */
    VolActivity volCheckMine(Integer activityId, Integer userId);

    //存图片本地位置
    void updateImage(String local,Integer id);
}
