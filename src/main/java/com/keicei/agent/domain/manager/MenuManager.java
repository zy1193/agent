package com.keicei.agent.domain.manager;

import com.keicei.agent.domain.entity.Menu;
import com.keicei.agent.domain.entity.MenuTree;
import com.keicei.agent.persistence.mapper.MenuMapper;
import com.keicei.common.BaseManager;

public interface MenuManager extends BaseManager<Menu, String, MenuMapper> {

	/**
	 * 查询一个用户的菜单
	 * @param userid
	 * @return
	 */
	MenuTree userMenu(String userid);

}
