package com.keicei.agent.app.action.aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.constant.MenuId;
import com.keicei.agent.domain.entity.Notice;
import com.keicei.agent.domain.entity.OperateLog;
import com.keicei.agent.domain.entity.User;
import com.keicei.agent.domain.manager.NoticeManager;
import com.keicei.agent.domain.manager.OperateLogManager;
import com.keicei.agent.domain.manager.UserManager;
import com.keicei.common.SecurityCode;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 登录和登出
 * 
 * @author ZHANGYAN
 * 
 */
@Controller
@Scope("prototype")
public class AopLoginAction extends ActionSupport {

	private static final long serialVersionUID = -4617804669541066598L;
	@Resource
	private UserManager userManager;
	@Resource
	private OperateLogManager operateLogManager;
	@Resource
	private NoticeManager noticeManager;

	private String errmsg, securityCode;
	private Notice notice;

	private User user;

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String login() {

		if (user == null) {
			return LOGIN;
		}

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();

		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AO");
		operateLog.setIp(request.getRemoteAddr());
		operateLog.setLoginId(user.getId());
		operateLog.setMenuId(MenuId.LOGIN);
		operateLog.setOperateDetail("登录:id=" + user.getId() + ",密码="
				+ user.getPassword());
		operateLogManager.asyncLog(operateLog);

		if (!securityCode.equals((String) session
				.getAttribute(SecurityCode.SESSION_ATTR_NAME))) {
			errmsg = "验证码不正确";
			return ERROR;
		}

		User u = userManager.select(user.getId());
		if (u == null) {
			errmsg = "登录帐号不存在";
			return ERROR;
		}

		if (!u.getPassword().equals(DigestUtils.md5Hex(user.getPassword()))) {
			errmsg = "密码错误";
			return ERROR;
		}

		// 查询菜单
		// MenuTree menuTree = menuManager.userMenu(user.getId());

		// 保存到session
		session.setAttribute(Constant.USER_SESSION_NAME, u);
		// session.setAttribute(Constant.MENU_SESSION_NAME, menuTree.getMenu());
		// session.setAttribute(Constant.MENULIST_SESSION_NAME,
		// menuTree.getMenus());

		return SUCCESS;
	}

	public String notice() {
		if (notice != null) {
			notice = noticeManager.select(notice.getBrandid());
		}
		return SUCCESS;
	}

	public String saveOrUpdate() {
		String brandid = notice.getBrandid();
		if (brandid != null && brandid.length() > 0) {
			if (noticeManager.update(notice) == 0) {
				noticeManager.insert(notice);
			}
			errmsg = "公告已保存";
		}
		return SUCCESS;
	}

	public String logout() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constant.USER_SESSION_NAME);
		OperateLog operateLog = new OperateLog();
		operateLog.setCategory("AO");
		operateLog.setIp(request.getRemoteAddr());
		if (user != null)
			operateLog.setLoginId(user.getId());
		operateLog.setMenuId(MenuId.LOGOUT);
		operateLog.setOperateDetail("登出:id="
				+ (user == null ? "" : user.getId()));
		operateLogManager.asyncLog(operateLog);

		session.removeAttribute(Constant.USER_SESSION_NAME);
		return SUCCESS;
	}
}
