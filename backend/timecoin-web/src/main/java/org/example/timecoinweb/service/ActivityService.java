package org.example.timecoinweb.service;

import org.example.pojo.Activity;
import org.example.pojo.PageBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ActivityService {

    /**
     * 查找活动
     * @param page
     * @param pageSize
     * @param title
     * @param date
     * @param begin
     * @param end
     * @param status
     * @param address
     * @return
     */
    PageBean selectPageActivity(Integer page, Integer pageSize, String title, LocalDate date, LocalTime begin, LocalTime end, Short status, String address);

    /**
     * 用来实现批量删除
     * @param ids
     */
    void deleteById(List<Integer> ids);

    /**
     * 用来实现修改活动数据
     * @param activity
     */
    void update(Activity activity,Integer userId);

    /**
     * 管理员新增加活动
     * @param activity
     */
    void add(Activity activity,Integer userId);

    /**
     * 将过期的活动状态设为4（已过期）
     */
    void updateExpired();

    /**
     * 根据id查询活动信息
     * @param id
     * @return
     */
    Activity selectById(Integer id);
}
