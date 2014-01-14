package com.keicei.agent.domain.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.repository.BrandRepository;
import com.keicei.agent.persistence.mapper.AgentMapper;

@Repository("brandRepository")
public class BrandRepositoryImpl implements BrandRepository {

	@Resource
	private AgentMapper agentMapper;

	private final Map<String, String> agentMap = new HashMap<String, String>();

	@SuppressWarnings("unused")
	@PostConstruct
	private void postConstruct() {
		String brandid;
		List<Agent> agents = list();
		for (Agent agent : agents) {
			brandid = agent.getBrandid();
			if (!"*".equals(brandid)) {
				agentMap.put(agent.getId(), brandid);
			}
		}
	}

	@Override
	public String brand(String agentId) {
		String brandid = agentMap.get(agentId);
		if (brandid == null) {
			brandid = brandid(agentId);
			if (brandid != null) {
				agentMap.put(agentId, brandid);
			}
		}
		return brandid;
	}

	@Transactional(readOnly = true)
	private List<Agent> list() {
		return agentMapper.brands();
	}

	@Transactional(readOnly = true)
	private String brandid(String agentId) {
		return agentMapper.brand(agentId);
	}

}
