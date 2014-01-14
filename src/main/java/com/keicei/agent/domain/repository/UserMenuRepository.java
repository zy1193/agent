package com.keicei.agent.domain.repository;

import java.util.List;

import com.keicei.agent.domain.entity.Menu;

public interface UserMenuRepository {

	Menu select(int id);

	List<Menu> list();

}
