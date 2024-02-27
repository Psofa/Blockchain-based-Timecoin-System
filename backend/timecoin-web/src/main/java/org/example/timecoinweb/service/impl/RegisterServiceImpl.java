package org.example.timecoinweb.service.impl;

import org.example.pojo.User;
import org.example.timecoinweb.mapper.RegisterMapper;
import org.example.timecoinweb.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegisterServiceImpl implements RegisterService{

    @Autowired
    private RegisterMapper registerMapper;

    @Override
    public void register(User user) {
        //调用对应的registermapper
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        registerMapper.register(user);
    }

    @Override
    public User login(User user) {
        return registerMapper.getByUsernameAndPassword(user);
    }
}
