package com.keicei.agent.app.action.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.manager.AgentManager;
import com.keicei.util.PaginationUtil;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class AuditAction extends ActionSupport {

	private static final long serialVersionUID = -8345086241276454568L;
	@Resource
	private AgentManager agentManager;
	private Agent agent;
	private List<Agent> agents;

	private int totalRecordCount;
	private int page;

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Agent getAgent() {
		return agent;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPage() {
		return page;
	}

	@Override
	public String execute() {
		if (agent != null) {
			agentManager.accountAudit(agent);
			return SUCCESS;
		} else {

			if (page < 1)
				page = 1;

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("acctStatus", "3");

			totalRecordCount = agentManager.count(param);
			agents = agentManager.list(param, page, PaginationUtil.PAGE_SIZE);

			return INPUT;
		}

	}

}
