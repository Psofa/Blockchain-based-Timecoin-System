package org.example.timecoinweb.service;

import org.example.pojo.PageBean;
import org.example.pojo.User;

import java.util.List;

public interface ManageUserService {

    /**
     * 根据id批量删除用户
     * @param ids
     */
    void deleteById(List<Integer> ids);

    /**
     * 更新或修改用户信息
     * @param user
     */
    void update(User user);


    /**
     * 用相关数据查询用户
     * @param page
     * @param pageSize
     * @param role
     * @param address
     * @param username
     * @param minAge
     * @param maxAge
     * @return
     */
    PageBean selectUsers(Integer page, Integer pageSize, Short role, String address, String username, Short minAge,Short maxAge);
}
