package com.keicei.agent.app.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.domain.entity.Menu;
import com.keicei.agent.domain.repository.AgentMenuRepository;
import com.keicei.agent.domain.repository.UserMenuRepository;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class MenuAction extends ActionSupport {

	private static final long serialVersionUID = -6305413139849306769L;

	@Resource
	private AgentMenuRepository agentMenuRepository;
	@Resource
	private UserMenuRepository userMenuRepository;
	private Menu root;

	public Menu getRoot() {
		return root;
	}

	public void setRoot(Menu root) {
		this.root = root;
	}

	public String menutree() {
		root = agentMenuRepository.select(0);
		return SUCCESS;
	}

	public String userMenutree() {
		root = userMenuRepository.select(-1000);
		return SUCCESS;
	}
}
