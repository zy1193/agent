package com.keicei.agent.domain.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.domain.entity.Card;
import com.keicei.agent.domain.manager.StockStatManager;
import com.keicei.agent.persistence.mapper.CardMapper;

@Service("stockStatManager")
public class StockStatManagerImpl implements StockStatManager {

	@Resource
	private CardMapper cardMapper;

	@Override
	public int count(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Card entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional(readOnly = true)
	@Override
	public List<Card> list(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return cardMapper.list(parameters);
	}

	@Override
	public List<Card> list(Map<String, Object> parameters, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card select(Integer key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Card entity) {
		// TODO Auto-generated method stub
		return 0;
	}

}
