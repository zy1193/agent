package com.keicei.agent.domain.entity;

import java.util.Date;

/**
 * 账户交易日志
 * 
 * @author ZHANGYAN
 * 
 */
public class AcctTransLog {

	private int id;
	private String brandid, acctId, transType, transSn, rmk, subname;

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	private long payAmount, incomeAmount, balance;
	private Date transTime;
	private int count;
	private String accName;
	private long sumPayCount;
	private long sumIncomeCount;

	public long getSumPayCount() {
		return sumPayCount;
	}

	public void setSumPayCount(long sumPayCount) {
		this.sumPayCount = sumPayCount;
	}

	public long getSumIncomeCount() {
		return sumIncomeCount;
	}

	public void setSumIncomeCount(long sumIncomeCount) {
		this.sumIncomeCount = sumIncomeCount;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAcctId() {
		return acctId;
	}

	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTransSn() {
		return transSn;
	}

	public void setTransSn(String transSn) {
		this.transSn = transSn;
	}

	public String getRmk() {
		return rmk;
	}

	public void setRmk(String rmk) {
		this.rmk = rmk;
	}

	public long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(long payAmount) {
		this.payAmount = payAmount;
	}

	public long getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(long incomeAmount) {
		this.incomeAmount = incomeAmount;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

	public String getSubname() {
		return subname;
	}

	public void setSubname(String subname) {
		this.subname = subname;
	}
}
