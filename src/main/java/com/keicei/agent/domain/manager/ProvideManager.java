package com.keicei.agent.domain.manager;

import java.util.List;

import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.persistence.mapper.Provide;
import com.keicei.common.BaseManager;

public interface ProvideManager extends BaseManager<Agent, String, Provide> {
	/**
	 * 查询下级
	 * 
	 * @param id
	 * @return
	 */
	List<Agent> listSubAgent(String id);

}
