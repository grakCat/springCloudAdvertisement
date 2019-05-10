package com.stude.timer_task.quartz;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.simpl.RAMJobStore;
import org.quartz.simpl.SimpleThreadPool;
import org.quartz.spi.JobStore;

/**
 * Created on 2019/5/10.
 *
 * 一种单例调度方法
 * @author hy
 * @since 1.0
 */
public class DirectSchedulerService {

    public static void main(String[] args) throws SchedulerException {
        // 创建线程池
        SimpleThreadPool threadPool = new SimpleThreadPool(20, Thread.NORM_PRIORITY);
        threadPool.initialize();

        // 创建job存储器
        JobStore jobStore = new RAMJobStore();

        //使用所有参数创建调度程序
        DirectSchedulerFactory.getInstance().createScheduler("My Quartz Scheduler", "My Instance",threadPool, jobStore);

        // 不要忘了调用start()方法来启动调度程序
        Scheduler scheduler = DirectSchedulerFactory.getInstance().getScheduler("My Quartz Scheduler");
        scheduler.start();
    }



}
