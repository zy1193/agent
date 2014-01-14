package com.keicei.agent.app.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.constant.MenuId;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.entity.Card;
import com.keicei.agent.domain.entity.OperateLog;
import com.keicei.agent.domain.entity.User;
import com.keicei.agent.domain.manager.CardListManager;
import com.keicei.agent.domain.manager.OperateLogManager;
import com.keicei.agent.domain.repository.ProductRepository;
import com.keicei.util.PwdUtil;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class CardListAction extends ActionSupport {

	private static final long serialVersionUID = -5755367302131155617L;

	@Resource
	CardListManager cardListmanager;

	@Resource
	ProductRepository productRepository;
	@Resource
	private OperateLogManager operateLogManager;

	private List<Card> cardlist;

	private String errmsg;

	private String cardno, cardpassword;// 卡号

	private Date dateStart;// 开始日期

	private Date dateEnd;// 结束日期

	private String saleStatus;// 销售状态

	private int totalRecordCount, page;
	private int pagesize = 15;
	private String proName;
	
	private Card card;
	
	private String agentId,status,rechargeStatus, beginCardNo, endCardNo;

	public String getBeginCardNo() {
		return beginCardNo;
	}

	public void setBeginCardNo(String beginCardNo) {
		this.beginCardNo = beginCardNo;
	}

	public String getEndCardNo() {
		return endCardNo;
	}

	public void setEndCardNo(String endCardNo) {
		this.endCardNo = endCardNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRechargeStatus() {
		return rechargeStatus;
	}

	public void setRechargeStatus(String rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public List<Card> getCardlist() {
		return cardlist;
	}

	public void setCardlist(List<Card> cardlist) {
		this.cardlist = cardlist;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getCardpassword() {
		return cardpassword;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public void setCardpassword(String cardpassword) {
		this.cardpassword = cardpassword;
	}

	// 以下为主方法
	public String cardlist() {

		Agent agent = selectuid();

		HttpServletRequest request = ServletActionContext.getRequest();
		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AM");
		operateLog.setIp(request.getRemoteAddr());
		operateLog.setLoginId(agent.getId());
		operateLog.setMenuId(MenuId.LIST_CARD);
		operateLog.setOperateDetail("卡列表:queryString="
				+ request.getQueryString());
		operateLogManager.asyncLog(operateLog);

		if (page < 1)
			page = 1;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agent_id", agent.getId());
		if (cardno != null && !"".equals(cardno)) {
			String ncardno = "%" + cardno + "%";
			map.put("cardno", ncardno);
		}
		if (cardpassword != null && !"".equals(cardpassword)) {
			String cpassword = "%" + cardpassword + "%";
			map.put("cardpassword", cpassword);
		}
		map.put("despwd", PwdUtil.despwd(cardpassword));
		if (dateStart != null)
			map.put("dateStart", dateStart);
		if (dateEnd != null)
			map.put("dateEnd", dateEnd);
		if (saleStatus != null && !"999".equals(saleStatus))
			map.put("saleStatus", saleStatus);
		
		totalRecordCount = cardListmanager.count(map);
		if (totalRecordCount > 0) {
			cardlist = cardListmanager.list(map, page, pagesize);
			for (int i = 0; i < cardlist.size(); i++) {
				if (cardlist.get(i).getProductId() != null
						&& !"".equals(cardlist.get(i).getProductId())) {
					proName = productRepository.select(
							cardlist.get(i).getProductId()).getName();
					if (proName.indexOf("(") != -1) {
						cardlist.get(i).setProductName(
								proName.substring(0, proName.indexOf("(")));
					} else {
						cardlist.get(i).setProductName(proName);
					}
				}
			}
		}
		return SUCCESS;
	}
	
	
	//卡冻结  --查询卡列表
	public String queryCardList() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) (session.getAttribute(Constant.USER_SESSION_NAME));

		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AO");
		operateLog.setIp(request.getRemoteAddr());
		if (user != null)
			operateLog.setLoginId(user.getId());
		operateLog.setMenuId(MenuId.CARD_FROZEN_QUERY);
		operateLog.setOperateDetail("卡冻结查询:queryString="
				+ request.getQueryString());
		operateLogManager.asyncLog(operateLog);

		if (page < 1)
			page = 1;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agent_id", agentId);
		if (cardno != null && !"".equals(cardno)) {
			String ncardno = "%" + cardno + "%";
			map.put("cardno", ncardno);
		}
		if (cardpassword != null && !"".equals(cardpassword)) {
			String cpassword = "%" + cardpassword + "%";
			map.put("cardpassword", cpassword);
		}
		map.put("despwd", PwdUtil.despwd(cardpassword));
		if (dateStart != null)
			map.put("dateStart", dateStart);
		if (dateEnd != null){
			Calendar c=Calendar.getInstance();
			c.setTime(dateEnd);
			c.add(Calendar.DAY_OF_MONTH, 1);
			c.add(Calendar.MILLISECOND, -1);
			map.put("dateEnd", c.getTime());
		}
			
		if (saleStatus != null && !"999".equals(saleStatus))
			map.put("saleStatus", saleStatus);
		
		if (rechargeStatus != null && !"999".equals(rechargeStatus))
			map.put("rechargeStatus", rechargeStatus);
		
		if (status != null && !"999".equals(status))
			map.put("status", status);
		
		totalRecordCount = cardListmanager.count(map);
		if (totalRecordCount > 0) {
			cardlist = cardListmanager.list(map, page, pagesize);
			for (int i = 0; i < cardlist.size(); i++) {
				if (cardlist.get(i).getProductId() != null
						&& !"".equals(cardlist.get(i).getProductId())) {
					proName = productRepository.select(
							cardlist.get(i).getProductId()).getName();
					if (proName.indexOf("(") != -1) {
						cardlist.get(i).setProductName(
								proName.substring(0, proName.indexOf("(")));
					} else {
						cardlist.get(i).setProductName(proName);
					}
				}
			}
		}
		return SUCCESS;
	}
	
	
	//卡冻结  --查询卡列表--根据开始和结束卡号
	public String querySerialCardList() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) (session.getAttribute(Constant.USER_SESSION_NAME));

		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AO");
		operateLog.setIp(request.getRemoteAddr());
		if (user != null)
			operateLog.setLoginId(user.getId());
		operateLog.setMenuId(MenuId.CARD_FROZEN_QUERY);
		operateLog.setOperateDetail("卡冻结查询-根据开始和结束卡号:queryString="
				+ request.getQueryString());
		operateLogManager.asyncLog(operateLog);

		if (page < 1)
			page = 1;
		
		if(StringUtils.isNotBlank(beginCardNo)&&StringUtils.isNotBlank(endCardNo)){
			//先计算是否包含该考号
			Map<String, Object> assignMap = new HashMap<String, Object>();
			List<String> assignList=new ArrayList<String>();
			assignList.add(beginCardNo);
			if(!beginCardNo.equals(endCardNo)){
				assignList.add(endCardNo);
			}
			assignMap.put("assignCards", assignList);
			int count = cardListmanager.count(assignMap);
			//开始卡号和结束卡号不一样，则应该为两条记录，一样则为同一条记录
			//否则返回错误
			if((!beginCardNo.equals(endCardNo)&&count!=2)||(beginCardNo.equals(endCardNo)&&count!=1)){
				return SUCCESS;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("agent_id", agentId);
		if (saleStatus != null && !"999".equals(saleStatus))
			map.put("saleStatus", saleStatus);
		
		if (status != null && !"999".equals(status))
			map.put("status", status);
		
		map.put("begin", beginCardNo);
		map.put("end", endCardNo);
		totalRecordCount = cardListmanager.count(map);
		if (totalRecordCount > 0) {
			cardlist = cardListmanager.list(map, page, pagesize);
			for (int i = 0; i < cardlist.size(); i++) {
				if (cardlist.get(i).getProductId() != null
						&& !"".equals(cardlist.get(i).getProductId())) {
					proName = productRepository.select(
							cardlist.get(i).getProductId()).getName();
					if (proName.indexOf("(") != -1) {
						cardlist.get(i).setProductName(
								proName.substring(0, proName.indexOf("(")));
					} else {
						cardlist.get(i).setProductName(proName);
					}
				}
			}
		}
		return SUCCESS;
	}
	
	
	

	/***
	 * 修改卡的状态
	 * 
	 * @return
	 */
	public String changestatus() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String status = request.getParameter("status");
		String id = request.getParameter("id");

		Agent agent = selectuid();
		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AM");
		operateLog.setIp(request.getRemoteAddr());
		operateLog.setLoginId(agent.getId());
		operateLog.setMenuId(MenuId.CHANGE_CARD_STATUS);
		operateLog
				.setOperateDetail("修改卡状态:cardId==" + id + ",status=" + status);
		operateLogManager.asyncLog(operateLog);

		if (!"".equals(status) && status != null && !"".equals(id)
				&& id != null) {
			Card card = new Card();
			card.setId(Integer.parseInt(id));
			card.setSaleStatus(status);
			cardListmanager.changeStatus(card);
		}
		return SUCCESS;
	}

	// 以下为辅助方法w

	/** 查询当前登录用户的用户ID * */
	public Agent selectuid() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Agent agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);
		return agent;
	}

}
