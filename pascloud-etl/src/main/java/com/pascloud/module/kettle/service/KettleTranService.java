package com.pascloud.module.kettle.service;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pascloud.utils.PasCloudCode;
import com.pascloud.utils.StrUtil;
import com.pascloud.vo.result.ResultCommon;

/**
 * 
 * @author chenly
 *
 * date: 2018年12月5日 下午12:27:36 <br/> 
 *
 */
@Service
public class KettleTranService {
	
	private static Logger log = LoggerFactory.getLogger(KettleTranService.class);
	
	private static TransformationMap transMap = new TransformationMap();
	
	@Autowired
	private KettleRepoService kettleRepoService;
	
	private static LogChannel logChannel = new LogChannel("spoon");

	
	/**
	 * 执行转换
	 * @param repName
	 * @param username
	 * @param password
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	public ResultCommon execute(String repName, String username, String password,
			String filePath, String fileName) {
		ResultCommon result = new ResultCommon(PasCloudCode.SUCCESS);
		Repository r = null;
		RepositoryDirectoryInterface rdi = null;
		try {
			r = kettleRepoService.getRepository(repName, username, password);
			rdi =kettleRepoService.getRepositoryDirectoryInterface(r, filePath);
			TransMeta tm = r
					.loadTransformation(fileName, rdi, null, true, null);
			executeTrans(tm);
		} catch (Exception e) {
			log.info(e.getMessage());
			result = new ResultCommon(PasCloudCode.EXCEPTION.getCode(),e.getMessage());

		}
		return result;
	}

	private void executeTrans(TransMeta tm) {

		Trans trans = new Trans(tm);
		TransConfiguration tc = new TransConfiguration(tm,new TransExecutionConfiguration());
		try {

			String startTime = StrUtil.getSysdateToString("yyyy/MM/dd HH:mm:ss.SSS");
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
			trans.waitUntilFinished();
			logChannel.logMinimal("工作完成!");
			String endTime = StrUtil.getSysdateToString("yyyy/MM/dd HH:mm:ss.SSS");
			logChannel.logMinimal("工作开始时间=" + startTime + ", 线束时间=" + endTime);
			Long l = StrUtil.StringToDate(endTime, "yyyy/MM/dd HH:mm:ss.SSS").getTime() - StrUtil.StringToDate(startTime, "yyyy/MM/dd HH:mm:ss.SSS").getTime();
			logChannel.logMinimal("工作执行总共" + l / 1000L + " 秒.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
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
