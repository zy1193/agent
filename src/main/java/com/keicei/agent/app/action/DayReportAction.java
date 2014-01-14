package com.keicei.agent.app.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.domain.entity.AcctDayReport;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.manager.DayReportManager;
import com.keicei.agent.domain.manager.ProvideManager;
import com.keicei.util.PaginationUtil;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class DayReportAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4004585029581875393L;

	@Resource
	private DayReportManager dayReportManager;

	@Resource
	ProvideManager provideManager;

	private int totalRecordCount, page;

	private List<AcctDayReport> acctDayReportList;

	private List<Agent> subAgents;

	private String acctId, transType, agents;

	private Date dateStart;// 开始日期

	private Date dateEnd;// 结束日期

	private int sumpayamount = 0, sumincomeAmount = 0;

	int sumtransCount=0;

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

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public List<AcctDayReport> getAcctDayReportList() {
		return acctDayReportList;
	}

	public void setAcctDayReportList(List<AcctDayReport> acctDayReportList) {
		this.acctDayReportList = acctDayReportList;
	}

	public List<Agent> getSubAgents() {
		return subAgents;
	}

	public void setSubAgents(List<Agent> subAgents) {
		this.subAgents = subAgents;
	}

	public String getAgents() {
		return agents;
	}

	public void setAgents(String agents) {
		this.agents = agents;
	}

	public int getSumtransCount() {
		return sumtransCount;
	}

	public void setSumtransCount(int sumtransCount) {
		this.sumtransCount = sumtransCount;
	}

	public int getSumpayamount() {
		return sumpayamount;
	}

	public void setSumpayamount(int sumpayamount) {
		this.sumpayamount = sumpayamount;
	}

	public int getSumincomeAmount() {
		return sumincomeAmount;
	}

	public void setSumincomeAmount(int sumincomeAmount) {
		this.sumincomeAmount = sumincomeAmount;
	}

	/**
	 * 我的日报
	 * 
	 * @return
	 */
	public String mydayreport() {
		if (page < 1)
			page = 1;
		Agent agent = selectuid();
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		list.add(agent.getId());
		map.put("agent_id", list);
		// 交易类型
		if (transType != null && !"999".equals(transType))
			map.put("transType", transType);

		if (dateStart != null)
			map.put("dateStart", dateStart);

		if (dateEnd != null)
			map.put("dateEnd", dateEnd);

		totalRecordCount = dayReportManager.count(map);// 得到总行数
		if (totalRecordCount > 0) {
			acctDayReportList = dayReportManager.list(map, page,
					PaginationUtil.PAGE_SIZE);
			// 总计
			for (AcctDayReport ad : acctDayReportList) {
				sumpayamount += ad.getPayAmount();
				sumincomeAmount += ad.getIncomeAmount();
				sumtransCount += ad.getTransCount();
			}
		}
		return SUCCESS;
	}

	/**
	 * 下级日报
	 * 
	 * @return
	 */
	public String subdayreport() {
		if (page < 1)
			page = 1;
		Agent agent = selectuid();
		subAgents = provideManager.listSubAgent(agent.getId());// 下级信息
		// 是否有下级
		if (subAgents.size() < 1)
			return SUCCESS;

		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		for (Agent agent2 : subAgents) {
			list.add(agent2.getId());
		}

		map.put("agent_id", list);

		// 交易类型
		if (transType != null && !"999".equals(transType))
			map.put("transType", transType);

		if (dateStart != null)
			map.put("dateStart", dateStart);

		if (dateEnd != null)
			map.put("dateEnd", dateEnd);

		// 下级
		if (agents != null && !"".equals(agents))
			map.put("agents", agents);

		totalRecordCount = dayReportManager.count(map);// 得到总行数
		if (totalRecordCount > 0) {
			acctDayReportList = dayReportManager.list(map, page,
					PaginationUtil.PAGE_SIZE);
			for (AcctDayReport dr : acctDayReportList) {
				for (Agent agent2 : subAgents) {
					if (dr.getAcctId().equals(agent2.getId())) {
						dr.setSubname(agent2.getName());
					}
				}
				// 总计
				sumpayamount += dr.getPayAmount();
				sumincomeAmount += dr.getIncomeAmount();
				sumtransCount += dr.getTransCount();
			}
		}
		return SUCCESS;
	}

	/** 查询当前登录用户的用户ID * */
	public Agent selectuid() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Agent agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);
		return agent;
	}
}
