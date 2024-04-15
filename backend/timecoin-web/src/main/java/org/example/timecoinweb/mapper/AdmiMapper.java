package org.example.timecoinweb.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdmiMapper {
    /**
     * 查询管理员的管理员id
     * @param userId
     * @return
     */
    @Select("select id from administrator where user_id=#{userId}")
    Integer selectAdministratorId(Integer userId);

    /**
     * 查询管理员的用户id
     * @param id
     * @return
     */
    @Select("select user_id from administrator where id=#{id}")
    Integer selectUserId(Integer id);

    /**
     * 插入新的管理员，自动分配一个管理员id
     * @param userId
     */
    @Insert("insert into administrator(user_id) values(#{userId})")
    void insertByUserId(Integer userId);

    /**
     * 当管理员用户表被删除时，删除管理员表的条例
     * @param userId
     */
    @Delete("delete from administrator where user_id=#{userId}")
    void deleteByUserId(Integer userId);

    /**
     * 批量删除管理员
     * @param ids
     */
    void deleteByUserIds(@Param("ids") List<Integer> ids);
}
