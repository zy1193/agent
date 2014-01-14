package com.keicei.agent.app.action;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.constant.MenuId;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.entity.OperateLog;
import com.keicei.agent.domain.entity.UserRechargeLog;
import com.keicei.agent.domain.manager.OperateLogManager;
import com.keicei.agent.domain.manager.RegisterManager;
import com.keicei.agent.domain.manager.UserLoginManager;
import com.keicei.agent.domain.manager.UserRechargeManager;
import com.keicei.util.GetClientIP;
import com.keicei.util.PasswordUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author del1
 * 
 */
@Scope("prototype")
@Controller
public class UserRechargeAction extends ActionSupport {
	private static Logger log = Logger.getLogger(UserRechargeAction.class);
	private static final long serialVersionUID = 1712983671527348446L;

	@Resource
	private UserRechargeManager userRechargeManager;

	@Resource
	private OperateLogManager operateLogManager;

	@Resource
	private UserLoginManager userLoginManager;

	@Resource
	private RegisterManager registerManager;


	private Agent agent;
	private String sum, uid, password, account, accountType, location, invitedby, invitedflag, errmsg,rechargeSum,rechargeCount;

	private int totalRecordCount, page;
	private int pagesize = 15;

	private List<UserRechargeLog> userRechargeList;
	private String orderSn,  status;
	private Date dateStart;// 开始日期
	private Date dateEnd;// 结束日期

	

	/**
	 * 验证用户是否存在
	 * 
	 * @return
	 */
	public String findUserInfo() {
		if (StringUtils.isBlank(account) || StringUtils.isBlank(accountType)) {
			return INPUT;
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);

		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AM");
		operateLog.setIp(request.getRemoteAddr());
		operateLog.setLoginId(agent.getId());
		operateLog.setMenuId(MenuId.USER_RECHARGE_QUERY);
		operateLog.setOperateDetail("代充_查询用户:agent=" + agent.getId() + ",account=" + account);
		operateLogManager.asyncLog(operateLog);

		// 代充用户信息
		if (agent != null) {
			JSONObject json = userRechargeManager.findUserInfo(account, accountType, agent.getBrandid());
			try {
				// 存在该用户
				if (json == null || !"0".equals(json.getString("code"))) {
					// existsCheck="no";
				} else {
					// existsCheck="yes";
				}
			} catch (JSONException e) {
				log.error(e);
			}
		}

		return SUCCESS;
	}

	public String userRecharge() {
		if (StringUtils.isBlank(account)) {
			return INPUT;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);

		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AM");
		operateLog.setIp(request.getRemoteAddr());
		operateLog.setLoginId(agent.getId());
		operateLog.setMenuId(MenuId.USER_RECHARGE);
		operateLog.setOperateDetail("代充:account=" + account + ",sum=" + sum + ",password=" + password);
		operateLogManager.asyncLog(operateLog);

		// 购卡
		if (!validatePassword(password)) {
			errmsg = "密码不正确";
			return ERROR;
		}
		String firstUse = "2";// 默认不是首次使用
		// step 1:验证用户是否存在
		JSONObject json = userRechargeManager.findUserInfo(account, accountType, agent.getBrandid());
		try {
			// 不存在该用户
			if (json == null || !"0".equals(json.getString("code"))) {
				firstUse = "1";
				// step 2:如果输入的是手机，则自动注册
				if ("mobile".equalsIgnoreCase(accountType)) {
					JSONObject json_re = autoRegisterForMobile();
					if (json_re == null || !"0".equals(json_re.getString("code"))) {
						errmsg = "自动注册手机号失败！";
						return ERROR;
					}
					uid = json_re.getString("uid");
					password = json_re.getString("password");

					// step 3:发短信告知用户注册成功
					registerForMobile();

					// step 4:完成首次登入
					String ip = GetClientIP.getIpAddr(ServletActionContext.getRequest());
					password=PasswordUtil.rc4Decrypt(password);
					JSONObject json_login = userLoginManager.getLoginInfo("mobile", account, password, ip, "web",
							agent.getBrandid());
					// 登录不成功
					if (json_login == null || !"0".equals(json_login.get("code"))) {
						errmsg = "用户登录失败！";
						return ERROR;
					}

				} else {
					errmsg = "不存在该用户，请输入该用户的手机号，完成自动注册！";
					return ERROR;
				}
			}else if("0".equals(json.getString("code"))){
				uid = json.getString("uid");
			}else{
				errmsg = "不存该用户，充值失败！";
				return ERROR;
			}
		} catch (JSONException e) {
			log.error(e);
		}
		// step 5：充值userRecharge(agent.getId(), uid, account, agent.getBrandid(), firstUse, sum);
		int retcode = userRechargeManager.userRecharge(agent.getId(),uid,account,accountType,agent.getBrandid(),firstUse,sum);
		if (retcode == Constant.RC_USERRECHARGE_SUCCESS) {
			return SUCCESS;
		} else {
			errmsg = Constant.RC_USERRECHARGE_DESC[retcode];
			return ERROR;
		}
	}

	public String findUserRerchargeLogList() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		agent = (Agent) request.getSession().getAttribute(Constant.AGENT_SESSION_NAME);
		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AM");
		operateLog.setIp(request.getRemoteAddr());
		operateLog.setLoginId(agent.getId());
		operateLog.setMenuId(MenuId.USER_RECHARGE_QUERY_LIST);
		operateLog.setOperateDetail("用户代充列表:queryString=" + request.getQueryString());
		operateLogManager.asyncLog(operateLog);

		
		if (page < 1)
			page = 1;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("agent_id", agent.getId());
		if (orderSn != null && !"".equals(orderSn)) {
			String ncardno = "%" + orderSn + "%";
			map.put("orderSn", ncardno);
		}
		if (agent != null && !"".equals(agent.getBrandid())) {
			map.put("brandId", agent.getBrandid());
		}
		
		if (sum != null && !"".equals(sum)) {
			map.put("rechargeAmount", sum);
		}

		if (account != null && !"".equals(account)) {
			map.put("account", "%"+account+"%");
		}
		if (dateStart != null)
			map.put("dateStart", dateStart);
		if (dateEnd != null){
			Calendar c=Calendar.getInstance();
			c.setTime(dateEnd);
			c.add(Calendar.DAY_OF_MONTH, 1);
			c.add(Calendar.MILLISECOND, -1);
			map.put("dateEnd", c.getTime());
		}
		if (status != null && !"999".equals(status))
			map.put("status", status);
		if (accountType != null && !"999".equals(accountType))
			map.put("accountType", accountType);

		 Map<String,Object> result_map= userRechargeManager.findUserRerchargeLogListCount(map);
		 if(result_map!=null&&!result_map.isEmpty()){
			 if(result_map.get("rechargeCount")!=null){
				 totalRecordCount= Integer.valueOf(result_map.get("rechargeCount").toString());
				 rechargeCount= result_map.get("rechargeCount").toString();
			 }else{
				 totalRecordCount=0;
				 rechargeCount="0";
			 }
			 if(result_map.get("rechargeSum")!=null){
				 rechargeSum =result_map.get("rechargeSum").toString();
			 }else{
				 rechargeSum="0";
			 }
		 }
		
		 
		if (totalRecordCount > 0) {
			userRechargeList = userRechargeManager.findUserRerchargeLogList(map, page, pagesize);
		}
		return SUCCESS;
	}

	/**
	 * 手机号码自动注册
	 * @return
	 */
	private JSONObject autoRegisterForMobile() {

		String mobileNo = account;

		/** 手机号码为空就跳转到输入页 **/
		if (mobileNo == null || mobileNo.length() == 0) {
			/** 位置不为空表示客户端访问,此时将联盟id保存到cookie中 **/
			if (location != null && location.length() > 0) {
				if (invitedby != null && invitedby.length() > 0) {
					HttpServletResponse response = ServletActionContext.getResponse();
					Cookie cookie = new Cookie("invitedby", invitedby);
					response.addCookie(cookie);
				}
			}
			return null;
		}

		HttpServletRequest request = ServletActionContext.getRequest();

		/** 从cookie读取联盟id **/
		readInvitedbyFromCookie(request);

		try {
			if (location == null)
				location = "";

			String ip = GetClientIP.getIpAddr(ServletActionContext.getRequest());
			String rsp = registerManager.autoRegisterForMobile(ip, mobileNo, invitedby, invitedflag, "web", location,
					agent.getBrandid());
			return analyzeStr(rsp);
		} catch (Exception e) {
			log.error(e, e);
			errmsg = "自动注册手机号失败！";
			return null;
		}
	}
	
	
	/**
	 * 手机号码注册
	 * @return
	 */
	private JSONObject registerForMobile() {
		String mobileNo = account;
		try {
			if (location == null)
				location = "";
			String ip = GetClientIP.getIpAddr(ServletActionContext.getRequest());
			String rsp = registerManager.registerForMobile(ip, mobileNo, invitedby, invitedflag, "web", location,
					agent.getBrandid());
			return analyzeStr(rsp);
		} catch (Exception e) {
			log.error(e, e);
			errmsg = "自动注册手机号失败！";
			return null;
		}
	}
	

	/**
	 * 从cookie中读取推荐人id(即联盟id)
	 */
	private void readInvitedbyFromCookie(HttpServletRequest request) {
		if (invitedby == null || invitedby.length() == 0) {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if ("invitedby".equals(cookie.getName())) {
					this.invitedby = cookie.getValue();
					break;
				}
			}
			if (invitedby == null || invitedby.length() == 0) {
				invitedby = Constant.INVITEBY_MAP.get(agent.getBrandid().toLowerCase());
			}
		}
		if (invitedflag == null || invitedflag.length() == 0) {
			Cookie[] cookies = request.getCookies();
			for (Cookie cookie : cookies) {
				if ("invitedflag".equals(cookie.getName())) {
					this.invitedflag = cookie.getValue();
					break;
				}
			}
			if (invitedflag == null || invitedflag.length() == 0) {
				invitedflag = "1";
			}
		}
	}

	/** 验证密码 直接与sesion里的密码比较，不查询数据库 **/
	private boolean validatePassword(String password) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Agent agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);
		return password != null && DigestUtils.md5Hex(password).equals(agent.getPassword());
	}

	private JSONObject analyzeStr(String str) {
		str = str.replace("=", ":'");
		str = "{" + str.replace("&", "',") + "'}";
		// System.out.println("str=="+str);
		JSONObject json = null;
		try {
			json = new JSONObject(str);
		} catch (JSONException e) {
			log.error(e, e);
			return null;
		}
		return json;
	}

	/*
	private String getSMSInfo() {
		String brandName = "";
		String brandid = agent.getBrandid();
		String brandURL = "";
		if ("uu".equalsIgnoreCase(brandid)) {
			brandName = "UU";
			brandURL = "http://www.uuwldh.com/";
		} else if ("efl".equalsIgnoreCase(brandid)) {
			brandName = "E分聊";
			brandURL = "http://www.eflwldh.com/";
		} else if ("sky".equalsIgnoreCase(brandid)) {
			brandName = "SKY";
			brandURL = "http://www.skywldh.com/";
		} else if ("kc".equalsIgnoreCase(brandid)) {
			brandName = "KC";
			brandURL = "http://www.keepc.com/";
		} else if ("3g".equalsIgnoreCase(brandid)) {
			brandName = "3G";
			brandURL = "http://www.3gwldh.com/";
		}
		String info = "欢迎使用" + brandName + "网络电话：" + brandURL + "\t\n" + "您的" + brandName + "账号：" + account + "，登录密码："
				+ PasswordUtil.rc4Decrypt(password) + ",\r\n请勿回复。";
		return info;
	}
*/
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getInvitedby() {
		return invitedby;
	}

	public void setInvitedby(String invitedby) {
		this.invitedby = invitedby;
	}

	public String getInvitedflag() {
		return invitedflag;
	}

	public void setInvitedflag(String invitedflag) {
		this.invitedflag = invitedflag;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<UserRechargeLog> getUserRechargeList() {
		return userRechargeList;
	}

	public void setUserRechargeList(List<UserRechargeLog> userRechargeList) {
		this.userRechargeList = userRechargeList;
	}


	public String getOrderSn() {
		return orderSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	
	public String getRechargeSum() {
		return rechargeSum;
	}

	public void setRechargeSum(String rechargeSum) {
		this.rechargeSum = rechargeSum;
	}

	public String getRechargeCount() {
		return rechargeCount;
	}

	public void setRechargeCount(String rechargeCount) {
		this.rechargeCount = rechargeCount;
	}
	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

}
