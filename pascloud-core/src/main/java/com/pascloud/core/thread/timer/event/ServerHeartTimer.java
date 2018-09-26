package com.pascloud.core.thread.timer.event;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.script.ITimerEventScript;
import com.pascloud.core.script.ScriptManager;
import com.pascloud.core.thread.timer.TimerEvent;

public class ServerHeartTimer extends TimerEvent {

	public static boolean GLOBAL_PROTECT = false;

	protected static Logger log = LoggerFactory.getLogger(ServerHeartTimer.class);

	protected int hour = -1;
	protected int min = -1;
	protected int sec = -1;
	protected int date = -1;

	public ServerHeartTimer() {
	}

	@Override
	public void run() {
		LocalTime localTime = LocalTime.now();
		int _sec = localTime.getSecond();
		if (sec != _sec) { // 每秒钟执行
			sec = _sec;
			ScriptManager.getInstance().getBaseScriptEntry().executeScripts(ITimerEventScript.class,
					(ITimerEventScript script) -> {
						long start = System.currentTimeMillis();
						script.secondHandler(localTime);
						long executeTime = System.currentTimeMillis() - start;
						if (executeTime > 50) {
							log.warn("定时脚本[{}] 执行{}ms", script.getClass().getName(), executeTime);
						}
					});
		}
		int _min = localTime.getMinute();
		if (min != _min) { // 每分钟执行
			min = _min;
			ScriptManager.getInstance().getBaseScriptEntry().executeScripts(ITimerEventScript.class,
					(ITimerEventScript script) -> {
						long start = System.currentTimeMillis();
						script.minuteHandler(localTime);
						long executeTime = System.currentTimeMillis() - start;
						if (executeTime > 50) {
							log.warn("定时脚本[{}] 执行{}ms", script.getClass().getName(), executeTime);
						}
					});
		}
		int _hour = localTime.getHour();
		if (hour != _hour) { // 每小时执行
			hour = _hour;
			ScriptManager.getInstance().getBaseScriptEntry().executeScripts(ITimerEventScript.class,
					(ITimerEventScript script) -> {
						long start = System.currentTimeMillis();
						script.hourHandler(localTime);
						long executeTime = System.currentTimeMillis() - start;
						if (executeTime > 50) {
							log.warn("定时脚本[{}] 执行{}ms", script.getClass().getName(), executeTime);
						}
					});
		}
	}
}