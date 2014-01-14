package com.keicei.agent.domain.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.domain.entity.BuycardOrder;
import com.keicei.agent.domain.manager.BuycardManager;
import com.keicei.agent.persistence.mapper.Buycard;
import com.keicei.util.PaginationUtil;

@Service("buycardManager")
public class BuycardManagerImpl implements BuycardManager {

	@Resource
	private Buycard buycardmapper;

	@Transactional(readOnly = true)
	@Override
	public int count(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return buycardmapper.count(parameters);
	}

	@Override
	public int delete(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(BuycardOrder entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BuycardOrder> list(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<BuycardOrder> list(Map<String, Object> parameters, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		PaginationUtil.generatePageParameter(parameters, page, pageSize);
		return buycardmapper.list(parameters);
	}

	@Override
	public BuycardOrder select(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(BuycardOrder entity) {
		// TODO Auto-generated method stub
		return 0;
	}

}
