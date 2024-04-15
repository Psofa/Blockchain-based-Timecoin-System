package org.example.timecoinweb.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.User;
import org.example.timecoinweb.mapper.AdmiMapper;
import org.example.timecoinweb.mapper.OldMapper;
import org.example.timecoinweb.mapper.RegisterMapper;
import org.example.timecoinweb.mapper.VolMapper;
import org.example.timecoinweb.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@Slf4j
@Service
public class RegisterServiceImpl implements RegisterService{

    @Autowired
    private RegisterMapper registerMapper;
    @Autowired
    private AdmiMapper admiMapper;
    @Autowired
    private OldMapper oldMapper;
    @Autowired
    private VolMapper volMapper;


    @Override
    public String register(User user) {
        //调用对应的registermapper
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        //将user存入到user表中
        try {
            registerMapper.register(user);
        }catch (DuplicateKeyException e){
            log.info("注册的用户名或电话或邮箱重复");
            return "用户名或电话或邮箱重复";
        }


        //提取出user,为了把他存入老人、志愿者、管理员表
        User user2=registerMapper.getByUsernameAndPassword(user);

        switch (user2.getRole()){
            case 1:
                oldMapper.insertByUserId(user2.getId());
                break;
            case 2:
                volMapper.insertByUserId(user2.getId());
                break;
            case 3:
                admiMapper.insertByUserId(user2.getId());
                break;
        }

        return "1";
    }

    @Override
    public User login(User user) {
        return registerMapper.getByUsernameAndPassword(user);
    }

    @Override
    public User selectSelf(Integer id) {
        User user=registerMapper.getById(id);

        return user;
    }
}
