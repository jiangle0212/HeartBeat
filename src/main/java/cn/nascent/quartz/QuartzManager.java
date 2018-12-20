package cn.nascent.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author wei
 * @date 12/19/18  11:18 AM
 * <p>
 * 功能：管理quartz的定时任务
 */
public class QuartzManager {

    /**
     * 调度器工厂
     */
    private static final SchedulerFactory SCHEDULER_FACTORY = new StdSchedulerFactory();
    /**
     * 默认的任务组名
     */
    private static final String JOB_GROUP_NAME = "HEART_BEAT_JOBGROUP_NAME";
    /**
     * 默认的触发器组名
     */
    private static final String TRIGGER_GROUP_NAME = "HEART_BEAT_TRIGGERGROUP_NAME";

    /**
     * 使用默认的job组名以及默认的trigger组名来创建定时任务
     * 且触发器名与任务名一样
     *
     * @param jobName  任务名
     * @param jobClazz job，任务组件
     * @param interval 任务执行间隔
     */
    public static void addJob(String jobName, Class jobClazz, int interval) {

        try {

            Scheduler scheduler = QuartzManager.SCHEDULER_FACTORY.getScheduler();
            JobKey jobKey = new JobKey(jobName, QuartzManager.JOB_GROUP_NAME);
            JobDetail jobDetail = JobBuilder.newJob(jobClazz).withIdentity(jobKey).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, QuartzManager.TRIGGER_GROUP_NAME).
                    withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).repeatForever()).build();

            if (scheduler == null) {
                throw new RuntimeException("Scheduller 为空");
            }

            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();

            System.err.println("定时任务添加成功");
        } catch (SchedulerException e) {
            System.err.println("定时任务任务添加失败");
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }

    /**
     * 移除一个定时任务
     * 使用了默认的任务组名，默认的触发器组名，且触发器名与任务名一样
     *
     * @param jobName
     */
    public static void removeJob(String jobName) {
        try {

            Scheduler scheduler = QuartzManager.SCHEDULER_FACTORY.getScheduler();

            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, QuartzManager.TRIGGER_GROUP_NAME);
            JobKey jobKey = JobKey.jobKey(jobName, QuartzManager.JOB_GROUP_NAME);

            Trigger trigger = scheduler.getTrigger(triggerKey);

            if (trigger == null) {
                return;
            }

            //停止触发器
            scheduler.pauseTrigger(triggerKey);
            //移除触发器
            scheduler.unscheduleJob(triggerKey);
            //删除任务
            scheduler.deleteJob(jobKey);

            System.err.println("定时任务移除成功");
        } catch (SchedulerException e) {
            System.err.println("定时任务任务移除失败");
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
