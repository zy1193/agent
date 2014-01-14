package com.keicei.agent.app.action;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;

import com.keicei.agent.domain.entity.Acct;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.manager.AgentManager;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 代理商注册
 * 
 * @author ZHANGYAN
 * 
 */
@Controller
@Scope("prototype")
public class RegisterAction extends ActionSupport {

	private static final Logger log = Logger.getLogger(RegisterAction.class);
	private static final long serialVersionUID = -2446967436120999215L;
	private Agent agent;
	private Acct acct;
	private String errmsg;

	@Resource
	private AgentManager agentManager;

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Acct getAcct() {
		return acct;
	}

	public void setAcct(Acct acct) {
		this.acct = acct;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String register() {
		if (agent == null) {
			acct = new Acct();
			acct.setDeposit(0);
			return INPUT;
		} else {
			try {
				agentManager.register(agent, acct);
			} catch (Exception e) {
				if (e instanceof DuplicateKeyException) {
					errmsg = "您要注册的登录名(" + agent.getId() + ")已经存在，请换一个登录名注册";
					log.error("注册失败:" + errmsg);
				} else {
					errmsg = "内部错误(" + e.getMessage() + ")";
					log.error(e, e);
				}
				return ERROR;
			}
			return SUCCESS;
		}
	}
}
