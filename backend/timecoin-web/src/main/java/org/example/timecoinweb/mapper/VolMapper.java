package org.example.timecoinweb.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Activity;
import org.example.pojo.User;
import org.example.pojo.Vol;
import org.example.pojo.VolActivity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Mapper
public interface VolMapper {
    /**
     * 查询志愿者的志愿id
     * @param userId
     * @return
     */
    @Select("select id from volunteer where user_id=#{userId}")
    Integer selectVolId(Integer userId);

    /**
     * 查询志愿的用户id
     * @param id
     * @return
     */
    @Select("select user_id from volunteer where id=#{id}")
    Integer selectUserId(Integer id);

    /**
     * 插入新的志愿者，自动分配一个志愿者id
     * @param userId
     */
    @Insert("insert into volunteer(user_id) values(#{userId})")
    void insertByUserId(Integer userId);

    /**
     * 当志愿者用户表被删除时，删除志愿者表的条例
     * @param userId
     */
    @Delete("delete from volunteer where user_id=#{userId}")
    void deleteByUserId(Integer userId);


    /**
     * 维护活动、志愿者中间表
     * @param volId
     * @param activityId
     */
    @Insert("insert into activity_volunteer(activity_id,volunteer_id,create_time,update_time) values(#{activityId},#{volId},now(),now()) ")
    void insertVolAct(@Param("volId") Integer volId,@Param("activityId") Integer activityId);

    /**
     * 查询志愿者用户报名的活动id
     * @param volId
     * @return
     */
    @Select("select activity_id from activity_volunteer where volunteer_id=#{volId}")
    List<Integer> selectActIds(@Param("volId") Integer volId);

    /**
     * 取消报名，将vol_act表中对应条例删除
     * @param volId
     * @param activityId
     */
    @Delete("delete from activity_volunteer where volunteer_id=#{volId} and activity_id=#{activityId}")
    void volCancel(@Param("volId") Integer volId,@Param("activityId") Integer activityId);

    /**
     * 批量删除vol用户
     * @param ids
     */
    void deleteByUserIds(@Param("ids") List<Integer> ids);


    /**
     * 查询用户报名了的活动显示
     * @param oldId
     * @param title
     * @param date
     * @param begin
     * @param end
     * @param status
     * @param address
     * @param ids
     * @return
     */
    List<Activity> volSelectPageActivity(@Param("volId") Integer volId,@Param("oldId") Integer oldId,@Param("title") String title,@Param("date") LocalDate date,
                                         @Param("begin") LocalTime begin,@Param("end") LocalTime end,@Param("status") Short status,
                                         @Param("address") String address,@Param("ids") List<Integer> ids);

    /**
     * 查询志愿者用户未报名活动
     * @param ids
     * @param title
     * @param date
     * @param begin
     * @param end
     * @param address
     * @return
     */
    List<Activity> volSelectNotActivity(@Param("ids") List<Integer> ids, @Param("title") String title,
                                        @Param("date") LocalDate date, @Param("begin") LocalTime begin,
                                        @Param("end") LocalTime end, @Param("status") Short status, @Param("address") String address, @Param("deadline")LocalDateTime deadline);

    /**
     * 根据活动id查询用户信息
     * @param activityId
     * @return
     */
    @Select("select user.id,user.name,user.phone," +
            "user.email,user.age,av.status,av.sign " +
            "from user,activity_volunteer as av,volunteer as vol " +
            "where av.activity_id=#{activityId} and av.volunteer_id =vol.id and vol.user_id=user.id")
    List<Vol> selectUsers(@Param("activityId") Integer activityId);

    /**
     * 根据活动名删除志愿者活动中间表的项
     * @param ids
     */
    void deleteVolActByActIds(@Param("ids") List<Integer> ids);

    /**
     * 对volAct中间表进行更新
     * @param volActivity
     */
    void update(VolActivity volActivity);

    /**
     * 将数据库中的签到改为1
     * @param volId
     * @param activityId
     */
    @Update("update activity_volunteer set sign=1 where volunteer_id=#{volId} and activity_id=#{activityId}")
    void volSignIn(@Param("volId") Integer volId,@Param("activityId") Integer activityId);

    /**
     * 用活动id和志愿者id查询状态信息
     * @param activityId
     * @param volId
     * @return
     */
    @Select("select id, volunteer_id as volId, activity_id, status, create_time, update_time, sign from activity_volunteer where activity_id=#{activityId} and volunteer_id=#{volId}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "volId",column = "volId"),
            @Result(property = "activityId",column = "activity_id"),
            @Result(property = "status",column = "status"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "updateTime",column = "updateTime"),
            @Result(property = "sign",column = "sign")
    })
    VolActivity volSelectByActVolId(@Param("activityId") Integer activityId,@Param("volId") Integer volId);
}
