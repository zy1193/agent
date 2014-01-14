package com.keicei.agent.domain.entity;

import java.util.Date;

/**
 * 卡统计信息
 * 
 * @author ZHANGYAN
 * 
 */
public class CardStat {

	/** 数量 **/
	private int count;
	/** 总金额 **/
	private long totalAmount;
	/** 商品信息 **/
	private String productId, productName;
	/** 单价 **/
	private int price;
	/** 代理商 **/
	private String agentId, agentName;
	/** 购卡日期 **/
	private Date buyDate, startDate, endDate;
	private String brandid;

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
