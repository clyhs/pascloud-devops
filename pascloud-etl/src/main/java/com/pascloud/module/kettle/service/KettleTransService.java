package com.pascloud.module.kettle.service;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.LogChannel;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.repository.RepositoryDirectoryInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransConfiguration;
import org.pentaho.di.trans.TransExecutionConfiguration;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.www.CarteObjectEntry;
import org.pentaho.di.www.TransformationMap;

import com.pascloud.utils.StrUtil;


/**
 * 
 * @author chenly
 * 
 * @version createtime:2016-6-27 上午10:04:50
 */
public class KettleTransService {

	private static TransformationMap transMap = new TransformationMap();
	private KettleRepositoryService krs;
	private static LogChannel logChannel = new LogChannel("spoon");

	public KettleTransService(KettleRepositoryService krs) {
		this.krs = krs;
	}

	public void execute(String repName, String username, String password,
			String filePath, String fileName) {
		Repository r = null;
		RepositoryDirectoryInterface rdi = null;
		JobMeta jobMeta = new JobMeta();
		try {
			r = krs.getRepository(repName, username, password);
			rdi = krs.getDirectory(r, filePath);
			TransMeta tm = r
					.loadTransformation(fileName, rdi, null, true, null);
			executeTrans(tm);
		} catch (Exception e) {

		}

	}

	public void executeTrans(TransMeta tm) {

		Trans trans = new Trans(tm);
		TransConfiguration tc = new TransConfiguration(tm,
				new TransExecutionConfiguration());
		try {

			String startTime = StrUtil
					.getSysdateToString("yyyy/MM/dd HH:mm:ss.SSS");
			String transName = tm.getName();
			String objId = null;
			if (tm.getObjectId() != null) {
				objId = new CarteObjectEntry(transName, tm.getObjectId()
						.toString()).getId();
			} else {
				objId = tm.getFilename();
			}
			transMap.addTransformation(transName, objId, trans, tc);
			trans.execute(null);
			// trans.waitUntilFinished();

			//trans.beginProcessing();
			//trans.execute(null);
			
			//trans.startThreads();
			
			trans.waitUntilFinished();
			logChannel.logMinimal("ETL--JOB Finished!");
			String endTime = StrUtil
					.getSysdateToString("yyyy/MM/dd HH:mm:ss.SSS");
			logChannel.logMinimal("ETL--JOB Start=" + startTime + ", Stop="
					+ endTime);
			Long l = StrUtil.StringToDate(endTime, "yyyy/MM/dd HH:mm:ss.SSS")
					.getTime()
					- StrUtil
							.StringToDate(startTime, "yyyy/MM/dd HH:mm:ss.SSS")
							.getTime();
			logChannel.logMinimal("ETL--JOB Processing ended after " + l
					/ 1000L + " seconds.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

	public void stopTrans(String transName) {
		try {
			Trans trans = transMap.getTransformation(transName);
			if (trans.isRunning()) {
				trans.stopAll();
			}
		} catch (Exception ex) {

		}
	}
	
	public void pauseTrans(String transName) {
		try {
			Trans trans = transMap.getTransformation(transName);
			if (trans.isRunning()) {
				trans.pauseRunning();
				System.out.println("pause");
			}
		} catch (Exception ex) {

		}
	}
	
	public void resumeTrans(String transName) {
		try {
			Trans trans = transMap.getTransformation(transName);
			
			if (trans.isPaused()) {
				trans.resumeRunning();
				System.out.println("resume");
			}
		} catch (Exception ex) {

		}
	}
	
	

}