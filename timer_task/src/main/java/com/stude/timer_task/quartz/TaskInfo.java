package com.stude.timer_task.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created on 2018/11/9.
 *
 * @author grayCat
 * @since 1.0
 */
public class TaskInfo {

    private String name;

    private String group;
    /*开始时间*/
    private Date startAT;
    /*结束时间*/
    private Date endAT;
    /*执行次数*/
    private int count;
    /*间隔时间*/
    private long intervalTime;
    /*时间单位*/
    private TimeUnit intervalUnit;
    /*一直循环执行*/
    private boolean forever;
    /*复杂定时器的corn表达式，没有代表普通定时器*/
    private String corn;
    /*定时任务执行类*/
    private Class<? extends Job> jobClass;
    /*定时器内传递的消息*/
    private JobDataMap jobMap;

    public JobDataMap getJobMap() {
        return jobMap;
    }

    public void setJobMap(JobDataMap jobMap) {
        this.jobMap = jobMap;
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

    public void setGroup(String group) {
        this.group = group;
    }

    public Date getStartAT() {
        return startAT;
    }

    public void setStartAT(Date startAT) {
        this.startAT = startAT;
    }

    public Date getEndAT() {
        return endAT;
    }

    public void setEndAT(Date endAT) {
        this.endAT = endAT;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public TimeUnit getIntervalUnit() {
        return intervalUnit;
    }

    public void setIntervalUnit(TimeUnit intervalUnit) {
        this.intervalUnit = intervalUnit;
    }

    public boolean isForever() {
        return forever;
    }

    public void setForever(boolean forever) {
        this.forever = forever;
    }

    public String getCorn() {
        return corn;
    }

    public void setCorn(String corn) {
        this.corn = corn;
    }

    public Class<? extends Job> getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class<? extends Job> jobClass) {
        this.jobClass = jobClass;
    }
}
