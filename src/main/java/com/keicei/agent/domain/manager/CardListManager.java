package com.keicei.agent.domain.manager;

import com.keicei.agent.domain.entity.Card;
import com.keicei.agent.persistence.mapper.CardList;
import com.keicei.common.BaseManager;

public interface CardListManager extends BaseManager<Card, String, CardList> {
	/**
	 * 修改卡的状态
	 * 
	 * @param card
	 */
	void changeStatus(Card card);
	
	
}
