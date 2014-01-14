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
import com.keicei.agent.domain.entity.AcctTransLog;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.manager.AcctTransLogManager;
import com.keicei.agent.domain.manager.AgentManager;
import com.keicei.agent.domain.manager.ProvideManager;
import com.keicei.util.PaginationUtil;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class AcctTransLogAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3345280496021871139L;

	@Resource
	private AcctTransLogManager acctTransLogManager;
	@Resource
	private AgentManager agentManager;
	@Resource
	ProvideManager provideManager;

	private List<AcctTransLog> acctTransLog;

	private List<Agent> subAgents;

	private int totalRecordCount, page;

	private String transSn, transType, agents; // 交易流水号,交易类型

	private Date dateStart;// 开始日期

	private Date dateEnd;// 结束日期

	private String acctId;// 代理商账号

	private long sumPayCounts = 0;// 支出总金额

	private long sumIncomeCounts = 0;// 收入总金额

	public long getSumPayCounts() {
		return sumPayCounts;
	}

	public void setSumPayCounts(long sumPayCounts) {
		this.sumPayCounts = sumPayCounts;
	}

	public long getSumIncomeCounts() {
		return sumIncomeCounts;
	}

	public void setSumIncomeCounts(long sumIncomeCounts) {
		this.sumIncomeCounts = sumIncomeCounts;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public List<AcctTransLog> getAcctTransLog() {
		return acctTransLog;
	}

	public void setAcctTransLog(List<AcctTransLog> acctTransLog) {
		this.acctTransLog = acctTransLog;
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

	public String getTransSn() {
		return transSn;
	}

	public void setTransSn(String transSn) {
		this.transSn = transSn;
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

	/**
	 * 我的流水
	 * 
	 * @return
	 */
	public String runningaccount() {
		if (page < 1)
			page = 1;
		Agent agent = selectuid();
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		list.add(agent.getId());
		map.put("agent_id", list);
		// 交易流水号
		if (transSn != null && !"".equals(transSn))
			map.put("transSn", transSn);
		// 交易类型
		if (transType != null && !"999".equals(transType))
			map.put("transType", transType);

		if (dateStart != null)
			map.put("dateStart", dateStart);

		if (dateEnd != null)
			map.put("dateEnd", dateEnd);

		totalRecordCount = acctTransLogManager.count(map);// 得到总行数
		if (totalRecordCount > 0) {
			acctTransLog = acctTransLogManager.list(map, page,
					PaginationUtil.PAGE_SIZE);

		}

		return SUCCESS;
	}

	/**
	 * 下级流水
	 * 
	 * @return
	 */
	public String subrunningaccount() {
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
		// 交易流水号
		if (transSn != null && !"".equals(transSn))
			map.put("transSn", transSn);
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

		totalRecordCount = acctTransLogManager.count(map);// 得到总行数
		if (totalRecordCount > 0) {
			acctTransLog = acctTransLogManager.list(map, page,
					PaginationUtil.PAGE_SIZE);
			for (AcctTransLog at : acctTransLog) {
				for (Agent agent2 : subAgents) {
					if (at.getAcctId().equals(agent2.getId())) {
						at.setSubname(agent2.getName());
					}
				}
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

	/** 流水统计 **/
	public String listAccTransLog() {
		if (page < 1)
			page = 1;
		Map<String, Object> map = new HashMap<String, Object>();
		if (transType != null && !"999".equals(transType))
			map.put("transType", transType);
		if (acctId != null && !"".equals(acctId))
			map.put("acctId", acctId);
		if (dateStart != null)
			map.put("dateStart", dateStart);

		if (dateEnd != null)
			map.put("dateEnd", dateEnd);
		totalRecordCount = acctTransLogManager.counts(map);
		if (totalRecordCount > 0) {
			acctTransLog = acctTransLogManager.listaccTransLog(map, page,
					PaginationUtil.PAGE_SIZE);
			for (AcctTransLog acc : acctTransLog) {
				if (acc != null && !"".equals(acc)) {
					Agent agent = agentManager.select(acc.getAcctId());
					if (agent == null) {
						acc.setAccName("");
					} else {
						acc.setAccName(agent.getName());
					}
				}
				sumPayCounts += acc.getSumPayCount();
				sumIncomeCounts += acc.getSumIncomeCount();
			}
		}
		return SUCCESS;
	}

}
