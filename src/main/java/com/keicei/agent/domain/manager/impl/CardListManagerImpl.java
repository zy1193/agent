package com.keicei.agent.domain.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.domain.entity.Card;
import com.keicei.agent.domain.manager.CardListManager;
import com.keicei.agent.persistence.mapper.CardList;
import com.keicei.util.PaginationUtil;

@Service("cardlistManager")
public class CardListManagerImpl implements CardListManager {

	@Resource
	private CardList cardlistmapper;

	@Transactional(readOnly = true)
	@Override
	public int count(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return cardlistmapper.count(parameters);
	}

	@Override
	public int delete(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(Card entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	//@Transactional(readOnly=true)
	@Override
	public List<Card> list(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly=true)
	@Override
	public List<Card> list(Map<String, Object> parameters, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		PaginationUtil.generatePageParameter(parameters, page, pageSize);
		return  cardlistmapper.list(parameters);
	}

	@Override
	public Card select(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Card entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	@Override
	public void changeStatus(Card card) {
		// TODO Auto-generated method stub
		 cardlistmapper.changeStatus(card);
	}

}
