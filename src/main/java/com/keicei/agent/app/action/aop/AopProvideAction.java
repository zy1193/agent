package com.keicei.agent.app.action.aop;

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
import com.keicei.agent.domain.entity.User;
import com.keicei.agent.domain.manager.AcctManager;
import com.keicei.agent.domain.manager.AgentManager;
import com.keicei.agent.domain.manager.OperateLogManager;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 后台授信
 * 
 * @author ZHANGYAN
 * 
 */

@Controller
@Scope("prototype")
public class AopProvideAction extends ActionSupport {

	private static final long serialVersionUID = 7016960965144660836L;

	@Resource
	private AgentManager agentManager;
	@Resource
	private AcctManager acctManager;
	@Resource
	private OperateLogManager operateLogManager;
	/** 代理商id */
	private String agentId;
	/** 授信的金额 元 **/
	private double amount;
	/** 用户密码 **/
	private String password;
	/** 错误提示 **/
	private String errmsg;
	/** 备注 **/
	private String rmk;

	private Agent agent;
	private Acct acct;

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Agent getAgent() {
		return agent;
	}

	public Acct getAcct() {
		return acct;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public String provide() {
		if (agentId == null || agentId.length() == 0) {
			return INPUT;
		} else {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			User user = (User) (session
					.getAttribute(Constant.USER_SESSION_NAME));

			OperateLog operateLog = new OperateLog();
			operateLog.setCategory("AO");
			operateLog.setIp(request.getRemoteAddr());
			if (user != null)
				operateLog.setLoginId(user.getId());
			operateLog.setMenuId(MenuId.ADMIN_PROVIDE);
			operateLog.setOperateDetail("后台授信:adminId=" + user.getId()
					+ ",money=" + amount + ",agentId=" + agentId + ",rmk="
					+ rmk);
			operateLogManager.asyncLog(operateLog);

			if (!DigestUtils.md5Hex(password).equals(user.getPassword())) {
				errmsg = "密码错误";
				return ERROR;
			}

			if (acctManager.manualRecharge(agentId, amount, rmk) == 0) {
				errmsg = "代理商不存在";
				return ERROR;
			} else {
				return SUCCESS;
			}
		}
	}

	public String agentInfo() {
		agent = agentManager.select(agentId);
		acct = acctManager.select(agentId);
		return SUCCESS;
	}

}
