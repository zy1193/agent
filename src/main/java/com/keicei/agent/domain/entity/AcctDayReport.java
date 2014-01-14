package com.keicei.agent.domain.entity;

import java.util.Date;

/**
 * 日报
 * 
 * @author zy1193
 * 
 */
public class AcctDayReport {

	private int id, transCount;
	private String brandid, acctId, transType, subname;

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	private long payAmount, incomeAmount;
	private Date transDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTransCount() {
		return transCount;
	}

	public void setTransCount(int transCount) {
		this.transCount = transCount;
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

	public Date getTransDate() {
		return transDate;
	}

	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}

	public String getSubname() {
		return subname;
	}

	public void setSubname(String subname) {
		this.subname = subname;
	}

}
