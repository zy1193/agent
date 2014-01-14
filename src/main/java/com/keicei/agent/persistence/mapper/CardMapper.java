package com.keicei.agent.persistence.mapper;

import java.util.List;
import java.util.Map;

import com.keicei.agent.domain.entity.Card;
import com.keicei.common.BaseMapper;

public interface CardMapper extends BaseMapper<Card, Integer> {
	Card selectByNo(Map<String, String> map);

	Card selectByPassword(Card card);

	/**
	 * 查询出一个代理商全部的可以冻结or解冻的充值卡
	 * 
	 * @param card
	 * @return
	 */
	List<Card> tobeSwitch(Card card);

	/**
	 * 冻结or解冻一个代理商的所有卡
	 * 
	 * @param card
	 * @return
	 */
	int switchStatus(Card card);

	List<Card> listRechargeStat(Map<String, Object> map);

	/**
	 * 查询开始卡号与结束卡号之间的所有卡的卡密码
	 * @param map
	 * @return
	 */
	List<Card> getCardPassFromBeginToEndNo(Map<String, String> map);

	/**
	 * 查询指定考号的卡密码
	 * @param map
	 * @return
	 */
	List<Card> getCardPassFromCardsNo(Map<String, Object> map);

	void switchStatusByPass(Map<String,Object>map);

}
