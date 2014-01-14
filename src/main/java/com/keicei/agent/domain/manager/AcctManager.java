package com.keicei.agent.domain.manager;

import java.util.List;
import java.util.Map;

import com.keicei.agent.domain.entity.Acct;
import com.keicei.agent.domain.entity.AcctFrozenLog;
import com.keicei.agent.domain.entity.AcctTransLog;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.persistence.mapper.AcctMapper;
import com.keicei.common.BaseManager;

public interface AcctManager extends BaseManager<Acct, String, AcctMapper> {

	/**
	 * 冻结 将可用账户余额转移到冻结账户
	 * 
	 * @return 返回是否冻结成功，如冻结失败说明账户可用余额不足
	 * 
	 */
	boolean frozen(AcctFrozenLog acctFrozenLog);

	/**
	 * 解冻
	 * 
	 * @param translog
	 */
	void thaw(AcctFrozenLog acctFrozenLog);

	/**
	 * 支付
	 * 
	 * @param translog
	 */
	void pay(AcctTransLog acctTransLog);

	/**
	 * 解冻并支付
	 * 
	 * @param acctFrozenLog
	 * @param acctTransLog
	 */
	void thawAndPay(AcctFrozenLog acctFrozenLog, AcctTransLog acctTransLog);

	/**
	 * 授信
	 */
	void provide(Agent agent, String agents, Long np_money);

	void provide(String sendAgentId, String receiveAgentId, long provideMoney);

	/**
	 * 手工充值(即后台授信 影响一个代理的余额)
	 * 
	 * @param agentId 代理商ID
	 * @param amount 授信的金额(元)
	 * @param rmk 备注
	 * @return 如果返回0 表示代理商不存在
	 */

	int manualRecharge(String agentId, double amount, String rmk);

	/**
	 * 钱包基本操作：收入
	 * 余额增加 + 记录交易流水
	 * 
	 * @param acctTransLog
	 * @return 如果返回0 表示代理商不存在
	 */
	int income(AcctTransLog acctTransLog);
	/**余额统计**/
	List<Acct> listbalstat(Map<String, Object> map);
	List<Acct> listbalstat(Map<String, Object> map,int page,int pageSize);
	/**余额统计数量**/
	int countbal(Map<String, Object> map);
}
