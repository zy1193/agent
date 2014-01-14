package com.keicei.agent.domain.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.domain.entity.Menu;
import com.keicei.agent.domain.entity.MenuTree;
import com.keicei.agent.domain.manager.MenuManager;
import com.keicei.agent.persistence.mapper.MenuMapper;

@Service("menuManager")
public class MenuManagerImpl implements MenuManager {

	@Resource
	private MenuMapper menuMapper;

	@Override
	public int insert(Menu entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Menu entity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional(readOnly = true)
	@Override
	public Menu select(String key) {
		return menuMapper.select(key);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Menu> list(Map<String, Object> parameters) {
		return menuMapper.list(parameters);
	}

	@Override
	public List<Menu> list(Map<String, Object> parameters, int page,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int count(Map<String, Object> parameters) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional(readOnly = true)
	@Override
	public MenuTree userMenu(String userid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("category", "1");
		List<Menu> menuList = menuMapper.list(param);
		HashMap<Integer, Menu> menuMap;

		menuMap = new HashMap<Integer, Menu>(menuList.size());
		for (Menu menu : menuList) {
			Queue<Menu> fifo = new LinkedList<Menu>();
			fifo.offer(menu);
			do {
				fillSubMenu(fifo, menuList);
			} while (fifo.size() > 0);
			menuMap.put(menu.getId(), menu);
		}

		return new MenuTree(menuMap.get(-1000), menuList);
	}

	/** 使用一个先进先出的队列来加载菜单的子菜单 **/
	private void fillSubMenu(Queue<Menu> fifo, List<Menu> menuList) {
		Menu menu = fifo.poll();
		List<Menu> submenus = new ArrayList<Menu>();
		for (Menu m : menuList) {
			if (m.getSuperId() == menu.getId()) {
				submenus.add(m);
				fifo.offer(m);
			}
		}
		menu.setSubMenus(submenus);
	}

}
