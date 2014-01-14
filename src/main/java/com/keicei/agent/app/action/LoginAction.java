package com.keicei.agent.app.action;

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
import com.keicei.agent.domain.entity.Notice;
import com.keicei.agent.domain.entity.OperateLog;
import com.keicei.agent.domain.manager.AcctManager;
import com.keicei.agent.domain.manager.AgentManager;
import com.keicei.agent.domain.manager.NoticeManager;
import com.keicei.agent.domain.manager.OperateLogManager;
import com.keicei.common.SecurityCode;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 登录和登出
 * 
 * @author ZHANGYAN
 * 
 */
@Controller
@Scope("prototype")
public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = -4617804669541066598L;
	@Resource
	private AgentManager agentManager;
	@Resource
	private AcctManager acctManager;

	@Resource
	private NoticeManager noticeManager;
	@Resource
	private OperateLogManager operateLogManager;

	private String errmsg, securityCode;

	private Agent agent;
	private Notice notice;

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String login() {

		if (agent == null) {
			return LOGIN;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AM");
		operateLog.setIp(request.getRemoteAddr());
		operateLog.setLoginId(agent.getId());
		operateLog.setMenuId(MenuId.LOGIN);
		operateLog.setOperateDetail("登录:id=" + agent.getId() + ",密码="
				+ agent.getPassword());
		operateLogManager.asyncLog(operateLog);

		if (!securityCode.equals((String) session
				.getAttribute(SecurityCode.SESSION_ATTR_NAME))) {
			errmsg = "验证码不正确";
			return ERROR;
		}

		Agent agt = agentManager.select(agent.getId());
		if (agt == null) {
			errmsg = "代理商未注册";
			return ERROR;
		}

		if ("2".equals(agt.getStatus())) {
			errmsg = "代理商帐号已注销";
			return ERROR;
		}

		if (!agt.getPassword().equals(DigestUtils.md5Hex(agent.getPassword()))) {
			errmsg = "密码错误";
			return ERROR;
		}

		// 保存代理商信息、钱包帐号状态到session
		session.setAttribute(Constant.AGENT_SESSION_NAME, agt);
		Acct acct = acctManager.select(agt.getId());
		session.setAttribute(Constant.ACCT_STATUS,
				acct == null ? "2" : acct.getStatus());
		return SUCCESS;
	}

	public String notice() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Agent agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);
		notice = noticeManager.select(agent.getBrandid());
		return SUCCESS;
	}

	public String logout() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		Agent agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);
		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AM");
		operateLog.setIp(request.getRemoteAddr());
		if (agent != null) {
			operateLog.setLoginId(agent.getId());
		}
		operateLog.setMenuId(MenuId.LOGOUT);
		operateLog.setOperateDetail("登出:id="
				+ (agent == null ? "" : agent.getId()));
		operateLogManager.asyncLog(operateLog);

		session.removeAttribute(Constant.AGENT_SESSION_NAME);
		return SUCCESS;
	}
}
