package com.keicei.agent.domain.entity;

import java.sql.Date;

/**
 * 账户冻结日志
 * 
 * @author ZHANGYAN
 * 
 */
public class AcctFrozenLog {
	private int id;
	private String brandid, acctId, frozenType, frozenTypeDesc, frozenSn,
			status, rmk;

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	private long frozenAmount;
	private Date frozenTime;

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

	public String getFrozenType() {
		return frozenType;
	}

	public void setFrozenType(String frozenType) {
		this.frozenType = frozenType;
	}

	public String getFrozenTypeDesc() {
		return frozenTypeDesc;
	}

	public void setFrozenTypeDesc(String frozenTypeDesc) {
		this.frozenTypeDesc = frozenTypeDesc;
	}

	public String getFrozenSn() {
		return frozenSn;
	}

	public void setFrozenSn(String frozenSn) {
		this.frozenSn = frozenSn;
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

	public long getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(long frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public Date getFrozenTime() {
		return frozenTime;
	}

	public void setFrozenTime(Date frozenTime) {
		this.frozenTime = frozenTime;
	}
}
