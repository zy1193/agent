package com.keicei.agent.app.action.aop;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.domain.entity.AcctTransLog;
import com.keicei.agent.domain.manager.AcctTransLogManager;
import com.keicei.util.PaginationUtil;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller
public class AcctlogAction extends ActionSupport {

	private static final long serialVersionUID = 9120449915606978620L;
	@Resource
	private AcctTransLogManager acctTransLogManager;

	private int totalRecordCount, page;
	private String transType; // 交易类型
	private String dateStart;// 开始日期
	private String dateEnd;// 结束日期
	private String acctId;// 代理商账号

	private List<AcctTransLog> loglist;

	public List<AcctTransLog> getLoglist() {
		return loglist;
	}

	public void setLoglist(List<AcctTransLog> loglist) {
		this.loglist = loglist;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getDateStart() {
		return dateStart;
	}

	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String list() {
		if (page < 1)
			page = 1;
		Map<String, Object> map = new HashMap<String, Object>();
		if (acctId != null && !"".equals(acctId)) {
			map.put("acctId", acctId);
		}
		if (transType != null && !"999".equals(transType)) {
			map.put("transType", transType);
		}
		if (dateStart != null && isdate(dateStart)) {
			map.put("dateStart", dateStart);
		}
		if (dateEnd != null && isdate(dateEnd)) {
			map.put("dateEnd", dateEnd + " 23:59:59");
		}
		totalRecordCount = acctTransLogManager.count1(map);
		if (totalRecordCount > 0) {
			loglist = acctTransLogManager.list1(map, page,
					PaginationUtil.PAGE_SIZE);
		}
		return SUCCESS;
	}

	private boolean isdate(String string) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			df.parse(string);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

}
