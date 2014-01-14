package com.keicei.agent.app.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.constant.MenuId;
import com.keicei.agent.domain.entity.Acct;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.entity.OperateLog;
import com.keicei.agent.domain.manager.AcctManager;
import com.keicei.agent.domain.manager.OperateLogManager;
import com.keicei.agent.domain.manager.ProvideManager;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class ProvideAction extends ActionSupport {

	private static final long serialVersionUID = 7052002559921080031L;

	@Resource
	ProvideManager provideManager;
	@Resource
	private OperateLogManager operateLogManager;
	@Resource
	private AcctManager acctManager;

	private Acct acct;

	private String errmsg;

	private List<Agent> subAgents;

	private Agent agent;

	private String p_money, agents, password;

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Acct getAcct() {
		return acct;
	}

	public void setAcct(Acct acct) {
		this.acct = acct;
	}

	public List<Agent> getSubAgents() {
		return subAgents;
	}

	public void setSubAgents(List<Agent> subAgents) {
		this.subAgents = subAgents;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getP_money() {
		return p_money;
	}

	public void setP_money(String pMoney) {
		p_money = pMoney;
	}

	public String getAgents() {
		return agents;
	}

	public void setAgents(String agents) {
		this.agents = agents;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/** 查看下级代理商姓名、ID **/
	public String subproname() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);
		subAgents = provideManager.listSubAgent(agent.getId());// 下级信息
		acct = acctManager.select(agent.getId());// 当前账户的信息（可用余额和状态等）
		return SUCCESS;
	}

	/**
	 * 授信
	 * 
	 * @return
	 */
	public String accredited() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);

		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AM");
		operateLog.setIp(request.getRemoteAddr());
		operateLog.setLoginId(agent.getId());
		operateLog.setMenuId(MenuId.PROVIDE);
		operateLog.setOperateDetail("授信:money=" + p_money + ",password="
				+ password + ",receiver=" + agents);
		operateLogManager.asyncLog(operateLog);

		long np_money = Long.parseLong(p_money) * 1000;// 授信额度

		if (np_money < 0) {
			errmsg = "不能授信负数";
			return ERROR;
		}

		long present_money = acctManager.select(agent.getId())
				.getAvailableBalance();// 当前账户余额
		// long d_money = acctManager.select(agents).getAvailableBalance();//
		// 下级余额

		/**
		 * 授信的条件：当前用户的密码、当前账户的状态、授信用户的状态、当前账户的余额
		 */
		if (!DigestUtils.md5Hex(password).equals(agent.getPassword())) {
			errmsg = "你输入的密码不正确！";
			return ERROR;
		}
		if (present_money > np_money) {
			acctManager.provide(agent, agents, np_money);
			return SUCCESS;
		} else {
			errmsg = "你的账户余额不足！";
			return ERROR;
		}
	}
}
