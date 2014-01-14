package com.keicei.agent.domain.manager;

import java.util.List;
import java.util.Map;

import com.keicei.agent.domain.entity.AcctTransLog;
import com.keicei.agent.persistence.mapper.AcctTransLogMapper;
import com.keicei.common.BaseManager;

public interface AcctTransLogManager extends
		BaseManager<AcctTransLog, String, AcctTransLogMapper> {
	/** 流水统计 **/
	List<AcctTransLog> listaccTransLog(Map<String, Object> map);

	int counts(Map<String, Object> map);

	List<AcctTransLog> listaccTransLog(Map<String, Object> parameters,
			int page, int pageSize);

	int count1(Map<String, Object> map);

	List<AcctTransLog> list1(Map<String, Object> map, int page, int pageSize);

}
