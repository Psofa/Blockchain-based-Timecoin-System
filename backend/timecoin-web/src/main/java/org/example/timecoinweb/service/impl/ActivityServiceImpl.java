package org.example.timecoinweb.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.pojo.Activity;
import org.example.pojo.PageBean;
import org.example.timecoinweb.mapper.ActivityMapper;
import org.example.timecoinweb.mapper.AdmiMapper;
import org.example.timecoinweb.mapper.VolMapper;
import org.example.timecoinweb.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    VolMapper volMapper;
    @Autowired
    AdmiMapper admiMapper;


    @Override
    public PageBean selectPageActivity(Integer page, Integer pageSize, String title, LocalDate date, LocalTime begin, LocalTime end, Short status, String address) {
        //1.设置分页参数
        PageHelper.startPage(page, pageSize);

        //2.执行查询
        List<Activity> activityList = activityMapper.selectPageActivity(null,title, date, begin, end, status, address,null);
        Page<Activity> p=(Page<Activity>) activityList;

        //3.封装PageBean对象
        PageBean pageBean=new PageBean(p.getTotal(),p.getResult());

        return pageBean;
    }

    @Transactional
    @Override
    public void deleteById(List<Integer> ids) {
        //删除活动表的的对应项
        activityMapper.deleteById(ids);
        //维护与活动表相关的表
        volMapper.deleteVolActByActIds(ids);
    }

    @Override
    public void update(Activity activity,Integer userId) {
        //将userId转换为administratorId
        Integer administrator=admiMapper.selectAdministratorId(userId);
        //放入到activity
        activity.setAdministratorId(administrator);

        activity.setUpdateTime(LocalDateTime.now());

        activityMapper.update(activity);
    }

    @Override
    public void add(Activity activity,Integer userId) {
        //将userId转换为administratorId
        Integer administrator=admiMapper.selectAdministratorId(userId);
        //放入到activity
        activity.setAdministratorId(administrator);

        activity.setCreateTime(LocalDateTime.now());
        activity.setUpdateTime(LocalDateTime.now());
        activity.setRemain(activity.getQuota());

        activityMapper.insert(activity);
    }

    @Override
    public void updateExpired() {
        activityMapper.updateExpired();
    }

    @Override
    public Activity selectById(Integer id) {
        return activityMapper.selectByActId(id);
    }


}
