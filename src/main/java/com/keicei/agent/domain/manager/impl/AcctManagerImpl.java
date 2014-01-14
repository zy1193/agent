package com.keicei.agent.domain.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.domain.entity.Acct;
import com.keicei.agent.domain.entity.AcctFrozenLog;
import com.keicei.agent.domain.entity.AcctTransLog;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.manager.AcctManager;
import com.keicei.agent.domain.repository.BrandRepository;
import com.keicei.agent.persistence.mapper.AcctFrozenLogMapper;
import com.keicei.agent.persistence.mapper.AcctMapper;
import com.keicei.agent.persistence.mapper.AcctTransLogMapper;
import com.keicei.agent.persistence.mapper.Provide;
import com.keicei.util.PaginationUtil;
import com.keicei.util.SequenceUtil;

@Service("acctManager")
public class AcctManagerImpl implements AcctManager {

	@Resource
	private AcctMapper acctMapper;
	@Resource
	private AcctFrozenLogMapper acctFrozenLogMapper;
	@Resource
	private AcctTransLogMapper acctTransLogMapper;
	@Resource
	private BrandRepository brandRepository;

	@Resource
	private Provide provideMapper;

	@Resource
	private AcctManager acctManager;

	@Override
	public int insert(Acct entity) {
		return acctMapper.insert(entity);
	}

	@Transactional
	@Override
	public int update(Acct entity) {
		return acctMapper.update(entity);
	}

	@Override
	public int delete(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	@Override
	public Acct select(String key) {
		return acctMapper.select(key);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Acct> list(Map<String, Object> parameters) {
		return acctMapper.list(parameters);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Acct> list(Map<String, Object> parameters, int page,
			int pageSize) {
		PaginationUtil.generatePageParameter(parameters, page, pageSize);
		return acctMapper.list(parameters);
	}

	@Transactional(readOnly = true)
	@Override
	public int count(Map<String, Object> parameters) {
		return acctMapper.count(parameters);
	}

	@Transactional
	@Override
	public boolean frozen(AcctFrozenLog acctFrozenLog) {
		if (acctMapper.frozen(acctFrozenLog.getAcctId(),
				acctFrozenLog.getFrozenAmount()) == 1) {
			acctFrozenLog.setStatus(Constant.FROZEN_STATUS_UNTHAW);
			acctFrozenLogMapper.insert(acctFrozenLog);
			return true;
		} else {
			return false;
		}

	}

	@Transactional
	@Override
	public void thaw(AcctFrozenLog acctFrozenLog) {
		if (acctFrozenLogMapper.thaw(acctFrozenLog) == 1) {
			acctMapper.thaw(acctFrozenLog.getAcctId(),
					acctFrozenLog.getFrozenAmount());
		}
	}

	@Transactional
	@Override
	public void pay(AcctTransLog acctTransLog) {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public void thawAndPay(AcctFrozenLog acctFrozenLog,
			AcctTransLog acctTransLog) {
		/** setp1 解冻 **/
		if (acctFrozenLogMapper.thaw(acctFrozenLog) == 1) {
			acctMapper.thaw(acctFrozenLog.getAcctId(),
					acctFrozenLog.getFrozenAmount());
		}

		/** step2 支付并查询余额 **/
		acctMapper.pay(acctTransLog);
		Acct acct = acctMapper.select(acctFrozenLog.getAcctId());

		/** step3 记录账户流水日志 **/
		acctTransLog.setBalance(acct.getAvailableBalance()
				+ acct.getFrozenBalance());
		acctTransLogMapper.insert(acctTransLog);
	}

	/**
	 * 授信
	 * agents 被授信用户的id
	 * np_money 授信额度
	 * 
	 */
	@Transactional
	@Override
	public void provide(Agent agent, String agents, Long np_money) {
		Acct acct = new Acct();
		acct.setId(agent.getId());
		acct.setAvailableBalance(np_money);
		int effect = provideMapper.provide(acct);
		String bid = agent.getBrandid();

		if (effect == 1) {

			Acct at = new Acct();

			at.setId(agents);
			at.setAvailableBalance(0 - np_money);
			provideMapper.provide(at);

			String orderSn = SequenceUtil.id();
			long p_over = acctManager.select(agent.getId())
					.getAvailableBalance();// 当前账户余额
			// 授信流水记录
			AcctTransLog acctTransLog = new AcctTransLog();
			acctTransLog.setBrandid(bid);
			acctTransLog.setAcctId(agent.getId());
			acctTransLog.setPayAmount(np_money);
			acctTransLog.setIncomeAmount(0);
			acctTransLog.setTransSn(orderSn);
			acctTransLog.setBalance(p_over);
			acctTransLog.setTransType(Constant.ACCT_TRANS_TYPE_PROVIDE);
			acctTransLog
					.setRmk("授信给：" + agents + "合计：" + np_money / 1000 + "元");
			acctTransLogMapper.insert(acctTransLog);

			// 被授信流水记录
			long d_over = acctManager.select(agents).getAvailableBalance();// 下级余额
			AcctTransLog acctT_Log = new AcctTransLog();
			acctT_Log.setAcctId(agents);
			acctT_Log.setBrandid(bid);
			acctT_Log.setPayAmount(0);
			acctT_Log.setIncomeAmount(np_money);
			acctT_Log.setTransSn(orderSn);
			acctT_Log.setBalance(d_over);
			acctT_Log.setTransType(Constant.ACCT_TRANS_TYPE_PROVIDE_RECEIVE);
			acctT_Log.setRmk("接收来自" + agent.getId() + "授信：" + np_money / 1000
					+ "元");
			acctTransLogMapper.insert(acctT_Log);

		}
	}

	@Transactional
	@Override
	public void provide(String sendAgentId, String receiveAgentId,
			long provideMoney) {
		Acct acct = new Acct();
		acct.setId(sendAgentId);
		acct.setAvailableBalance(provideMoney);
		int effect = provideMapper.provide(acct);

		if (effect == 1) {

			Acct at = new Acct();

			at.setId(receiveAgentId);
			at.setAvailableBalance(0 - provideMoney);
			provideMapper.provide(at);

			String orderSn = SequenceUtil.id();
			long p_over = acctManager.select(sendAgentId).getAvailableBalance();// 当前账户余额
			// 授信流水记录
			AcctTransLog acctTransLog = new AcctTransLog();
			acctTransLog.setAcctId(sendAgentId);
			acctTransLog.setPayAmount(provideMoney);
			acctTransLog.setIncomeAmount(0);
			acctTransLog.setTransSn(orderSn);
			acctTransLog.setBalance(p_over);
			acctTransLog.setTransType(Constant.ACCT_TRANS_TYPE_PROVIDE);
			acctTransLog.setRmk("授信给：" + receiveAgentId + "合计：" + provideMoney
					/ 1000 + "元");
			acctTransLogMapper.insert(acctTransLog);

			// 被授信流水记录
			long d_over = acctManager.select(receiveAgentId)
					.getAvailableBalance();// 下级余额
			AcctTransLog acctT_Log = new AcctTransLog();
			acctT_Log.setAcctId(receiveAgentId);
			acctT_Log.setPayAmount(0);
			acctT_Log.setIncomeAmount(provideMoney);
			acctT_Log.setTransSn(orderSn);
			acctT_Log.setBalance(d_over);
			acctT_Log.setTransType(Constant.ACCT_TRANS_TYPE_PROVIDE_RECEIVE);
			acctT_Log.setRmk("接收来自" + sendAgentId + "授信：" + provideMoney / 1000
					+ "元");
			acctTransLogMapper.insert(acctT_Log);

		}

	}

	@Override
	public int manualRecharge(String agentId, double amount, String rmk) {

		String brandid = brandRepository.brand(agentId);
		if (brandid == null) {
			throw new RuntimeException("no brand");
		}
		String seq = SequenceUtil.id();
		/** 将金额从元转换为厘 **/
		long amt = (long) (amount * 1000);

		AcctTransLog acctTransLog = new AcctTransLog();
		acctTransLog.setAcctId(agentId);
		acctTransLog.setIncomeAmount(amt);
		acctTransLog.setPayAmount(0);
		acctTransLog.setTransSn(seq);
		acctTransLog.setTransTime(new Date());
		acctTransLog.setTransType(Constant.ACCT_TRANS_TYPE_PROVIDE_RECEIVE);
		acctTransLog.setRmk(rmk);
		acctTransLog.setBrandid(brandid);

		return income(acctTransLog);
	}

	@Transactional
	@Override
	public int income(AcctTransLog acctTransLog) {
		/** 增加可用余额 **/
		if (acctMapper.income(acctTransLog) == 1) {
			/** 查询当前余额 **/
			Acct acct = acctMapper.select(acctTransLog.getAcctId());
			/** 记录操作流水 **/
			acctTransLog.setBalance(acct.getAvailableBalance()
					+ acct.getFrozenBalance());
			return acctTransLogMapper.insert(acctTransLog);
		} else {
			return 0;
		}
	}

	@Override
	public List<Acct> listbalstat(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Acct> listbalstat(Map<String, Object> map, int page,
			int pageSize) {
		PaginationUtil.generatePageParameter(map, page, pageSize);
		return acctMapper.listbalstat(map);
	}

	@Override
	public int countbal(Map<String, Object> map) {
		return acctMapper.countbal(map);
	}

}
