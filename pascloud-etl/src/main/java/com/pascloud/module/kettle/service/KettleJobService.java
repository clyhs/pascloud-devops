package com.pascloud.module.kettle.service;
import org.pentaho.di.core.logging.LogChannel;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobConfiguration;
import org.pentaho.di.job.JobExecutionConfiguration;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.www.CarteObjectEntry;
import org.pentaho.di.www.JobMap;

import com.pascloud.utils.StrUtil;



/**
 * 
 * @author chenly 
 *
 * @version createtime:2016-6-27 上午10:05:03
 */
public class KettleJobService {

	private static JobMap jobMap = new JobMap();
	
	private  static LogChannel logChannel = new  LogChannel("spoon");

	private KettleRepositoryService krs;

	public KettleJobService(KettleRepositoryService krs) {
		this.krs = krs;
	}

	public void execute(String repName, String username, String password,
			String filePath, String fileName) {
		Repository r = null;
		RepositoryDirectoryInterface rdi = null;
		JobMeta jobMeta = new JobMeta();
		System.out.println("**********job start:"+filePath+"**********");
		try {
			r = krs.getRepository(repName, username, password);
			rdi = krs.getDirectory(r, filePath);
			System.out.println("************"+rdi.getPath()+"*****************");
			jobMeta = r.loadJob(fileName, rdi, null, null);

			executeJob(r, jobMeta);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void executeJob(Repository rep, JobMeta jobMeta) {

		Job job = null;
		JobConfiguration jc =new JobConfiguration(jobMeta, new JobExecutionConfiguration());
		try {
			if (rep != null) {
				job = new Job(rep, jobMeta);
				//job.start();
				//job.waitUntilFinished();
				String startTime = StrUtil.getSysdateToString("yyyy/MM/dd HH:mm:ss.SSS");
				String jobName = job.getJobname();
				String objId = null;
				if(job.getObjectId()!=null){
					objId = new CarteObjectEntry(jobName, job.getObjectId().toString()).getId();
				}else{
					objId = job.getFilename();
				}
				jobMap.addJob(jobName, objId , job, jc);
				//job.beginProcessing();
				//job.run();
				job.start();
				//job.execute(0, null);
				//job.start();
				//job.suspend();
				//job.stopAll();
				//job.resume();
				job.waitUntilFinished();
				//job.stopAll();
				logChannel.logMinimal("ETL--JOB Finished!");
				String endTime = StrUtil.getSysdateToString("yyyy/MM/dd HH:mm:ss.SSS");
				logChannel.logMinimal("ETL--JOB Start=" + startTime + ", Stop=" + endTime);
				Long l = StrUtil.StringToDate(endTime, "yyyy/MM/dd HH:mm:ss.SSS").getTime() - StrUtil.StringToDate(startTime, "yyyy/MM/dd HH:mm:ss.SSS").getTime();
				logChannel.logMinimal("ETL--JOB Processing ended after " + l / 1000L + " seconds.");
			}
		} catch (Exception e) {

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