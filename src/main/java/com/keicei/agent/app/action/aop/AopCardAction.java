package com.keicei.agent.app.action.aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.constant.MenuId;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.entity.Card;
import com.keicei.agent.domain.entity.OperateLog;
import com.keicei.agent.domain.entity.User;
import com.keicei.agent.domain.manager.AgentManager;
import com.keicei.agent.domain.manager.CardManager;
import com.keicei.agent.domain.manager.OperateLogManager;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class AopCardAction extends ActionSupport {

	private static final long serialVersionUID = 5532524468807517975L;
	@Resource
	private CardManager cardManager;
	@Resource
	private AgentManager agentManager;
	@Resource
	private OperateLogManager operateLogManager;

	private String keytype, key, errmsg;
	private Card card;
	private Agent agent;

	public String getKeytype() {
		return keytype;
	}

	public void setKeytype(String keytype) {
		this.keytype = keytype;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String trace() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) (session.getAttribute(Constant.USER_SESSION_NAME));

		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AO");
		operateLog.setIp(request.getRemoteAddr());
		if (user != null)
			operateLog.setLoginId(user.getId());
		operateLog.setMenuId(MenuId.TRACE);
		operateLog.setOperateDetail("卡追踪:keytype=" + keytype + ",key=" + key);
		operateLogManager.asyncLog(operateLog);

		if (keytype == null)
			keytype = "0";

		if (key != null && key.length() > 0) {
			if ("0".equals(keytype)) {
				card = cardManager.selectByNo(key, null);
			} else if ("1".equals(keytype)) {
				card = cardManager.selectByPassword(key);
			}
			if (card != null) {
				agent = agentManager.select(card.getAgentId());
			} else {
				errmsg = "找不到符合条件的充值卡信息";
			}
		}

		return SUCCESS;
	}
}
