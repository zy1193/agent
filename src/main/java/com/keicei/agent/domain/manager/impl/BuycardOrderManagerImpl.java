package com.keicei.agent.domain.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.domain.entity.BuycardOrder;
import com.keicei.agent.domain.manager.BuycardOrderManager;
import com.keicei.agent.persistence.mapper.BuycardOrderMapper;
import com.keicei.util.PaginationUtil;

/**
 *@author guoqidi
 *@date 2011-9-5 下午03:00:00
 *@version V1.1
 */
@Service("buycardOrderManager")
public class BuycardOrderManagerImpl implements BuycardOrderManager{

	@Resource
	private BuycardOrderMapper buycardOrderMapper;
	@Override
	public int insert(BuycardOrder entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(BuycardOrder entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BuycardOrder select(Integer key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BuycardOrder> list(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public List<BuycardOrder> list(Map<String, Object> parameters, int page,
			int pageSize) {
		PaginationUtil.generatePageParameter(parameters, page, pageSize);
		return buycardOrderMapper.list(parameters);
	}

	@Override
	@Transactional(readOnly=true)
	public int count(Map<String, Object> parameters) {
		return buycardOrderMapper.count(parameters);
	}

}
