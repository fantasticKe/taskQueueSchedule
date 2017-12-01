package com.github.fantasticke.taskqueueschedule.schedule.dao;

import com.github.fantasticke.taskqueueschedule.schedule.po.Task;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author maokeluo
 * @desc
 * @create 17-11-30
 */
@Mapper
@Component
public interface TaskDao {

    /**
     * @desc 新增任务
     * @author maokeluo
     * @methodName save
     * @param  task
     * @create 17-11-30
     * @return int
     */
    @Insert("insert into task_job values(#{id},#{cron},#{jobStatus},#{jobName},#{jobGroup},#{task})")
    int save(Task task);

    /**
     * @desc 根据id删除任务
     * @author maokeluo
     * @methodName delete
     * @param  id
     * @create 17-11-30
     * @return int
     */
    @Delete("delete from task_job where id = #{id}")
    int delete(@Param("id") long id);

    /**
     * @desc 修改任务
     * @author maokeluo
     * @methodName update
     * @param  task
     * @create 17-11-30
     * @return int
     */
    @Update("update task_job set job_status = #{status}, set job_name = #{name}, set job_group = #{group}, set task = #{task} where id = #{id}")
    int update(Task task);
    /**
     * @desc 根据id查询任务
     * @author maokeluo
     * @methodName findById
     * @param  id
     * @create 17-11-30
     * @return com.github.fantasticke.taskqueueschedule.schedule.po.Task
     */
    @Select("select * from task_job where id = #{id}")
    Task findById(@Param("id") long id);

    /**
     * @desc 查询所有任务列表
     * @author maokeluo
     * @methodName findAll
     * @param
     * @create 17-11-30
     * @return java.util.List<com.github.fantasticke.taskqueueschedule.schedule.po.Task>
     */
    @Select("select * from task_job")
    @Results({
            @Result(property = "status", column = "job_status"),
            @Result(property = "name", column = "job_name"),
            @Result(property = "group", column = "job_group")
    })
    List<Task> findAll();


}
