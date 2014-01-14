package com.keicei.agent.app.action;

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
import com.keicei.agent.domain.entity.Card;
import com.keicei.agent.domain.entity.Product;
import com.keicei.agent.domain.manager.StockStatManager;
import com.keicei.agent.domain.repository.ProductRepository;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class StockStatAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8575016272002560654L;

	@Resource
	private StockStatManager stockStatManager;
	
	@Resource
	ProductRepository productRepository;

	private List<Card> stockstatlist;
	
	private List<Product> list;

	private String productId;
	
	private int sumpvalue=0, sumcounts=0;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public List<Card> getStockstatlist() {
		return stockstatlist;
	}

	public void setStockstatlist(List<Card> stockstatlist) {
		this.stockstatlist = stockstatlist;
	}

	public List<Product> getList() {
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

	public int getSumpvalue() {
		return sumpvalue;
	}

	public void setSumpvalue(int sumpvalue) {
		this.sumpvalue = sumpvalue;
	}

	public int getSumcounts() {
		return sumcounts;
	}

	public void setSumcounts(int sumcounts) {
		this.sumcounts = sumcounts;
	}

	public String stockstat() {
		Agent agent = selectuid();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agent_id", agent.getId());
		
		if(productId!=null && !"".equals(productId)){
			map.put("productId", productId);
		}
		
		list=productRepository.list();
		stockstatlist = stockStatManager.list(map);
		for (Card card : stockstatlist) {
			if(card.getProductId()!=null && !"".equals(card.getProductId())){
			card.setProductName(productRepository.select(card.getProductId()).getName());
			}
			sumpvalue+=card.getPvalue();
			sumcounts+=card.getCounts();
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
