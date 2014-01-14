package com.keicei.agent.domain.manager;

import java.sql.Date;

import com.keicei.agent.domain.entity.AcctDayReport;
import com.keicei.agent.persistence.mapper.DayReport;
import com.keicei.common.BaseManager;

public interface DayReportManager extends BaseManager<AcctDayReport, String, DayReport>{

	/** 账户日报 **/
	void acctDayReport(Date date);
}
