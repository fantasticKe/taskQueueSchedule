package com.github.fantasticke.taskqueueschedule.schedule.service;

import com.github.fantasticke.taskqueueschedule.schedule.dao.TaskDao;
import com.github.fantasticke.taskqueueschedule.schedule.po.Task;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author maokeluo
 * @desc
 * @create 17-12-1
 */
@Service
public class ScheduleTriggerServiceImpl implements ScheduleTriggerService{

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTriggerServiceImpl.class);
    @Autowired
    private Scheduler scheduler;

    @Autowired
    private TaskDao taskDao;

    @Scheduled(cron = "0 0 1 * * ?")
    @Override
    public void refreshTrigger() {
        try{
            List<Task> tasks = taskDao.findAll();
            if (tasks != null){
                for (Task task: tasks){
                    int status = task.getStatus();
                    TriggerKey triggerKey = TriggerKey.triggerKey(task.getId().toString());
                    CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                    if (null == trigger){//说明本条任务还没有添加到quartz中
                        if (status == 0){//如果该任务是禁用,则不用创建触发器
                            continue;
                        }
                        try{
                            JobDetail jobDetail = null;
                            JobDataMap jobDataMap = new JobDataMap();
                            String key = task.getId().toString();
                            jobDataMap.put(key,task.getTask());
                            //创建JobDetail（数据库中job_name存的任务全路径，这里就可以动态的把任务注入到JobDetail中）
                            jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(task.getName()))
                                    .withIdentity(task.getName(),task.getGroup()).withDescription(key)
                                    .setJobData(jobDataMap).build();
                            //表达式调度构建器
                            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCron());
                            //按新的cronExpression表达式构建一个新的trigger
                            trigger = TriggerBuilder.newTrigger().withIdentity(task.getName(),task.getGroup())
                                    .withSchedule(scheduleBuilder).build();
                            //把trigger和jobDetail注入到调度器
                            scheduler.scheduleJob(jobDetail, trigger);
                        }catch (ClassNotFoundException e){
                            e.printStackTrace();
                        }
                    }else {//说明查出来的这条任务，已经设置到quartz中了
                        // Trigger已存在，先判断是否需要删除，如果不需要，再判定是否时间有变化
                        if (status == 0){//如果是禁用，从quartz中删除这条任务
                            JobKey jobKey = JobKey.jobKey(task.getName());
                            scheduler.deleteJob(jobKey);
                            continue;
                        }
                        String searchCron = task.getCron(); //获取数据库的表达式
                        String currentCron = trigger.getCronExpression();
                        if (!currentCron.equals(searchCron)){//该任务有变化，需要更新quartz中的对应的记录
                            //表达式调度构建器
                            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron);
                            //按新的cronExpression表达式重新构建trigger
                            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                                    .withSchedule(scheduleBuilder).build();
                            //按新的trigger重新设置job执行
                            scheduler.rescheduleJob(triggerKey, trigger);
                        }
                    }
                }
            }
        }catch (Exception e){
            logger.error("定时任务每日刷新触发器任务异常，在ScheduleTriggerService的方法refreshTrigger中，异常信息：",e);
        }
    }
}
