package com.keicei.agent.persistence.mapper;

import com.keicei.agent.domain.entity.Card;
import com.keicei.common.BaseMapper;

public interface CardList extends BaseMapper<Card, String> {
	/**
	 * 修改卡的状态
	 * 
	 * @param card
	 */
	void changeStatus(Card card);

}
