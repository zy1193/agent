package com.keicei.agent.domain.entity;

import com.keicei.agent.constant.Constant;

/**
 * 代理商帐号
 * 
 * @author ZHANGYAN
 * 
 */
public class Acct {
	/** 帐号 **/
	private String id;
	/** 押金 **/
	private int deposit;
	/** 可用余额 **/
	private long availableBalance;
	/** 冻结余额 **/
	private long frozenBalance;
	/** 状态 **/
	private String status;
    private long sAvailableBalance;//总可用余额
    private long sFrozenBalance;//总冻结余额
    private int count;
    private String brandid;
    
	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public long getsAvailableBalance() {
		return sAvailableBalance;
	}

	public void setsAvailableBalance(long sAvailableBalance) {
		this.sAvailableBalance = sAvailableBalance;
	}

	public long getsFrozenBalance() {
		return sFrozenBalance;
	}

	public void setsFrozenBalance(long sFrozenBalance) {
		this.sFrozenBalance = sFrozenBalance;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

	public long getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(long availableBalance) {
		this.availableBalance = availableBalance;
	}

	public long getFrozenBalance() {
		return frozenBalance;
	}

	public void setFrozenBalance(long frozenBalance) {
		this.frozenBalance = frozenBalance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return Constant.ACCT_STATUS_DESC.get(status);
	}
}
