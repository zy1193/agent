package com.keicei.agent.domain.entity;

import java.util.List;

public class MenuTree {
	private Menu menu;
	private List<Menu> menus;

	public MenuTree(Menu menu, List<Menu> menus) {
		super();
		this.menu = menu;
		this.menus = menus;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
}
