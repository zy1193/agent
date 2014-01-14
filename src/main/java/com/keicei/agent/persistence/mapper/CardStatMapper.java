package com.keicei.agent.persistence.mapper;

import java.util.List;

import com.keicei.agent.domain.entity.CardStat;

public interface CardStatMapper {
	/** 购卡统计 **/
	List<CardStat> buyStat(CardStat cardStat);
}
