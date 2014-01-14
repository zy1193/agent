package com.keicei.agent.app.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.entity.BuycardOrder;
import com.keicei.agent.domain.entity.Product;
import com.keicei.agent.domain.manager.AgentManager;
import com.keicei.agent.domain.manager.BuycardOrderManager;
import com.keicei.agent.domain.repository.ProductRepository;
import com.keicei.util.PaginationUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author guoqidi
 * @date 2011-9-5 下午03:09:52
 * @version V1.1
 */
@Controller
@Scope("prototype")
public class BuycardOrderAction extends ActionSupport {

	private static final long serialVersionUID = 7369752689350595878L;
	@Resource
	private BuycardOrderManager buycardOrderManager;
	@Resource
	ProductRepository productRepository;
	@Resource
	private AgentManager agentManager;
	private int totalRecordCount;
	private int page;
	private Date dateStart;// 开始日期
	private Date dateEnd;// 结束日期
	private String productId;
	private List<Product> products;
	private String agentId;
	private int sumcount = 0;
	private int sumPrice = 0;

	public int getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(int sumPrice) {
		this.sumPrice = sumPrice;
	}

	public int getSumcount() {
		return sumcount;
	}

	public void setSumcount(int sumcount) {
		this.sumcount = sumcount;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	private List<BuycardOrder> buycardOrders;

	public List<BuycardOrder> getBuycardOrders() {
		return buycardOrders;
	}

	public void setBuycardOrders(List<BuycardOrder> buycardOrders) {
		this.buycardOrders = buycardOrders;
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

	/** 提卡日报表 **/
	public String buycardReport() {
		products = productRepository.list();
		if (page < 1)
			page = 1;
		Map<String, Object> map = new HashMap<String, Object>();
		if (agentId != null && !"".equals(agentId))
			map.put("agentId", agentId);
		if (dateStart != null)
			map.put("dateStart", dateStart);
		if (dateEnd != null)
			map.put("dateEnd", dateEnd);
		if (productId != null && !"".equals(productId)) {
			map.put("productId", productId);
		}
		totalRecordCount = buycardOrderManager.count(map);
		if (totalRecordCount > 0) {
			buycardOrders = buycardOrderManager.list(map, page,
					PaginationUtil.PAGE_SIZE);
			for (BuycardOrder cardOrder : buycardOrders) {
				if (cardOrder != null && !"".equals(cardOrder)) {
					cardOrder.setProductName(productRepository.select(
							cardOrder.getProductId()).getName());
					Agent agent = agentManager.select(cardOrder.getAgentId());
					if (agent != null) {
						cardOrder.setAgentName(agent.getName());
					} else {
						cardOrder.setAgentName("");
					}
				}
				sumcount += cardOrder.getSumProductCount();
				sumPrice += cardOrder.getSumTotalPrice();

			}
		}
		return SUCCESS;
	}
}
