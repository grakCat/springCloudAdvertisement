package com.stude.timer_task.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2018/11/9.
 *
 * @author grayCat
 * @since 1.0
 */
public class QuartzService {

    /*调度器*/
    private Scheduler scheduler;

    public QuartzService() {
        try {
            //方法1
            SchedulerFactory sfact = new StdSchedulerFactory();
            scheduler = sfact.getScheduler();

            //方法2(单例)
//          DirectSchedulerFactory factory = DirectSchedulerFactory.getInstance();
//          scheduler = factory.getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始
     */
    protected void start() throws SchedulerException {
        scheduler.start();
    }

    /**
     * 暂停
     */
    protected void pause() throws SchedulerException {
        scheduler.standby();
    }

    /**
     * 关闭
     */
    protected void shutdown() throws SchedulerException {
        //true表示等待定时任务执行完才关闭
        scheduler.shutdown(false);
    }

    /**
     * 添加通用定时器
     */
    public void commonTimmer(TaskInfo info) throws SchedulerException {
        //设置默认参数
        setBaseInfo(info);
        //绑定具体定时任务：执行任务的类、传递信息、名称、组
        JobDetail jobDetail = JobBuilder.newJob(info.getJobClass())
                .usingJobData(info.getJobMap())
                .withIdentity(info.getName(), info.getGroup()).build();

        Trigger trigger = null;
        if (info.getCorn() == null) {
            //简单定时器
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(info.getName(), info.getGroup())
                    //定时器开始时间
                    .startAt(info.getStartAT())
                    //定时器结束时间
                    .endAt(info.getEndAT())
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            //任务执行间隔时间
                            .withIntervalInMilliseconds(info.getIntervalTime())
                            //执行次数
                            .withRepeatCount(info.getCount()))
                    .build();
        } else {
            //复杂定时器
            trigger = TriggerBuilder.newTrigger()
                    .withIdentity(info.getName(), info.getGroup())
                    .startAt(info.getStartAT())
                    .endAt(info.getEndAT())
                    .withSchedule(CronScheduleBuilder.cronSchedule(info.getCorn()))
                    .build();
        }
        if (trigger != null) {
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

    /**
     * 设置默认参数
     *
     * @param info
     */
    private void setBaseInfo(TaskInfo info) {
        if (info.isForever()) {
            info.setCount(SimpleTrigger.REPEAT_INDEFINITELY);
        }
        if (info.getStartAT() == null) {
            info.setStartAT(new Date());
        }
        if (info.getIntervalTime() <= 0) {
            info.setIntervalTime(1000);
        }
        if (info.getName() == null) {
            info.setName("" + new Date().getTime());
        }
        if (info.getGroup() == null) {
            info.setGroup("" + new Date().getTime());
        }
        if (info.getJobMap() == null) {
            info.setJobMap(new JobDataMap());
        }

        TimeUnit timeUnit = info.getIntervalUnit();
        if (timeUnit != null) {
            long intervalTime = info.getIntervalTime();
            info.setIntervalTime(getRealTime(intervalTime, timeUnit));
        }
    }

    private long getRealTime(long time, TimeUnit timeUnit) {
        if (timeUnit == TimeUnit.SECONDS) {
            time *= 1000L;
        } else if (timeUnit == TimeUnit.MINUTES) {
            time *= 60000L;
        } else if (timeUnit == TimeUnit.HOURS) {
            time *= 3600000L;
        } else if (timeUnit == TimeUnit.DAYS) {
            time *= 86400000L;
        }
        return time;
    }

    /**
     * 倒计时单次任务
     * 延迟5秒执行一次
     */
    public void delayTimmer(long time, TimeUnit timeUnit, Class<? extends Job> jobClass, JobDataMap jobMap) throws SchedulerException {
        TaskInfo info = new TaskInfo();
        Date date = new Date();
        date.setTime(date.getTime() + getRealTime(time, timeUnit));
        info.setStartAT(date);
        info.setJobClass(jobClass);
        info.setJobMap(jobMap);
        commonTimmer(info);
    }

    /**
     * 倒计时单次任务
     * 延迟到指定时间，执行一次
     */
    public void delayTimmer(Date startDate, Class<? extends Job> jobClass, JobDataMap jobMap) throws SchedulerException {
        TaskInfo info = new TaskInfo();
        info.setStartAT(startDate);
        info.setJobClass(jobClass);
        info.setJobMap(jobMap);
        commonTimmer(info);
    }

    /**
     * 循环执行
     * 立即开始，每隔固定时间执行任务
     */
    public void intervalTimmer(long time, TimeUnit timeUnit, Class<? extends Job> jobClass, JobDataMap jobMap) throws SchedulerException {
        TaskInfo info = new TaskInfo();
        info.setIntervalTime(getRealTime(time, timeUnit));
        info.setForever(true);
        info.setJobClass(jobClass);
        info.setJobMap(jobMap);
        commonTimmer(info);
    }

    /**
     * 循环执行
     * 在指定时间范围内，固定间隔时间执行任务
     */
    public void intervalTimmer(Date startDate, Date endDate, long interval, TimeUnit timeUnit, Class<? extends Job> jobClass, JobDataMap jobMap) throws SchedulerException {
        TaskInfo info = new TaskInfo();
        info.setIntervalTime(getRealTime(interval, timeUnit));
        info.setStartAT(startDate);
        info.setEndAT(endDate);
        info.setForever(true);
        info.setJobClass(jobClass);
        info.setJobMap(jobMap);
        commonTimmer(info);
    }

    /**
     * 循环执行
     * 设置开始时间和执行次数
     */
    public void intervalTimmer(Date startDate, int count, long interval, TimeUnit timeUnit, Class<? extends Job> jobClass, JobDataMap jobMap) throws SchedulerException {
        TaskInfo info = new TaskInfo();
        info.setIntervalTime(getRealTime(interval, timeUnit));
        info.setStartAT(startDate);
        info.setCount(count - 1);
        info.setJobClass(jobClass);
        info.setJobMap(jobMap);
        commonTimmer(info);
    }

    /**
     * 添加复杂定时任务
     * cron可以在线生成
     */
    public void cornTimmer(String corn, Class<? extends Job> jobClass, JobDataMap jobMap) throws SchedulerException {
        TaskInfo info = new TaskInfo();
        info.setCorn(corn);
        info.setJobClass(jobClass);
        info.setJobMap(jobMap);
        commonTimmer(info);
    }


}
