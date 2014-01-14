package com.keicei.agent.domain.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.keicei.util.Des;

/**
 * 充值卡
 * 
 * @author ZHANGYAN
 * 
 */
public class Card {

	/** DES密钥,8字节 **/
	private static final byte[] deskey = { 1, 2, 3, 4, 5, 6, 7, 8 };
	/** 在12位的密码后补4个0,DES算法要求明文是8字节的倍数 **/
	private static final String PADDING = "0000";

	private String no, password, productId, productName;
	private Date expireDate;

	private String orderSn, agentId, saleStatus, rechargeStatus, payOrderno,
			uid, phone;
	private int id, price, pvalue, counts, paymoney;
	private Date saleTime, buyTime, rechargeTime;
	private String status;
	private String agentName;
	private int count;
	private int sumPaymoney;
	private String despwd;
	private String brandid;

	public String getBrandid() {
		return brandid;
	}

	public void setBrandid(String brandid) {
		this.brandid = brandid;
	}

	public int getSumPaymoney() {
		return sumPaymoney;
	}

	public void setSumPaymoney(int sumPaymoney) {
		this.sumPaymoney = sumPaymoney;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setPassword(String password) {
		if (password != null && password.length() == 12) {
			String pwdsrc = password + PADDING;
			this.despwd = Hex.encodeHexString(Des.encrypt(pwdsrc.getBytes(),
					deskey));
		}
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductId() {
		return productId;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
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

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public String getRechargeStatus() {
		return rechargeStatus;
	}

	public void setRechargeStatus(String rechargeStatus) {
		this.rechargeStatus = rechargeStatus;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(Date saleTime) {
		this.saleTime = saleTime;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public Date getRechargeTime() {
		return rechargeTime;
	}

	public void setRechargeTime(Date rechargeTime) {
		this.rechargeTime = rechargeTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getPvalue() {
		return pvalue;
	}

	public void setPvalue(int pvalue) {
		this.pvalue = pvalue;
	}

	public int getCounts() {
		return counts;
	}

	public void setCounts(int counts) {
		this.counts = counts;
	}

	public String getPayOrderno() {
		return payOrderno;
	}

	public void setPayOrderno(String payOrderno) {
		this.payOrderno = payOrderno;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getPaymoney() {
		return paymoney;
	}

	public void setPaymoney(int paymoney) {
		this.paymoney = paymoney;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private static final Map<String, String> SALE_STATUS_DESC = new HashMap<String, String>(
			3),
			RECHARGE_STATUS_DESC = new HashMap<String, String>(2),
			STATUS_DESC = new HashMap<String, String>(2);
	static {
		SALE_STATUS_DESC.put("0", "未销售");
		SALE_STATUS_DESC.put("1", "销售中");
		SALE_STATUS_DESC.put("2", "已销售");
		RECHARGE_STATUS_DESC.put("0", "未充值");
		RECHARGE_STATUS_DESC.put("1", "已充值");
		STATUS_DESC.put("0", "正常");
		STATUS_DESC.put("1", "冻结");
	}

	public String getSaleStatusDesc() {
		String desc = SALE_STATUS_DESC.get(saleStatus);
		return desc == null ? "未知状态" : desc;
	}

	public String getRechargeStatusDesc() {
		String desc = RECHARGE_STATUS_DESC.get(rechargeStatus);
		return desc == null ? "未知状态" : desc;
	}

	public String getStatusDesc() {
		String desc = STATUS_DESC.get(status);
		return desc == null ? "未知状态" : desc;
	}

	public String getDespwd() {
		return despwd;
	}

	public void setDespwd(String despwd) {
		if (despwd != null && despwd.length() > 0) {
			try {
				password = new String(Des.decrypt(
						Hex.decodeHex(despwd.toCharArray()), deskey));
			} catch (DecoderException e) {
				throw new RuntimeException(e);
			}
			password = password.substring(0, 12);
		}
		this.despwd = despwd;
	}

	public static void main(String[] args) {

		String[] pwd = { "739448367121", "721856221923", "897664338588",
				"815833273381", "558366632498", "966216491424", "834221643986",
				"882641314465", "225534976776", "356556834213", "483984875152",
				"549653196684", "692532924662" };
		for (String str : pwd) {
			System.out.print("'"
					+ Hex.encodeHexString(Des.encrypt(
							(str + "0000").getBytes(), deskey)) + "',");
		}

	}

}
