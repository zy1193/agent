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
public class TabsFrameAction extends ActionSupport {

	private static final long serialVersionUID = -1529925646087711932L;
	@Resource
	private AgentMenuRepository agentMenuRepository;
	@Resource
	private UserMenuRepository userMenuRepository;
	private Menu menu, superMenu;
	private int id;

	public Menu getMenu() {
		return menu;
	}

	public Menu getSuperMenu() {
		return superMenu;
	}

	public String tabs() {
		menu = agentMenuRepository.select(id);
		superMenu = agentMenuRepository.select(menu.getSuperId());
		return SUCCESS;
	}

	public String usertabs() {
		menu = userMenuRepository.select(id);
		superMenu = userMenuRepository.select(menu.getSuperId());
		return SUCCESS;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
