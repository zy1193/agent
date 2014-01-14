package com.keicei.agent.app.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.manager.AgentManager;
import com.keicei.util.PaginationUtil;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller
public class AgentAction extends ActionSupport {

	private static final long serialVersionUID = 1663409884057775788L;
	@Resource
	private AgentManager agentManager;
	private String id;
	private Agent agent;
	private String oldpwd, newpwd;
	private String errmsg;
	private List<Agent> subAgents, agents;
	private int page, totalRecordCount;
	private String password;
	private Agent ag;//上级代理商
	private Agent age;
	
	public Agent getAge() {
		return age;
	}

	public void setAge(Agent age) {
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getOldpwd() {
		return oldpwd;
	}

	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}

	public String getNewpwd() {
		return newpwd;
	}

	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public List<Agent> getSubAgents() {
		return subAgents;
	}

	public void setSubAgents(List<Agent> subAgents) {
		this.subAgents = subAgents;
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

	public String getPassword() {
		return password;
	}

	public Agent getAg() {
		return ag;
	}

	public void setAg(Agent ag) {
		this.ag = ag;
	}

	/** 查看当前登录代理商信息 **/
	public String myprofile() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);
		return SUCCESS;
	}

	/** 查看下级代理商信息 **/
	public String subprofile() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);
		subAgents = agentManager.listSubAgent(agent.getId());
		return SUCCESS;
	}

	/** 修改密码 **/
	public String password() {
		if (oldpwd == null) {
			return INPUT;
		} else {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);
			Agent agt = agentManager.select(agent.getId());
			if (DigestUtils.md5Hex(oldpwd).equals(agt.getPassword())) {
				// 修改密码
				agt.setPassword(DigestUtils.md5Hex(newpwd));
				agentManager.changePassword(agt);

				// 更新session里的密码
				agent.setPassword(agt.getPassword());
				session.setAttribute(Constant.AGENT_SESSION_NAME, agent);

				return SUCCESS;
			} else {
				errmsg = "原密码输入不正确";
				return ERROR;
			}
		}
	}

	public String resetPassword() {
		password = pwd();
		agent.setPassword(DigestUtils.md5Hex(password));
		agentManager.changePassword(agent);
		return SUCCESS;
	}

	/** 代理商列表 **/
	public String list() {

		if (page < 1)
			page = 1;

		Map<String, Object> param = new HashMap<String, Object>();
		if (agent != null && agent.getId() != null) {
			param.put("id", "%" + agent.getId() + "%");
			totalRecordCount = agentManager.count(param);
		} else {
			totalRecordCount = 0;
		}
		if (totalRecordCount > 0)
			agents = agentManager.list(param, page, PaginationUtil.PAGE_SIZE);
		return SUCCESS;
	}

	public String frozen() {
		agentManager.update(agent);

		/** 冻结or解冻代理商所有未销售的卡 **/
		if ("0".equals(agent.getStatus())) {
			agentManager.switchCardStatus(agent.getId(), "0");
		} else {
			agentManager.switchCardStatus(agent.getId(), "1");
		}

		return SUCCESS;
	}
	

	private static final String pwd() {
		StringBuilder buf = new StringBuilder(6);
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			buf.append(random.nextInt(10));
		}
		return buf.toString();
	}

	public String delete() {
		agentManager.delete(agent.getId());
		return SUCCESS;
	}

	/**
	 * 切换卡状态
	 * 可以将代理商的所有未充值的卡冻结或解冻
	 * 
	 * @return
	 */
	public String switchCardStatus() {
		return SUCCESS;
	}
	/**
	 * 按照编号查询代理商详细信息
	 */
	public String showDetail(){
		agent = agentManager.select(agent.getId());//代理商信息
		ag = agentManager.select(agent.getSuperId());//上级代理商信息
		if(ag != null && !"".equals(ag)){
			age = agentManager.select(ag.getSuperId());
		}
		if(ag != null && !"".equals(ag)){
			agent.setSuperName(ag.getName());
		}else{
		agent.setSuperName("无");
		}
		agent.setAgents(agentManager.listSubAgent(agent.getId()));
		return SUCCESS;
	}

}
