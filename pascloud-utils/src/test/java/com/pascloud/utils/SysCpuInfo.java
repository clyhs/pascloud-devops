package com.pascloud.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class SysCpuInfo {

	private int CPU_IDLE = 0;
	int processStatus = 0;

	public SysCpuInfo(Session session) {
		InputStream is = null;
		BufferedReader brStat = null;
		StringTokenizer tokenStat = null;
		Session sess = null;
		String str = "";
		int i = 0, j = 0, cpuidle = 0;

		/**
		 * 对于执行linux shell.
		 */
		try {
			sess = session;
			sess.execCommand("vmstat 2 10 ");
			/**
			 * 执行vmstat命令获得系统CPU负载情况,vmstat 2 10表示2秒钟输出一次共输出10组数据 显示结果如下:
			 * [mon724@v0A-202-40-18 ~]$ vmstat 2 10 procs
			 * -----------memory---------- ---swap-- -----io---- --system--
			 * -----cpu------ r b swpd free buff cache si so bi bo in cs us sy
			 * id wa st 1 0 41328 58860 199292 1877728 0 0 2 23 0 0 2 0 98 0 0 0
			 * 0 41328 58744 199292 1877884 0 0 0 0 1080 1057 3 0 96 0 0 0 0
			 * 41328 58084 199300 1878036 0 0 0 250 1310 1258 6 0 94 0 0 0 0
			 * 41328 57844 199300 1878148 0 0 0 32 761 697 3 0 97 0 0 0 0 41328
			 * 57852 199304 1878224 0 0 0 214 630 593 1 1 98 0 0 0 0 41328 56984
			 * 199304 1878372 0 0 0 50 1033 881 6 0 94 0 0 0 0 41328 56860
			 * 199304 1878440 0 0 0 0 536 578 2 0 98 0 0 1 0 41328 56868 199308
			 * 1878552 0 0 0 200 545 581 1 0 99 0 0 0 0 41328 56876 199308
			 * 1878644 0 0 0 102 628 663 1 0 99 0 0 0 0 41328 56876 199308
			 * 1878696 0 0 0 118 615 580 3 0 96 0 0
			 */
			is = new StreamGobbler(sess.getStdout());

			brStat = new BufferedReader(new InputStreamReader(is));
			/*
			 * 先读取第一行Title信息 procs -----------memory---------- ---swap--
			 * -----io---- --system-- -----cpu------
			 */
			brStat.readLine();
			/*
			 * 读取第二行Title信息读取第三行信息 r b swpd free buff cache si so bi bo in cs us
			 * sy id wa st
			 */
			brStat.readLine();

			/*
			 * 读取第三行信息 1 0 41328 58860 199292 1877728 0 0 2 23 0 0 2 0 98 0 0
			 * 注意每次执行vmstat命令时,此行信息基本不变,因此不做为抽取数据使用
			 */
			brStat.readLine();
			/*
			 * 读取第4行到第12行信息
			 */
			for (j = 0; j < 9; j++) {
				str = brStat.readLine();
				if (str == null)
					break;
				tokenStat = new StringTokenizer(str);
				for (i = 0; i < 14; i++) {
					tokenStat.nextToken();
				}
				cpuidle = cpuidle + new Integer(tokenStat.nextToken()).intValue();
			}
			CPU_IDLE = new Double(cpuidle / 9).intValue();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public int getCPUInfo() {
		return CPU_IDLE;
	}

}
