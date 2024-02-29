package org.example.timecoinweb.service;


import org.example.pojo.User;

public interface RegisterService {
    public void register(User user);

    User login(User user);
}
