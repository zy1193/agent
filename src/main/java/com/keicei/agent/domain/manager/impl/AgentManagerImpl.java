package com.keicei.agent.domain.manager.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.domain.entity.Acct;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.entity.Card;
import com.keicei.agent.domain.manager.AgentManager;
import com.keicei.agent.domain.repository.BrandRepository;
import com.keicei.agent.domain.rpc.SwitchCardStatusService;
import com.keicei.agent.persistence.mapper.AcctMapper;
import com.keicei.agent.persistence.mapper.AgentMapper;
import com.keicei.agent.persistence.mapper.CardMapper;
import com.keicei.util.PaginationUtil;

@Service("agentManager")
public class AgentManagerImpl implements AgentManager {

	@Resource
	private AgentMapper agentMapper;
	@Resource
	private AcctMapper acctMapper;
	@Resource
	private CardMapper cardMapper;
	@Resource
	private SwitchCardStatusService switchCardStatusService;
	@Resource
	private BrandRepository brandRepository;

	private static final Map<Integer, String> RANK = new HashMap<Integer, String>(
			4);
	static {
		RANK.put(30000 * 1000, "0");
		RANK.put(10000 * 1000, "1");
		RANK.put(5000 * 1000, "2");
		RANK.put(0 * 1000, "3");
	}

	@Override
	public int insert(Agent entity) {
		return agentMapper.insert(entity);
	}

	@Transactional
	@Override
	public int update(Agent entity) {
		return agentMapper.update(entity);
	}

	@Transactional
	@Override
	public int delete(String key) {
		int effect = agentMapper.delete(key);
		if (effect > 0)
			acctMapper.delete(key);
		return effect;
	}

	@Transactional(readOnly = true)
	@Override
	public Agent select(String key) {
		return agentMapper.select(key);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Agent> list(Map<String, Object> parameters) {
		return agentMapper.list(parameters);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Agent> list(Map<String, Object> parameters, int page,
			int pageSize) {
		PaginationUtil.generatePageParameter(parameters, page, pageSize);
		return agentMapper.list(parameters);
	}

	@Transactional(readOnly = true)
	@Override
	public int count(Map<String, Object> parameters) {
		return agentMapper.count(parameters);
	}

	@Transactional
	@Override
	public void register(Agent agent, Acct acct) {
		/** 刚注册未审核的用户品牌为*,审核时才赋予正确的品牌 **/
		acct.setBrandid("*");
		acct.setId(agent.getId());
		acct.setStatus("3");
		acct.setDeposit(acct.getDeposit() * 1000);

		agent.setBrandid("*");
		agent.setRank(RANK.get(acct.getDeposit()));
		agent.setPassword(DigestUtils.md5Hex(agent.getPassword()));
		agent.setStatus("0");

		agentMapper.insert(agent);
		acctMapper.insert(acct);
	}

	@Transactional
	@Override
	public void changePassword(Agent agent) {
		agentMapper.changePassword(agent);
	}

	@Transactional(readOnly = true)
	@Override
	public Agent selectSubAgent(String id, String superId) {
		return agentMapper.selectSubAgent(id, superId);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Agent> listSubAgent(String id) {
		return agentMapper.listSubAgent(id);
	}

	@Transactional
	@Override
	public void accountAudit(Agent agent) {

		String agentId = agent.getId();
		String brandid = agent.getBrandid();

		/** 将账户状态修改为正常,并写入品牌 **/
		acctMapper.audit(brandid, agentId);
		agentMapper.audit(brandid, agentId);
	}

	@Override
	public void switchCardStatus(String agentId, String status) {

		String brandid = brandRepository.brand(agentId);
		if (brandid == null) {
			throw new RuntimeException("no brand");
		}

		Card card = new Card();
		card.setStatus(status);
		card.setAgentId(agentId);
		card.setBrandid(brandid);

		List<Card> passwords = passwords(card);
		if (passwords == null) {
			return;
		}

		int i = 0;
		StringBuilder buf = new StringBuilder();
		for (Card password : passwords) {
			buf.append(password.getPassword()).append(',');
			if (i < 49) {
				i++;
			} else {
				buf.deleteCharAt(buf.length() - 1);
				switchCardStatusService.switchCardStatus(brandid,
						buf.toString(), status);
				buf = new StringBuilder();
				i = 0;
			}
		}
		if (i != 0) {
			buf.deleteCharAt(buf.length() - 1);
			switchCardStatusService.switchCardStatus(brandid, buf.toString(),
					status);
		}

		// 修改卡状态
		switchStatus(card);
	}

	@Transactional(readOnly = true)
	private List<Card> passwords(Card card) {
		Card param = new Card();
		param.setAgentId(card.getAgentId());
		if ("0".equals(card.getStatus())) {
			param.setStatus("1");
		} else {
			param.setStatus("0");
		}
		return cardMapper.tobeSwitch(param);
	}

	@Transactional
	private void switchStatus(Card card) {
		cardMapper.switchStatus(card);
	}
}
