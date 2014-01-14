package com.keicei.agent.domain.repository;

import java.util.List;

import com.keicei.agent.domain.entity.Menu;

public interface AgentMenuRepository {

	Menu select(int id);

	List<Menu> list();

}
