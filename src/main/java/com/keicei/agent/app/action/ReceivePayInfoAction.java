package com.keicei.agent.app.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.domain.entity.Card;
import com.keicei.agent.domain.manager.CardManager;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 接收充值平台的通知修改卡的充值状态
 * 
 * @author zy1193
 *         测试：
 *         http://localhost:8080/agent/amp/changecardinfo.action?orderid=
 *         20110512000030
 *         &kc=82987656&paytype=98&phone=15820479265&password=787942593382
 *         &money=1
 */
@Scope("prototype")
@Controller
public class ReceivePayInfoAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8601339160267191760L;
	private static final Logger log = Logger
			.getLogger(ReceivePayInfoAction.class);

	@Resource
	private CardManager cardManager;

	private String orderid, kc, paytype, phone, sign, password;

	private int money;
	private int code;

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getKc() {
		return kc;
	}

	public void setKc(String kc) {
		this.kc = kc;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	private static final List<String> paytypes = new ArrayList<String>();
	static {
		paytypes.add("98");
		paytypes.add("5");
	}

	/**
	 * 接收充值平台的通知修改卡的充值状态
	 */
	public String procpayinfo() {
		int paymoney = money * 1000;

		// 是否是KC卡充值
		if (!paytypes.contains(paytype)) {
			code = 1;
			return ERROR;
		}
		// sign是否正确
		if (!getsign().equals(sign)) {
			code = 1;
			return ERROR;
		}

		Card card = new Card();
		card.setPassword(password);
		card.setPayOrderno(orderid);
		card.setPhone(phone);
		card.setUid(kc);
		card.setPaymoney(paymoney);
		try {
			cardManager.update(card);
			code = 0;
			return SUCCESS;
		} catch (Exception e) {
			log.error(e, e);
			code = 1;
			return ERROR;
		}
	}

	/**
	 * 生成sign
	 * 
	 * @return
	 */
	private String getsign() {
		return DigestUtils.md5Hex("payment" + kc + orderid + money + paytype);
	}
}
