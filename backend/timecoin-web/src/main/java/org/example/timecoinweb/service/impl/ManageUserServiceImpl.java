package org.example.timecoinweb.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.pojo.Activity;
import org.example.pojo.PageBean;
import org.example.pojo.User;
import org.example.timecoinweb.mapper.AdmiMapper;
import org.example.timecoinweb.mapper.OldMapper;
import org.example.timecoinweb.mapper.UserMapper;
import org.example.timecoinweb.mapper.VolMapper;
import org.example.timecoinweb.service.ManageUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ManageUserServiceImpl implements ManageUserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    private VolMapper volMapper;
    @Autowired
    private AdmiMapper admiMapper;
    @Autowired
    private OldMapper oldMapper;

    @Transactional
    @Override
    public void deleteById(List<Integer> ids) {

        //维护中间表
        oldMapper.deleteByUserIds(ids);
        admiMapper.deleteByUserIds(ids);
        volMapper.deleteByUserIds(ids);

        userMapper.deleteById(ids);
    }

    @Override
    public void update(User user) throws DuplicateKeyException {
        user.setUpdateTime(LocalDateTime.now());

        userMapper.update(user);
    }

    @Override
    public PageBean selectUsers(Integer page, Integer pageSize, Short role, String address, String username, Short minAge,Short maxAge) {
        //1.设置分页参数
        PageHelper.startPage(page, pageSize);

        //2.执行查询
        List<User> userList = userMapper.selectUsers(role,address,username,minAge,maxAge);
        Page<User> p=(Page<User>) userList;

        //3.封装PageBean对象
        PageBean pageBean=new PageBean(p.getTotal(),p.getResult());

        return pageBean;


    }


}
