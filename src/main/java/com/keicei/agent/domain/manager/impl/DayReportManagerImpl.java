package com.keicei.agent.domain.manager.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.domain.entity.AcctDayReport;
import com.keicei.agent.domain.manager.DayReportManager;
import com.keicei.agent.persistence.mapper.AcctTransLogMapper;
import com.keicei.agent.persistence.mapper.DayReport;
import com.keicei.util.PaginationUtil;

@Service("dayReportManager")
public class DayReportManagerImpl implements DayReportManager {

	@Resource
	private DayReport dayReportMapper;
	@Resource
	private AcctTransLogMapper acctTransLogMapper;

	@Transactional(readOnly = true)
	@Override
	public int count(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return dayReportMapper.count(parameters);
	}

	@Override
	public int delete(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(AcctDayReport entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AcctDayReport> list(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public List<AcctDayReport> list(Map<String, Object> parameters, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		PaginationUtil.generatePageParameter(parameters, page, pageSize);
		return dayReportMapper.list(parameters);
	}

	@Override
	public AcctDayReport select(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(AcctDayReport entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	@Override
	public void acctDayReport(Date date) {
		acctTransLogMapper.clearDayreport(date);
		acctTransLogMapper.dayreport(date);
	}

}
