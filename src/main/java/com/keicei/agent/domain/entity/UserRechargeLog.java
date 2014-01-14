package com.keicei.agent.domain.entity;

import java.util.Date;

/**
 * 用户代充
 * 
 * @author lifh
 * 
 */
public class UserRechargeLog {

	   
	private int id;
	private String orderSn, brandId, agentId,account, accountType,status,firstUse, rmk;
	private long rechargeAmount;
	private Date rechargeTime;
	
	
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getFirstUse() {
		return firstUse;
	}
	public void setFirstUse(String firstUse) {
		this.firstUse = firstUse;
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
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
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
	public long getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(long rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public Date getRechargeTime() {
		return rechargeTime;
	}
	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}
}
