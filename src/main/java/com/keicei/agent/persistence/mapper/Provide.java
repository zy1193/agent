package com.keicei.agent.persistence.mapper;

import java.util.List;

import com.keicei.agent.domain.entity.Acct;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.common.BaseMapper;

public interface Provide extends BaseMapper<Agent, String> {

	/**
	 * 查询下级
	 * 
	 * @param id
	 * @return
	 */
	List<Agent> listSubAgent(String id);

	/**
	 * 授信
	 */
	int provide(Acct acct);
}
