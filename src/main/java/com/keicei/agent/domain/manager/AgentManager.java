package com.keicei.agent.domain.manager;

import java.util.List;

import com.keicei.agent.domain.entity.Acct;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.persistence.mapper.AgentMapper;
import com.keicei.common.BaseManager;

public interface AgentManager extends BaseManager<Agent, String, AgentMapper> {

	/** 代理商注册 **/
	void register(Agent agent, Acct acct);

	void changePassword(Agent agent);

	/** 查询上级为superid的代理商信息，已经代理商id **/
	Agent selectSubAgent(String id, String superId);

	/** 查询id的所有下级代理 **/
	List<Agent> listSubAgent(String id);

	/** 开户审核 **/
	void accountAudit(Agent agent);

	/**
	 * 冻结/解冻所有未销售的卡的状态
	 * 
	 * @param id
	 */
	void switchCardStatus(String id, String status);
	
	
	
	
}
