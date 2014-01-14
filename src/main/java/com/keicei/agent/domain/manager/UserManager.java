package com.keicei.agent.domain.manager;


import com.keicei.agent.domain.entity.User;
import com.keicei.agent.persistence.mapper.UserMapper;
import com.keicei.common.BaseManager;

public interface UserManager extends BaseManager<User, String, UserMapper> {
	void changePassword(User user);
}
