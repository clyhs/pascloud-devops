package com.pascloud.game.model.mongo.bydr.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.pascloud.game.model.mongo.IConfigChecker;

/**
 * 房间配置
 */
@Entity(value="c_room",noClassnameStored=true)
public class CRoom implements IConfigChecker {
	@Id
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
