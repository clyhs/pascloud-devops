package com.pascloud.module.etl.task;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pascloud.bean.job.ScheduleJob;


/**
 * 定时任务工作类
 */
@DisallowConcurrentExecution  
public class TaskA implements Job {
	 
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");    
	    System.out.println("任务名称 = [" + scheduleJob.getName() + "]"+ " 在 " + dateFormat.format(new Date())+" 时运行"); 
    }
}
