package com.keicei.agent.persistence.mapper;

import com.keicei.agent.domain.entity.User;
import com.keicei.common.BaseMapper;

public interface UserMapper extends BaseMapper<User, String> {
	void changePassword(User user);
}
