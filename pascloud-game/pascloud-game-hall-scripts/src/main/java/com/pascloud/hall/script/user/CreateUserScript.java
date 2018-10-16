package com.pascloud.hall.script.user;

import java.util.function.Consumer;

import com.pascloud.game.model.mongo.hall.dao.HallInfoDao;
import com.pascloud.game.model.strut.User;
import com.pascloud.hall.script.IUserScript;

public class CreateUserScript implements IUserScript {

	@Override
	public User createUser(Consumer<User> userConsumer) {
		User user = new User();
		user.setId(HallInfoDao.getUserId());
		if (userConsumer != null) {
			userConsumer.accept(user);
		}
		return user;
	}

}