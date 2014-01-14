package com.keicei.agent.domain.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.domain.entity.AcctTransLog;
import com.keicei.agent.domain.manager.AcctTransLogManager;
import com.keicei.agent.persistence.mapper.AcctTransLogMapper;
import com.keicei.util.PaginationUtil;

@Service("acctTransLogManager")
public class AcctTransLogManagerImpl implements AcctTransLogManager {

	@Resource
	private AcctTransLogMapper acctTransLogMapper;

	@Transactional(readOnly = true)
	@Override
	public int count(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return acctTransLogMapper.count(parameters);
	}

	@Override
	public int delete(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(AcctTransLog entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AcctTransLog> list(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<AcctTransLog> list(Map<String, Object> parameters, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		PaginationUtil.generatePageParameter(parameters, page, pageSize);
		return acctTransLogMapper.list(parameters);
	}

	@Override
	public AcctTransLog select(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(AcctTransLog entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AcctTransLog> listaccTransLog(Map<String, Object> map) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AcctTransLog> listaccTransLog(Map<String, Object> parameters,
			int page, int pageSize) {
		PaginationUtil.generatePageParameter(parameters, page, pageSize);
		return acctTransLogMapper.listaccTransLog(parameters);
	}

	@Override
	@Transactional(readOnly = true)
	public int counts(Map<String, Object> map) {
		return acctTransLogMapper.counts(map);
	}

	@Transactional(readOnly = true)
	@Override
	public int count1(Map<String, Object> map) {
		return acctTransLogMapper.count1(map);
	}

	@Transactional(readOnly = true)
	@Override
	public List<AcctTransLog> list1(Map<String, Object> map, int page,
			int pageSize) {
		PaginationUtil.generatePageParameter(map, page, pageSize);
		return acctTransLogMapper.list1(map);
	}

}
