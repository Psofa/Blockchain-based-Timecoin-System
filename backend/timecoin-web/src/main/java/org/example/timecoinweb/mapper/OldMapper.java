package org.example.timecoinweb.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OldMapper {
    /**
     * 查询老人的老人id
     * @param userId
     * @return
     */
    @Select("select id from old where user_id=#{userId}")
    Integer selectOldId(Integer userId);

    /**
     * 查询老人的用户id
     * @param id
     * @return
     */
    @Select("select user_id from old where id=#{id}")
    Integer selectUserId(Integer id);

    /**
     * 插入新的老人，自动分配一个老人id
     * @param userId
     */
    @Insert("insert into old(user_id) values(#{userId})")
    void insertByUserId(Integer userId);

    /**
     * 当老人用户表被删除时，删除老人员表的条例
     * @param userId
     */
    @Delete("delete from old where user_id=#{userId}")
    void deleteByUserId(Integer userId);

    /**
     * 老人多个用户删除
     * @param ids
     */
    void deleteByUserIds(@Param("ids") List<Integer> ids);
}
