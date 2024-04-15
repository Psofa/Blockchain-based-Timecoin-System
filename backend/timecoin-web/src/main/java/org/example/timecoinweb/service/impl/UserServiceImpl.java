package org.example.timecoinweb.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.pojo.*;
import org.example.timecoinweb.mapper.ActivityMapper;
import org.example.timecoinweb.mapper.OldMapper;
import org.example.timecoinweb.mapper.UserMapper;
import org.example.timecoinweb.mapper.VolMapper;
import org.example.timecoinweb.service.ActivityService;
import org.example.timecoinweb.service.UserService;
import org.example.utils.DistanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    ActivityMapper activityMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    OldMapper oldMapper;
    @Autowired
    VolMapper volMapper;
    @Autowired
    DistanceUtils distanceUtils;

    /**
     * 老人增加活动
     * @param activity
     * @param userId
     */
    @Override
    public void oldAddActivity(Activity activity,Integer userId) {
        //用用户io查老人id
        Integer oldId=oldMapper.selectOldId(userId);

        //补充数据
        activity.setRemain(activity.getQuota());
        activity.setCreateTime(LocalDateTime.now());
        activity.setUpdateTime(LocalDateTime.now());
        activity.setOldId(oldId);
        activity.setStatus((short)5);

        //将这个活动存入活动表
        activityMapper.insert(activity);
    }

    /**
     * 老人更新或修改活动数据
     * @param activity
     */
    @Override
    public void update(Activity activity) {

        //将更新时间更改了
        activity.setUpdateTime(LocalDateTime.now());

        activityMapper.update(activity);
    }

    /**
     * 老人删除活动
     * @param ids
     */
    @Transactional
    @Override
    public void oldDeleteActivity(List<Integer> ids) {
        activityMapper.deleteById(ids);

        //维护与活动表相关的表
        volMapper.deleteVolActByActIds(ids);
    }

    /**
     * 老人搜寻自己的活动
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageBean oldSelectActivity(Integer page, Integer pageSize,Integer userId) {
        //找到老人的老人id
        Integer oldId=oldMapper.selectOldId(userId);

        //1.设置分页参数
        PageHelper.startPage(page, pageSize);

        //2.执行查询
        List<Activity> activityList = activityMapper.selectPageActivity(oldId,null,null,null,null,null,null,null);
        Page<Activity> p=(Page<Activity>) activityList;

        //3.封装PageBean对象
        PageBean pageBean=new PageBean(p.getTotal(),p.getResult());

        return pageBean;
    }

    /**
     * 用户（志愿者）接受活动，找到志愿者id并维护中间表,让remain-1
     * @param activityId
     * @param userId
     */
    @Override
    public String volAcceptActivity(Integer activityId, Integer userId) {
        Activity activity;

        //1.找到该志愿者的志愿者id
        Integer volId=volMapper.selectVolId(userId);

        //2.将remain减少1并存入到数据库中
        try {
            activityMapper.lessRemain(activityId);
        }catch (DataAccessException e){
            return "剩余名额不够";
        }

        //3.维护志愿者、活动中间表
        volMapper.insertVolAct(volId, activityId);

        return "1";
    }


    @Override
    public PageBean volSelectMine(Integer id,Integer page, Integer pageSize, String title, LocalDate date, LocalTime begin, LocalTime end, String address,Short status) {
        //找到用户的志愿者id
        Integer volId=volMapper.selectVolId(id);

        //用志愿者ID搜寻出活动Id
        List<Integer> actIds=volMapper.selectActIds(volId);

        //1.设置分页参数
        PageHelper.startPage(page, pageSize);

        //2.执行查询
        List<Activity> activityList = volMapper.volSelectPageActivity(volId,null,title, date, begin, end, status, address,actIds);
        Page<Activity> p=(Page<Activity>) activityList;

        //3.封装PageBean对象
        PageBean pageBean=new PageBean(p.getTotal(),p.getResult());

        return pageBean;
    }

    @Transactional
    @Override
    public void volCancel(Integer userId, Integer activityId) throws DataAccessException{
        //找到志愿者id
        Integer volId=volMapper.selectVolId(userId);

        //删除vol_activity表的对应条例，再将remain加1
        //两条语句一气呵成
        volMapper.volCancel(volId,activityId);
        activityMapper.moreRemain(activityId);

    }

    @Override
    public PageBean volSelectActivity(Integer id, Integer page, Integer pageSize, String title, LocalDate date, LocalTime begin, LocalTime end, String address,Short status,LocalDateTime deadline) {
        //志愿者的志愿者ID
        Integer volId=volMapper.selectVolId(id);
        //找到已经报名的活动id
        List<Integer> actIds=volMapper.selectActIds(volId);

        //1.设置分页参数
        PageHelper.startPage(page, pageSize);

        //2.执行查询
        List<Activity> activities=volMapper.volSelectNotActivity(actIds,title,date,begin,end,status,address,deadline);//未报名活动
        Page<Activity> p=(Page<Activity>) activities;

        //3.封装PageBean对象
        PageBean pageBean=new PageBean(p.getTotal(),p.getResult());

        return pageBean;
    }

    @Override
    public PageBean oldSelectVolStatus(Integer id, Integer page, Integer pageSize) {


        //1.设置分页参数
        PageHelper.startPage(page, pageSize);

        //2.执行查询
        List<Vol> users=volMapper.selectUsers(id);
        Page<Vol> p=(Page<Vol>) users;

        //3.封装PageBean对象
        PageBean pageBean=new PageBean(p.getTotal(),p.getResult());

        return pageBean;
    }

    @Override
    public void updateVolActivity(VolActivity volActivity) {
        //找到志愿者id
        Integer volId=volMapper.selectVolId(volActivity.getUserId());
        volActivity.setVolId(volId);
        //updateTime更新
        volActivity.setUpdateTime(LocalDateTime.now());
        //更新actVol中间表
        volMapper.update(volActivity);
    }

    @Transactional
    @Override
    public Boolean volSignIn(Activity activity, Integer userId) {
        //1.得到志愿者id
        Integer volId=volMapper.selectVolId(userId);
        //2.找到该活动的地址
        String actAddress=activityMapper.selectAddress(activity);

        //3.保存志愿者用户的签到列表
        String userAddress=activity.getAddress();

        //4.检查用户地址与活动地址是否匹配
        double maxDistanceInMeters=100;//设置最大距离为100
        boolean judge= distanceUtils.isWithinDistance(userAddress,actAddress,maxDistanceInMeters);

        if(judge){
            volMapper.volSignIn(volId,activity.getId());

            return true;
        }

        return false;
    }

    @Override
    public VolActivity volCheckMine(Integer activityId, Integer userId) {
        Integer volId=volMapper.selectVolId(userId);

        //用志愿者id和活动id找信息
        VolActivity volActivity=volMapper.volSelectByActVolId(activityId,volId);
        if(volActivity!=null) {
            volActivity.setUserId(userId);
        }

        return volActivity;
    }

    @Override
    public void updateImage(String local,Integer id) {
        userMapper.updateImage(local,id);

    }


}
