package com.keicei.agent.domain.entity;

import java.util.Date;

/**
 * 购卡订单
 * 
 * @author ZHANGYAN
 * 
 */
public class BuycardOrder {

	private int id;
	private String orderSn, brandid, agentId, productId, status, rmk,
			productName;
	private int productCount, productPrice;
	private long totalAmount;
	private Date orderTime;
	private int num;
	private String agentName;
	private int sumProductCount;
	private int sumTotalPrice;

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public int getSumProductCount() {
		return sumProductCount;
	}

	public void setSumProductCount(int sumProductCount) {
		this.sumProductCount = sumProductCount;
	}

	public int getSumTotalPrice() {
		return sumTotalPrice;
	}

	public void setSumTotalPrice(int sumTotalPrice) {
		this.sumTotalPrice = sumTotalPrice;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}

	public long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
