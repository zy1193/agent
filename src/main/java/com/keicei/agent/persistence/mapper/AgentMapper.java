package com.keicei.agent.persistence.mapper;

import java.util.List;

import com.keicei.agent.domain.entity.Agent;
import com.keicei.common.BaseMapper;

public interface AgentMapper extends BaseMapper<Agent, String> {
	void changePassword(Agent agent);

	/** 查询上级为superid的代理商信息，已经代理商id **/
	Agent selectSubAgent(String id, String superId);

	/** 查询id的所有下级代理 **/
	List<Agent> listSubAgent(String id);

	/** 查询代理商的品牌 **/
	String brand(String agentId);

	/** 查询所有代理商的品牌(不包括未审核的代理商) **/
	List<Agent> brands();
	
	int audit(String brandid,String agentId);
	
	Agent select(String key);
}
