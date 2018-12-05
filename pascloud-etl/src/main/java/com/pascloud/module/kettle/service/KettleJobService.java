package com.pascloud.module.kettle.service;

import org.pentaho.di.core.logging.LogChannel;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobConfiguration;
import org.pentaho.di.job.JobExecutionConfiguration;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.www.CarteObjectEntry;
import org.pentaho.di.www.JobMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.StrUtil;
import com.pascloud.vo.result.ResultCommon;

/**
 * kettle工作执行接口
 * @author chenly
 *
 * date: 2018年12月5日 上午11:54:56 <br/> 
 *
 */
@Service
public class KettleJobService {
	
	private static Logger log = LoggerFactory.getLogger(KettleJobService.class);
	
	@Autowired
	private KettleRepoService kettleRepoService;
	
	private  static LogChannel logChannel = new  LogChannel("spoon");
	
	private static JobMap jobMap = new JobMap();
	
	/**
	 * 执行工作
	 * @param name
	 * @param username
	 * @param password
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public ResultCommon execute(String name, String username, String password,
			String filePath, String fileName) {
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		Repository r = null;
		RepositoryDirectoryInterface rdi = null;
		JobMeta jobMeta = new JobMeta();
		try {
			r = kettleRepoService.getRepository(name, username, password);
			rdi = kettleRepoService.getRepositoryDirectoryInterface(r, filePath);
			jobMeta = r.loadJob(fileName, rdi, null, null);
			executeJob(r, jobMeta);
		} catch (Exception e) {
			log.info(e.getMessage());
			result = new ResultCommon(PasCloudCode.EXCEPTION.getCode(),e.getMessage());
		}
		return result;
	}
	
	
	/**
	 * 执行工作
	 * @param rep
	 * @param jobMeta
	 */
	private void executeJob(Repository rep, JobMeta jobMeta) {
		Job job = null;
		JobConfiguration jc =new JobConfiguration(jobMeta, new JobExecutionConfiguration());
		try {
			if (rep != null) {
				job = new Job(rep, jobMeta);
				String startTime = StrUtil.getSysdateToString("yyyy/MM/dd HH:mm:ss.SSS");
				String jobName = job.getJobname();
				String objId = null;
				if(job.getObjectId()!=null){
					objId = new CarteObjectEntry(jobName, job.getObjectId().toString()).getId();
				}else{
					objId = job.getFilename();
				}
				jobMap.addJob(jobName, objId , job, jc);
				job.start();
				job.waitUntilFinished();
				//job.stopAll();
				logChannel.logMinimal("工作完成!");
				String endTime = StrUtil.getSysdateToString("yyyy/MM/dd HH:mm:ss.SSS");
				logChannel.logMinimal("工作开始时间=" + startTime + ", 线束时间=" + endTime);
				Long l = StrUtil.StringToDate(endTime, "yyyy/MM/dd HH:mm:ss.SSS").getTime() - StrUtil.StringToDate(startTime, "yyyy/MM/dd HH:mm:ss.SSS").getTime();
				logChannel.logMinimal("工作执行总共" + l / 1000L + " 秒.");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void stopJob(String jobName) {

		try {
			Job job = jobMap.getJob(jobName);
			//job.suspend();
			job.stopAll();
			System.out.println("stop");
			
			
		} catch (Exception ex) {

		}

	}
	
	public void suspendJob(String jobName) {
		try {
			Job job  = jobMap.getJob(jobName);
			
			//if(job.isAlive()){
				job.suspend();
				System.out.println("stop");
			//}
			
		} catch (Exception ex) {

		}
	}
	
	public void resumeJob(String jobName) {
		try {
			Job job  = jobMap.getJob(jobName);
			
			//if(job.isStopped()){
				job.resume();
				System.out.println("resume");
			//}
		} catch (Exception ex) {

		}
	}
	
	

}
