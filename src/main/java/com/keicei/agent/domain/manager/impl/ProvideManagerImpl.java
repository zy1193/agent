package com.keicei.agent.domain.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.manager.ProvideManager;
import com.keicei.agent.persistence.mapper.Provide;

@Service("provideManager")
public class ProvideManagerImpl implements ProvideManager {
	
	@Resource
	private Provide providemapper;
	
	@Override
	public int count(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Agent entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Agent> list(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Agent> list(Map<String, Object> parameters, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Agent select(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Agent entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Agent> listSubAgent(String id) {
		// TODO Auto-generated method stub
		return providemapper.listSubAgent(id);
	}

}
