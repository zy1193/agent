package com.keicei.agent.app.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.domain.entity.Agent;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class LogoAction extends ActionSupport {

	private static final long serialVersionUID = 8958332535992103160L;
	private Agent agent;

	public Agent getAgent() {
		return agent;
	}

	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		agent = (Agent) (session.getAttribute(Constant.AGENT_SESSION_NAME));
		return SUCCESS;
	}

}
