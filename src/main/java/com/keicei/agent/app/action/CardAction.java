package com.keicei.agent.app.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.constant.MenuId;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.entity.Card;
import com.keicei.agent.domain.entity.OperateLog;
import com.keicei.agent.domain.entity.Product;
import com.keicei.agent.domain.entity.User;
import com.keicei.agent.domain.manager.AgentManager;
import com.keicei.agent.domain.manager.CardListManager;
import com.keicei.agent.domain.manager.CardManager;
import com.keicei.agent.domain.manager.OperateLogManager;
import com.keicei.agent.domain.repository.ProductRepository;
import com.keicei.util.PaginationUtil;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class CardAction extends ActionSupport {

	private static final long serialVersionUID = -296046194205762800L;
	@Resource
	private ProductRepository productRepository;
	@Resource
	private CardManager cardManager;
	@Resource
	private AgentManager agentManager;
	@Resource
	private OperateLogManager operateLogManager;

	@Resource
	private CardListManager cardListmanager;
	
	private Product product;
	private int count, page, totalRecordCount;
	private String beginCardNo, endCardNo, rechargeStatus, agentId, errmsg,
			password, operFlag, status, recharge_status;;

	// cardsNo由 密码、品牌ID组成 如 12345@sky,43231@kc
	private String cardsNo;

	private List<List<Product>> productsList;
	private Card card;
	private Agent agent;
	private Date dateStart;// 开始日期
	private Date dateEnd;// 结束日期
	private List<Card> cards;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecharge_status() {
		return recharge_status;
	}

	public void setRecharge_status(String recharge_status) {
		this.recharge_status = recharge_status;
	}

	private int sumCount = 0;// 充值总笔数
	private int sumprice = 0;// 充值总金额

	public String getOperFlag() {
		return operFlag;
	}

	public void setOperFlag(String operFlag) {
		this.operFlag = operFlag;
	}

	public String getCardsNo() {
		return cardsNo;
	}

	public void setCardsNo(String cardsNo) {
		this.cardsNo = cardsNo;
	}

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

	public int getSumCount() {
		return sumCount;
	}

	public void setSumCount(int sumCount) {
		this.sumCount = sumCount;
	}

	public int getSumprice() {
		return sumprice;
	}

	public void setSumprice(int sumprice) {
		this.sumprice = sumprice;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getRechargeStatus() {
		return rechargeStatus;
	}

	public void setRechargeStatus(String rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}

	public int getPage() {
		return page;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
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

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<List<Product>> getProductsList() {
		return productsList;
	}

	public void setProductsList(List<List<Product>> productsList) {
		this.productsList = productsList;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	/**
	 * 冻结 or 解冻 制定的卡号
	 * 
	 * @return
	 */
	/*
	public String frozenOrThawCard() {
		List<String> cardList = null;
		if (cardsNo != null && cardsNo.length > 0) {
			// 按照brandid来分组,每个brand执行一次
			Map<String, List<String>> map = resolveCardsNo();
			for (Entry<String, List<String>> entry : map.entrySet()) {
				String brandid = entry.getKey();
				List<String> list = entry.getValue();
				cardList = cardManager.getCardPassFromCardsNo(agentId, list,
						brandid);

				if (cardList != null && !cardList.isEmpty()) {
					String status_flag = "";
					if ("frozen".equals(operFlag)) {
						status_flag = "1";
					} else if ("thaw".equals(operFlag)) {
						status_flag = "0";
					}
					cardManager
							.frozenOrThawCard(cardList, status_flag, brandid);
				}
			}
		}

		return SUCCESS;
	}
	*/
	
	public String frozenOrThawCard() {
		
		if (StringUtils.isNotBlank(cardsNo)) {
			//日志
			writeOperLog();
			// 按照brandid来分组,每个brand执行一次
			Map<String, List<String>> map = resolveCardsNo();
			
			String status_flag = "";
			if ("frozen".equals(operFlag)) {
				status_flag = "1";
			} else if ("thaw".equals(operFlag)) {
				status_flag = "0";
			}
			
			for (Entry<String, List<String>> entry : map.entrySet()) {
				String brandid = entry.getKey();
				List<String> list = entry.getValue();

				if (list != null && !list.isEmpty()) {
					cardManager
							.frozenOrThawCard(list, status_flag, brandid);
				}
			}
			return SUCCESS;
		}else{
			return INPUT;
		}

		
	}

	/**
	 * 冻结 or 解冻 制定的卡号
	 * 
	 * @return
	 */
	public String serialFrozenOrThawCard() {
		List<Card> cardList = null;
		// 序列号开始和结束来注销
		if (StringUtils.isNotBlank(beginCardNo)
				&& StringUtils.isNotBlank(endCardNo)) {
			//日志
			writeOperLog();
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
				errmsg="请输入正确的开始与结束卡号！";
				return ERROR;
			}
			
			//只能对未充值的卡做操作
			cardList = cardManager.getCardPassFromBeginToEndNo(agentId,
					beginCardNo, endCardNo, "0", status, null);
			if (cardList != null && !cardList.isEmpty()) {
				String status_flag = "";
				if ("frozen".equals(operFlag)) {
					status_flag = "1";
				} else if ("thaw".equals(operFlag)) {
					status_flag = "0";
				}
				
				// 按照brandid来分组,每个brand执行一次
				Map<String, List<String>> map = resolveCardsNo(cardList);
				
				for (Entry<String, List<String>> entry : map.entrySet()) {
					String brandid = entry.getKey();
					List<String> list = entry.getValue();

					if (list != null && !list.isEmpty()) {
						cardManager
								.frozenOrThawCard(list, status_flag, brandid);
					}
				}
			}else{
				errmsg="没有相匹配的代理卡！";
				return ERROR;
			}
			return SUCCESS;

		}else{
			return INPUT;
		}

		
	}

	public String buycard() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);

		if (product == null) {
			initProductsList();
			return INPUT;
		} else {
			OperateLog operateLog = new OperateLog();
			operateLog.setCategory("AM");
			operateLog.setIp(request.getRemoteAddr());
			operateLog.setLoginId(agent.getId());
			operateLog.setMenuId(MenuId.BUYC_ARD);
			operateLog.setOperateDetail("购卡:pid=" + product.getId() + ",count="
					+ count + ",password=" + password);
			operateLogManager.asyncLog(operateLog);

			// 购卡
			if (!validatePassword(password)) {
				errmsg = "密码不正确";
				return ERROR;
			}
			int retcode = cardManager.buycard(agent.getBrandid(),
					product.getId(), agent.getId(), password, count);
			if (retcode == Constant.RC_BUYCARD_SUCCESS) {
				return SUCCESS;
			} else {
				errmsg = Constant.RC_BUYCARD_DESC[retcode];
				return ERROR;
			}
		}
	}

	public String info() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);

		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AM");
		operateLog.setIp(request.getRemoteAddr());
		operateLog.setLoginId(agent.getId());
		operateLog.setMenuId(MenuId.SHOW_CARD);
		operateLog.setOperateDetail("查卡:cardno=" + card.getNo());
		operateLogManager.asyncLog(operateLog);

		card = cardManager.selectByNo(card.getNo(), agent.getId());
		return SUCCESS;
	}

	public String infoaop() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) (session.getAttribute(Constant.USER_SESSION_NAME));

		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AO");
		operateLog.setIp(request.getRemoteAddr());
		if (user != null)
			operateLog.setLoginId(user.getId());
		operateLog.setMenuId(MenuId.SHOW_CARD);
		operateLog.setOperateDetail("查卡:cardno=" + card.getNo());
		operateLogManager.asyncLog(operateLog);
		agent = agentManager.select(agent.getId());
		card = cardManager.selectByNo(card.getNo(), agent==null?null:agent.getId());
		return SUCCESS;
	}

	private void initProductsList() {
		productsList = new ArrayList<List<Product>>(4);
		productsList.add(productRepository.list("1"));
		productsList.add(productRepository.list("2"));

	}

	/** 验证密码 直接与sesion里的密码比较，不查询数据库 **/
	private boolean validatePassword(String password) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Agent agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);
		return password != null
				&& DigestUtils.md5Hex(password).equals(agent.getPassword());
	}

	/** 充值统计 **/
	public String listRechargestat() {
		if (page < 1)
			page = 1;
		Map<String, Object> map = new HashMap<String, Object>();
		if (agentId != null && !"".equals(agentId))
			map.put("agentId", agentId);
		if (rechargeStatus != null && !"999".equals(rechargeStatus))
			map.put("rechargeStatus", rechargeStatus);
		if (dateStart != null)
			map.put("dateStart", dateStart);

		if (dateEnd != null)
			map.put("dateEnd", dateEnd);
		totalRecordCount = cardManager.count(map);
		if (totalRecordCount > 0) {
			cards = cardManager.listRechargeStat(map, page,
					PaginationUtil.PAGE_SIZE);
			for (Card car : cards) {
				if (car != null && !"".equals(car)) {
					agent = agentManager.select(car.getAgentId());
					car.setAgentName(agent.getName());
				}
				sumCount += car.getCount();
				sumprice += car.getSumPaymoney();
			}
		}
		return SUCCESS;
	}

	//按照brandid分组，同样的brandid的卡放到一个key中
	//cardsNo: brandid@pass
	private Map<String, List<String>> resolveCardsNo() {
		// key=brandid,value=卡号list
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		if (StringUtils.isNotBlank(cardsNo)) {
			String [] cards=StringUtils.splitPreserveAllTokens(cardsNo, ",");
			for (String cardB : cards) {
				String[] cardBArr = StringUtils.splitPreserveAllTokens(cardB,
						"@");
				if (cardBArr != null && cardBArr.length == 2) {
					if (map.containsKey(cardBArr[0])) {
						map.get(cardBArr[0]).add(cardBArr[1]);
					} else {
						List<String> list = new ArrayList<String>();
						list.add(cardBArr[1]);
						map.put(cardBArr[0], list);
					}
				}
			}
		}
		return map;
	}
	
	
	//按照brandid分组，同样的brandid的卡放到一个key中
	private Map<String, List<String>> resolveCardsNo(List<Card> listCard) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		if (listCard != null && !listCard.isEmpty()) {
			for (Card card : listCard) {
				String brandid=card.getBrandid();
				String pass=card.getPassword();
				if (brandid!=null&&pass!=null&&map.containsKey(brandid)) {
					map.get(brandid).add(pass);
				} else  if(brandid!=null&&pass!=null){
					List<String> list = new ArrayList<String>();
					list.add(pass);
					map.put(brandid, list);
				}
			}
		}
		return map;
	}
	
	private void writeOperLog(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) (session.getAttribute(Constant.USER_SESSION_NAME));
		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AO");
		if ("frozen".equals(operFlag)) {
			operateLog.setMenuId(MenuId.CARD_FROZEN);
			operateLog.setOperateDetail("卡冻结:argsString="
					+ request.getQueryString());
		} else if ("thaw".equals(operFlag)) {
			operateLog.setMenuId(MenuId.CARD_THAW);
			operateLog.setOperateDetail("卡解冻:argsString="
					+ request.getQueryString());
		}
		
		operateLog.setIp(request.getRemoteAddr());
		if (user != null)
			operateLog.setLoginId(user.getId());
		
		operateLogManager.asyncLog(operateLog);
	}
}
