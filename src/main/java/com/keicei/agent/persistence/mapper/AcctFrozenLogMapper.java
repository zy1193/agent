package com.keicei.agent.persistence.mapper;

import com.keicei.agent.domain.entity.AcctFrozenLog;
import com.keicei.common.BaseMapper;

public interface AcctFrozenLogMapper extends BaseMapper<AcctFrozenLog, Integer> {
	/**
	 * 修改冻结状态
	 * 
	 * @param acctFrozenLog
	 * @return
	 */
	int thaw(AcctFrozenLog acctFrozenLog);
}
