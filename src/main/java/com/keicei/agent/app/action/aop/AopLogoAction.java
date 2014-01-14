package com.keicei.agent.app.action.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.domain.entity.User;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class AopLogoAction extends ActionSupport {

	private static final long serialVersionUID = 8958332535992103160L;
	private User user;

	public User getUser() {
		return user;
	}

	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		user = (User) (session.getAttribute(Constant.USER_SESSION_NAME));
		return SUCCESS;
	}

}
