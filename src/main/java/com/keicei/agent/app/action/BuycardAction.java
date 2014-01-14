package com.keicei.agent.app.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.entity.BuycardOrder;
import com.keicei.agent.domain.manager.BuycardManager;
import com.keicei.agent.domain.repository.ProductRepository;
import com.keicei.util.PaginationUtil;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class BuycardAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5999480513589249726L;

	@Resource
	BuycardManager buycardManager;
	
	@Resource
	ProductRepository productRepository;

	private List<BuycardOrder> buycardOrderList;

	private int totalRecordCount, page;
	
	private String orderSn;
	
	private Date dateStart;// 开始日期

	private Date dateEnd;// 结束日期
	
	private String orderStatus;

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

	public List<BuycardOrder> getBuycardOrderList() {
		return buycardOrderList;
	}

	public void setBuycardOrderList(List<BuycardOrder> buycardOrderList) {
		this.buycardOrderList = buycardOrderList;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
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

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String buycardorderlist() {
		if (page < 1)
			page = 1;
		Agent agent = selectuid();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agent_id", agent.getId());
		
		if (orderSn != null && !"".equals(orderSn)) {
			String order_sn = "%" + orderSn + "%";
			map.put("orderSn", order_sn);
		}
		
		if (dateStart != null)
			map.put("dateStart", dateStart);
		
		if (dateEnd != null)
			map.put("dateEnd", dateEnd);
		
		if (orderStatus != null && !"999".equals(orderStatus))
			map.put("orderStatus", orderStatus);
		
		totalRecordCount = buycardManager.count(map);// 得到总行数
		if (totalRecordCount > 0) {
			buycardOrderList = buycardManager.list(map, page,
					PaginationUtil.PAGE_SIZE);
			for (BuycardOrder cardOrder : buycardOrderList) {
				if(cardOrder!=null && !"".equals(cardOrder)){
					cardOrder.setProductName(productRepository.select(cardOrder.getProductId()).getName());
				}
			}
		}
		return SUCCESS;
	}

	/** 查询当前登录用户的用户ID * */
	public Agent selectuid() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Agent agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);
		return agent;
	}
}
