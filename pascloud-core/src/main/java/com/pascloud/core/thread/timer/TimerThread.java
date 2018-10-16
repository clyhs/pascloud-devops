package com.pascloud.core.thread.timer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pascloud.core.thread.ServerThread;


/**
 *  定时任务
 * @author chenly
 *
 */
public class TimerThread extends Timer {
	
	private static final Logger log = LoggerFactory.getLogger(TimerThread.class);
	
	// 定时器依附的线程
    private final ServerThread serverThread;
    // 定时器任务
    private final Collection<TimerEvent> events = Collections.synchronizedCollection(new ArrayList<TimerEvent>());

    // 定时器中记录的定时器类型
    private final HashSet<String> classLogNames = new HashSet<>();

    // 使用JDK TimerTask
    private TimerTask task;
	
	public TimerThread(ServerThread serverThread, Class<? extends TimerEvent>... classLogNames) {
		super(serverThread.getThreadName() + "-Timer");
        this.serverThread = serverThread;

        for (Class<? extends TimerEvent> cls : classLogNames) {
            this.classLogNames.add(cls.getSimpleName());
        }

        log.info("TimerThread:TimerThread = " + serverThread.getThreadName());
		
	}
	
	public void start() {
        log.info("TimerThread:start=" + this.serverThread.getThreadName() + "=heart=" + this.serverThread.getHeart());
        this.task = new TimerTask() {
            @Override
            public void run() {
                synchronized (TimerThread.this.events) {
                    Iterator<TimerEvent> it = TimerThread.this.events.iterator();
                    while (it.hasNext()) {
                        TimerEvent event = it.next();
                        if (event.remain() >= 0L) {
                            if (event.getLoop() > 0) {
                                event.setLoop(event.getLoop() - 1);
                                TimerThread.this.serverThread.execute(event);
                            } else if (event.getLoop() < 0) {
                                TimerThread.this.serverThread.execute(event);
                            }
                        } else {
                            TimerThread.this.serverThread.execute(event);
                            event.setLoop(0);
                        }
                        if (event.getLoop() == 0) {
                            it.remove();
                            if (TimerThread.this.classLogNames.contains(event.getClass().getName())) {
                                log.error("故意抛出的异常,以做提醒" + Thread.currentThread().getName() + " " + Thread.currentThread().getThreadGroup() + " " + Thread.currentThread());
                            }
                        }
                    }
                }
            }
        };
        schedule(this.task, 0L, this.serverThread.getHeart());
        log.info("TimerThread:end=" + this.serverThread.getThreadName() + "=heart=" + this.serverThread.getHeart());
    }

    public void stop(boolean flag) {
        synchronized (this.events) {
            this.events.clear();
            log.error("故意抛出的异常,以做提醒" + Thread.currentThread().getName() + " " + Thread.currentThread().getThreadGroup() + " " + Thread.currentThread());
            if (this.task != null) {
                this.task.cancel();
            }
            cancel();
        }
        int sign = flag ? 1 : 0;
        log.error("TimerThread:stop=" + this.serverThread.getThreadName() + "=flag=" + sign);
    }
	
	
	public void addTimerEvent(TimerEvent event) {
        synchronized (this.events) {
            this.events.add(event);
        }
        if (classLogNames.contains(event.getClass().getName())) {
            log.error("TimerThread:addTimerEvent=" + this.serverThread.getThreadName() + "=event=" + event.getClass().getName());
        }
    }

    public void removeTimerEvent(TimerEvent event) {
        synchronized (this.events) {
            this.events.remove(event);
            if (classLogNames.contains(event.getClass().getName())) {
                log.error("故意抛出的异常,以做提醒" + Thread.currentThread().getName() + " " + Thread.currentThread().getThreadGroup() + " " + Thread.currentThread());
            }
        }
    }

}
