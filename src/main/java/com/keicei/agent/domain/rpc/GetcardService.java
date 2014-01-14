package com.keicei.agent.domain.rpc;

import java.util.List;

import com.keicei.agent.domain.entity.Card;

/**
 * 提卡
 * 
 * @author ZHANGYAN
 * 
 */
public interface GetcardService {
	/**
	 * 提卡
	 * 
	 * @param orderSn
	 *            订单编号
	 * @param agentId
	 *            代理商编号
	 * @param productId
	 *            商品编号
	 * @param count
	 *            购卡数量
	 */
	List<Card> buycard(String brandid, String orderSn, String agentId,
			String productId, int count);
}
