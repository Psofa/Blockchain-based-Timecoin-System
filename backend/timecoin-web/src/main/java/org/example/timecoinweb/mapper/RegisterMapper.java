package org.example.timecoinweb.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.pojo.User;
<<<<<<< HEAD
import org.springframework.dao.DuplicateKeyException;

import java.sql.SQLIntegrityConstraintViolationException;
=======
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f

@Mapper
public interface RegisterMapper {

    /**
     * 新增加用户，注册
     */
    @Insert("insert into user(username,name,password,role,email,age,phone,address,create_time,update_time)" +
            "values(#{username},#{name},#{password},#{role},#{email},#{age},#{phone},#{address},#{createTime},#{updateTime})")
<<<<<<< HEAD
    void register(User user) throws DuplicateKeyException;

    /**
     * 根据用户名和密码查询
     * @param user
     * @return
     */
    @Select("select * from user where username=#{username} " +
            "and password=#{password}")
    User getByUsernameAndPassword(User user);

    /**
     * 根据id查询用户自身
     * @param id
     * @return
     */
    @Select("select * from user where id=#{id}")
    User getById(Integer id);
=======
    void register(User user);

    /**
     * 根据用户名和密码查询员工
     * @param user
     * @return
     */

    @Select("select * from user where username=#{username} " +
            "and password=#{password}")
    User getByUsernameAndPassword(User user);
>>>>>>> c09fab713a5f284a2c783aa7635c72ace7d1f39f
}
