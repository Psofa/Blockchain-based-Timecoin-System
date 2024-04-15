package org.example.timecoinweb.service;


import org.example.pojo.User;

public interface RegisterService {
    public String register(User user);

    User login(User user);

    /**
     * 根据id查找自己的数据
     * @param id
     * @return
     */
    User selectSelf(Integer id);
}
