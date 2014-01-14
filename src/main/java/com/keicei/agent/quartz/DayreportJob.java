package com.keicei.agent.quartz;

import java.sql.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.keicei.agent.domain.manager.DayReportManager;

/**
 * 生成日报
 * 
 * @author ZHANGYAN
 * 
 */
public class DayreportJob {

	private static final Logger log = Logger.getLogger(DayreportJob.class);
	private static final long ONE_DAY = 1000 * 60 * 60 * 24;

	@Resource
	private DayReportManager dayReportManager;

	public void execute() {
		log.info("开始日报任务");
		Date date = new Date(System.currentTimeMillis() - ONE_DAY);
		try {
			dayReportManager.acctDayReport(date);
		} catch (Exception e) {
			log.error(e, e);
		}
		log.info("日报任务完成");
	}

}
