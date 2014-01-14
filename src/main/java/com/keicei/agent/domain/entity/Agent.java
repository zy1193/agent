package com.keicei.agent.domain.entity;

import java.util.List;

import com.keicei.agent.constant.Constant;

/**
 * 代理商基本信息
 * 
 * @author ZHANGYAN
 * 
 */
public class Agent {
	private String id;
	private String brandid;
	private String name;
	private String password;
	private String rank;
	private String status;
	private String superId;
	private String phoneNo, mobileNo, idCardNo, qq, wangwang, taobaoShopUrl,
			paipaiShopUrl;
	private String superName;
	private List<Agent> agents;

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public String getSuperName() {
		return superName;
	}

	public void setSuperName(String superName) {
		this.superName = superName;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSuperId() {
		return superId;
	}

	public void setSuperId(String superId) {
		this.superId = superId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWangwang() {
		return wangwang;
	}

	public void setWangwang(String wangwang) {
		this.wangwang = wangwang;
	}

	public String getTaobaoShopUrl() {
		return taobaoShopUrl;
	}

	public void setTaobaoShopUrl(String taobaoShopUrl) {
		this.taobaoShopUrl = taobaoShopUrl;
	}

	public String getPaipaiShopUrl() {
		return paipaiShopUrl;
	}

	public void setPaipaiShopUrl(String paipaiShopUrl) {
		this.paipaiShopUrl = paipaiShopUrl;
	}

	public String getStatusDesc() {
		return Constant.AGENT_STATUS_DESC.get(status);
	}

	public String getRankDesc() {
		return Constant.AGENT_RANK_DESC.get(rank);
	}
}
