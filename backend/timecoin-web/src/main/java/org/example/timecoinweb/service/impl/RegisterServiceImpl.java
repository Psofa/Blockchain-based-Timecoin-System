package org.example.timecoinweb.service.impl;

<<<<<<< HEAD
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
=======
import org.example.pojo.User;
import org.example.timecoinweb.mapper.RegisterMapper;
import org.example.timecoinweb.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f
@Service
public class RegisterServiceImpl implements RegisterService{

    @Autowired
    private RegisterMapper registerMapper;
<<<<<<< HEAD
    @Autowired
    private AdmiMapper admiMapper;
    @Autowired
    private OldMapper oldMapper;
    @Autowired
    private VolMapper volMapper;


    @Override
    public String register(User user) {
=======

    @Override
    public void register(User user) {
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f
        //调用对应的registermapper
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

<<<<<<< HEAD
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
=======
        registerMapper.register(user);
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f
    }

    @Override
    public User login(User user) {
        return registerMapper.getByUsernameAndPassword(user);
    }
<<<<<<< HEAD

    @Override
    public User selectSelf(Integer id) {
        User user=registerMapper.getById(id);

        return user;
    }
=======
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f
}
