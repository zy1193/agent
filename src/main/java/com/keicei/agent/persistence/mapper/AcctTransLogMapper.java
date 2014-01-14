package com.keicei.agent.persistence.mapper;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.keicei.agent.domain.entity.AcctTransLog;
import com.keicei.common.BaseMapper;

public interface AcctTransLogMapper extends BaseMapper<AcctTransLog, String> {
	List<AcctTransLog> listaccTransLog(Map<String, Object> parameters,
			int page, int pageSize);

	List<AcctTransLog> listaccTransLog(Map<String, Object> parameters);

	int counts(Map<String, Object> map);

	int count1(Map<String, Object> map);

	List<AcctTransLog> list1(Map<String, Object> map);

	/** 生成某日的账户日报 **/
	int dayreport(Date date);

	/** 删除某日的日报 **/
	int clearDayreport(Date date);
}
