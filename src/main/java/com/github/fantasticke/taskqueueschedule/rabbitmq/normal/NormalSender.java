package com.github.fantasticke.taskqueueschedule.rabbitmq.normal;

import com.github.fantasticke.taskqueueschedule.schedule.dao.TaskDao;
import com.github.fantasticke.taskqueueschedule.schedule.po.Task;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author maokeluo
 * @desc
 * @create 17-11-30
 */
@Component
public class NormalSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private TaskDao taskDao;

    public void send(String id, String task){
        //发送任务到mq队列
        this.amqpTemplate.convertAndSend("task",task);
        //将任务的状态修改为已发送
        Task taskP = taskDao.findById(Long.valueOf(id));
        taskP.setStatus(0);
        taskDao.update(taskP);
    }
}
