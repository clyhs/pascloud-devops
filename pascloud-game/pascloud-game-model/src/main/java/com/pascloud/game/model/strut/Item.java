package com.pascloud.game.model.strut;

import java.util.Date;

import org.mongodb.morphia.annotations.Entity;
import org.redisson.api.RRemoteService;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pascloud.core.redis.jedis.JedisManager;
import com.pascloud.core.utils.JsonUtil;
import com.pascloud.game.model.constant.Config;
import com.pascloud.game.model.redis.key.HallKey;

/**
 * 道具 <br>
 * redis实时存储
 * <p> 暂时各个服务器各种修改，可以考虑再通过redisson {@link RRemoteService} 实现在大厅处理</p>
 * @author admin
 *
 */
@JSONType(serialzeFeatures = SerializerFeature.WriteClassName)
@Entity(value = "item", noClassnameStored = true)
public class Item {

	@JSONField
	private long id;
	/**配置Id*/
	@JSONField
	private int configId;
	/**数量*/
	@JSONField
	private int num;
	/**创建时间*/
	@JSONField
	private Date createTime;

	public Item() {
		this.id = Config.getId();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getConfigId() {
		return configId;
	}

	public void setConfigId(int configId) {
		this.configId = configId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return JsonUtil.toJSONString(this);
	}

	public void saveToRedis(long roleId) {
		String key = HallKey.Role_Map_Packet.getKey(roleId);
		JedisManager.getJedisCluster().hset(key, String.valueOf(this.id), JsonUtil.toJSONString(this));
	}
}
