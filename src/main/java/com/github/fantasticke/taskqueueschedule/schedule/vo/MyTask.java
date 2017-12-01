package com.github.fantasticke.taskqueueschedule.schedule.vo;

import com.github.fantasticke.taskqueueschedule.rabbitmq.normal.NormalSender;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author maokeluo
 * @desc
 * @create 17-12-1
 */
@DisallowConcurrentExecution//不能并发执行同一个Job Definition(由JobDetail定义), 但是可以同时执行多个不同的JobDetail
@PersistJobDataAfterExecution//为了避免并发时, 存储数据造成混乱
public class MyTask implements Job{

    @Autowired
    private NormalSender normalSender;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String description = jobExecutionContext.getJobDetail().getDescription();
        String[] keys = jobExecutionContext.getJobDetail().getJobDataMap().getKeys();
        String task = jobExecutionContext.getJobDetail().getJobDataMap().get(description).toString();
        normalSender.send(keys[0],task);
    }
}
