package com.keicei.agent.domain.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.domain.entity.AcctFrozenLog;
import com.keicei.agent.domain.entity.AcctTransLog;
import com.keicei.agent.domain.entity.BuycardOrder;
import com.keicei.agent.domain.entity.Card;
import com.keicei.agent.domain.entity.Product;
import com.keicei.agent.domain.manager.AcctManager;
import com.keicei.agent.domain.manager.CardManager;
import com.keicei.agent.domain.repository.ProductRepository;
import com.keicei.agent.domain.rpc.GetcardService;
import com.keicei.agent.domain.rpc.SwitchCardStatusService;
import com.keicei.agent.persistence.mapper.BuycardOrderMapper;
import com.keicei.agent.persistence.mapper.CardMapper;
import com.keicei.util.PaginationUtil;
import com.keicei.util.PwdUtil;
import com.keicei.util.SequenceUtil;

@Service("cardManager")
public class CardManagerImpl implements CardManager {

	private static final Logger log = Logger.getLogger(CardManagerImpl.class);

	@Resource
	private CardMapper cardMapper;
	@Resource
	private BuycardOrderMapper buycardOrderMapper;
	@Resource
	private ProductRepository productRepository;
	@Resource
	private AcctManager acctManager;
	@Resource
	private GetcardService getcardService;
	@Resource
	private SwitchCardStatusService switchCardStatusService;
	@Override
	public int insert(Card entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	@Override
	public int update(Card entity) {
		// TODO Auto-generated method stub
		return cardMapper.update(entity);
	}

	@Override
	public int delete(Integer key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Card select(Integer key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Card> list(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Card> list(Map<String, Object> parameters, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public int count(Map<String, Object> parameters) {
		return cardMapper.count(parameters);
	}

	/** 购卡 **/
	@Override
	public int buycard(String brandid, String productId, String agentId,
			String password, int count) {

		Product product = productRepository.select(productId);
		int price = product.getPrice();
		/** 购卡总金额 **/
		long amount = price * count;
		/** 订单号 **/
		String orderSn = SequenceUtil.id();
		/** 备注 **/
		StringBuilder rmk = new StringBuilder();
		rmk.append("购卡:");
		rmk.append(count).append("张,");
		rmk.append("单价").append(price / 1000).append("元,");
		rmk.append("合计").append(amount / 1000).append("元");

		// Step 2 : 生成订单并保存
		BuycardOrder buycardOrder = new BuycardOrder();
		buycardOrder.setAgentId(agentId);
		buycardOrder.setOrderSn(orderSn);
		buycardOrder.setProductCount(count);
		buycardOrder.setProductId(productId);
		buycardOrder.setProductPrice(price);
		buycardOrder.setTotalAmount(amount);
		buycardOrder.setStatus(Constant.BUYCARD_ORDER_STATUS_PROCESSING);
		buycardOrder.setBrandid(brandid);
		insertBuycardOrder(buycardOrder);

		// Step 3 : 冻结购卡金额
		AcctFrozenLog acctFrozenLog = new AcctFrozenLog();
		acctFrozenLog.setAcctId(agentId);
		acctFrozenLog.setFrozenType(Constant.FROZEN_TYPE_BUYCARD);
		acctFrozenLog.setFrozenAmount(amount);
		acctFrozenLog.setFrozenSn(orderSn);
		acctFrozenLog.setBrandid(brandid);
		if (!acctManager.frozen(acctFrozenLog)) {
			buycardOrder.setStatus(Constant.BUYCARD_ORDER_STATUS_FAIL);
			updateBuycardOrderStatus(buycardOrder);
			return Constant.RC_BUYCARD_BAL_LESS;
		}

		// Step 4 : 远程提卡
		List<Card> cards = null;
		try {
			cards = getcardService.buycard(brandid, buycardOrder.getOrderSn(),
					agentId, productId, count);
		} catch (Exception e) {
			log.error(e, e);
		}
		if (cards == null || cards.size() != count) {
			// 解冻
			try {
				acctManager.thaw(acctFrozenLog);
			} catch (Exception e) {
				log.error(e, e);
			}
			try {
				buycardOrder.setStatus(Constant.BUYCARD_ORDER_STATUS_FAIL);
				updateBuycardOrderStatus(buycardOrder);
			} catch (Exception e) {
				log.error(e, e);
			}
			return Constant.RC_BUYCARD_RPC_ERR;
		}

		// Step 5 : 保存卡信息 解冻+支付 修改订单状态
		/** 将卡信息保存到卡表 **/
		for (Card card : cards) {
			card.setOrderSn(orderSn);
			card.setAgentId(agentId);
			card.setPrice(price);
			card.setRechargeStatus(Constant.CARD_RECHARGE_STATUS_UNRECHARGE);
			card.setSaleStatus(Constant.CARD_SALE_STATUS_UNSALE);
			card.setStatus(Constant.CARD_STATUS_NORMAL);
			card.setBrandid(brandid);
			cardMapper.insert(card);
		}
		/** 支付购卡金额 **/
		AcctTransLog acctTransLog = new AcctTransLog();
		acctTransLog.setAcctId(agentId);
		acctTransLog.setPayAmount(amount);
		acctTransLog.setIncomeAmount(0);
		acctTransLog.setTransSn(orderSn);
		acctTransLog.setTransType(Constant.ACCT_TRANS_TYPE_BUYCARD);
		acctTransLog.setRmk(rmk.toString());
		acctTransLog.setBrandid(brandid);
		acctManager.thawAndPay(acctFrozenLog, acctTransLog);

		/** 将购卡订单修改为购卡成功 **/
		buycardOrder.setStatus(Constant.BUYCARD_ORDER_STATUS_SUCCESS);
		updateBuycardOrderStatus(buycardOrder);

		return Constant.RC_BUYCARD_SUCCESS;
	}

	@Transactional
	@Override
	public int insertBuycardOrder(BuycardOrder buycardOrder) {
		return buycardOrderMapper.insert(buycardOrder);
	}

	@Transactional
	@Override
	public int updateBuycardOrderStatus(BuycardOrder buycardOrder) {
		return buycardOrderMapper.updateStatus(buycardOrder);
	}

	@Transactional(readOnly = true)
	@Override
	public Card selectByNo(String no, String agentId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("no", no);
		if (agentId != null) {
			map.put("agentId", agentId);
		}
		return cardMapper.selectByNo(map);
	}

	@Transactional(readOnly = true)
	@Override
	public Card selectByPassword(String password) {
		Card card = new Card();
		card.setPassword(password);
		return cardMapper.selectByPassword(card);
	}

	@Override
	public List<Card> listRechargeStat(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Card> listRechargeStat(Map<String, Object> map, int page,
			int pageSize) {
		PaginationUtil.generatePageParameter(map, page, pageSize);
		return cardMapper.listRechargeStat(map);
	}
	
	@Override
	public List<Card> getCardPassFromBeginToEndNo(String agentId, String begin,
			String end, String recharge_status, String status,String brandid) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("agentId", agentId);
		map.put("begin", begin);
		map.put("end", end);
		map.put("recharge_status", recharge_status);
		map.put("status", status);
		map.put("branid", brandid);
		
		return cardMapper.getCardPassFromBeginToEndNo(map);
	}

	@Override
	public List<Card> getCardPassFromCardsNo(String agentId,List<String> cardsNo,
			String brandid) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("agentId", agentId);
		map.put("cardsNo", cardsNo);
		map.put("brandid", brandid);
		
		return cardMapper.getCardPassFromCardsNo(map);
	}

	@Override
	public void frozenOrThawCard(List<String> cardList, String status,String brandid) {
		int i = 0;
		StringBuilder buf = new StringBuilder();
		for (String password: cardList) {
			buf.append(password).append(',');
			if (i < 49) {
				i++;
			} else {
				buf.deleteCharAt(buf.length() - 1);
				switchCardStatusService.switchCardStatus(brandid,
						buf.toString(), status);
				buf = new StringBuilder();
				i = 0;
			}
		}
		if (i != 0) {
			buf.deleteCharAt(buf.length() - 1);
			switchCardStatusService.switchCardStatus(brandid, buf.toString(),
					status);
		}

		// 修改卡状态
		switchStatusByPass(cardList,status);
		
	}

	private void switchStatusByPass(List<String> cardList,String status) {
		Map<String,Object> map=new HashMap<String,Object>();
		List<String> passList=new ArrayList<String>();
		List<String> desPassList=new ArrayList<String>();
		for(String pass:cardList){
			passList.add(pass);
			desPassList.add(PwdUtil.despwd(pass));
		}
		map.put("passList", passList);
		map.put("desPassList", desPassList);
		map.put("status", status);
		cardMapper.switchStatusByPass(map);
	}
}
