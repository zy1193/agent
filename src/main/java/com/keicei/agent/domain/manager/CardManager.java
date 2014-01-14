package com.keicei.agent.domain.manager;

import java.util.List;
import java.util.Map;

import com.keicei.agent.domain.entity.BuycardOrder;
import com.keicei.agent.domain.entity.Card;
import com.keicei.agent.persistence.mapper.CardMapper;
import com.keicei.common.BaseManager;

public interface CardManager extends BaseManager<Card, Integer, CardMapper> {

	/** 购卡 **/
	int buycard(String brandid, String productId, String agentId,
			String password, int count);

	/** 保存购卡订单 **/
	int insertBuycardOrder(BuycardOrder buycardOrder);

	/** 修改购卡订单状态 **/
	int updateBuycardOrderStatus(BuycardOrder buycardOrder);

	Card selectByNo(String no, String agentId);

	Card selectByPassword(String password);

	/** 充值统计 **/
	List<Card> listRechargeStat(Map<String, Object> map);

	List<Card> listRechargeStat(Map<String, Object> map, int page, int pageSize);
	
	
	/**
	 * 查询从开始考号到结束考号的所有卡的卡密码
	 * @param agentId:代理ID
	 * @param begin：开始卡号
	 * @param end：结束卡卡号
	 * @param recharge_status：充值状态
	 * @param status：卡状态
	 * @param branid：产品ID
	 */
	List<Card> getCardPassFromBeginToEndNo(String agentId, String begin, String end,
			String recharge_status, String status,String brandid);

	List<Card> getCardPassFromCardsNo(String agentId, List<String> cardsNo,
			String brandid);

	void frozenOrThawCard(List<String> cardList, String status,String brandid);
}
