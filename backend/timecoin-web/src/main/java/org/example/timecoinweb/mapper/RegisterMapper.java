package org.example.timecoinweb.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.User;

@Mapper
public interface RegisterMapper {

    /**
     * 新增加用户，注册
     */
    @Insert("insert into user(username,name,password,role,email,age,phone,address,create_time,update_time)" +
            "values(#{username},#{name},#{password},#{role},#{email},#{age},#{phone},#{address},#{createTime},#{updateTime})")
    void register(User user);

    /**
     * 根据用户名和密码查询员工
     * @param user
     * @return
     */

    @Select("select * from user where username=#{username} " +
            "and password=#{password}")
    User getByUsernameAndPassword(User user);
}
