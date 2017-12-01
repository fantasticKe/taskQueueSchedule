package com.github.fantasticke.taskqueueschedule.schedule.po;

import java.io.Serializable;

/**
 * @author maokeluo
 * @desc
 * @create 17-11-30
 */
public class Task implements Serializable{

    private static final long serializableID = -1L;

    /**id**/
    private Long id;
    /**时间表达式**/
    private String cron;
    /**状态**/
    private int status;
    /**任务名称**/
    private String name;
    /**任务组别**/
    private String group;
    /**任务**/
    private String task;

    public Task(Long id, String cron, int status, String name, String group, String task) {
        this.id = id;
        this.cron = cron;
        this.status = status;
        this.name = name;
        this.group = group;
        this.task = task;
    }

    public static long getSerializableID() {
        return serializableID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setJobGroup(String group) {
        this.group = group;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", cron='" + cron + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", task='" + task + '\'' +
                '}';
    }
}
