package com.pascloud.vo.pass;

import java.io.Serializable;

public class PasConfigVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ZookeeperVo zookeeper;
	
	private MycatVo     mycat;
	
	private RedisVo     redis;
	
	private MqVo        mq;

	public ZookeeperVo getZookeeper() {
		return zookeeper;
	}

	public void setZookeeper(ZookeeperVo zookeeper) {
		this.zookeeper = zookeeper;
	}

	public MycatVo getMycat() {
		return mycat;
	}

	public void setMycat(MycatVo mycat) {
		this.mycat = mycat;
	}

	public RedisVo getRedis() {
		return redis;
	}

	public void setRedis(RedisVo redis) {
		this.redis = redis;
	}

	public MqVo getMq() {
		return mq;
	}

	public void setMq(MqVo mq) {
		this.mq = mq;
	}
	
	

}
