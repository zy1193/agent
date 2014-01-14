package com.keicei.agent.domain.entity;

import java.util.Date;

public class OperateLog {

	private long id;
	private String loginId;
	private String ip;
	private int menuId;
	private String category;
	private String operateDetail;
	private Date operateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
		if (this.ip != null && this.ip.length() > 15) {
			this.ip = this.ip.substring(0, 14);
		}
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOperateDetail() {
		return operateDetail;
	}

	public void setOperateDetail(String operateDetail) {
		this.operateDetail = operateDetail;
		if (this.operateDetail != null && this.operateDetail.length() > 100) {
			this.operateDetail = this.operateDetail.substring(0, 99);
		}
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

}
