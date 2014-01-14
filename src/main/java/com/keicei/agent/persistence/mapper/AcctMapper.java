package com.keicei.agent.persistence.mapper;

import java.util.List;
import java.util.Map;

import com.keicei.agent.domain.entity.Acct;
import com.keicei.agent.domain.entity.AcctTransLog;
import com.keicei.common.BaseMapper;

public interface AcctMapper extends BaseMapper<Acct, String> {
	int frozen(String acctId, long frozenAmount);

	int thaw(String acctId, long frozenAmount);

	int pay(AcctTransLog acctTransLog);

	int audit(String brandid,String id);
	
	/** 钱包基本操作：进账 **/
	int income(AcctTransLog acctTransLog);
	/**余额统计**/
	List<Acct> listbalstat(Map<String, Object> map);
	List<Acct> listbalstat(Map<String, Object> map,int page,int pageSize);
	/**余额统计数量**/
	int countbal(Map<String, Object> map);
}
