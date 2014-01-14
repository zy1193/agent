package com.keicei.agent.domain.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.domain.entity.User;
import com.keicei.agent.domain.manager.UserManager;
import com.keicei.agent.persistence.mapper.UserMapper;

@Service("userManager")
public class UserManagerImpl implements UserManager {

	@Resource
	private UserMapper userMapper;

	@Transactional
	@Override
	public int insert(User entity) {
		return userMapper.insert(entity);
	}

	@Transactional
	@Override
	public int update(User entity) {
		return userMapper.update(entity);
	}

	@Transactional
	@Override
	public int delete(String key) {
		return userMapper.delete(key);
	}

	@Transactional(readOnly = true)
	@Override
	public User select(String key) {
		return userMapper.select(key);
	}

	@Transactional(readOnly = true)
	@Override
	public List<User> list(Map<String, Object> parameters) {
		return userMapper.list(parameters);
	}

	@Override
	public List<User> list(Map<String, Object> parameters, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	@Override
	public void changePassword(User user) {
		userMapper.changePassword(user);
	}
}
