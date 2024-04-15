package org.example.timecoinweb.service;


import org.example.pojo.User;

public interface RegisterService {
<<<<<<< HEAD
    public String register(User user);

    User login(User user);

    /**
     * 根据id查找自己的数据
     * @param id
     * @return
     */
    User selectSelf(Integer id);
=======
    public void register(User user);

    User login(User user);
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f
}
