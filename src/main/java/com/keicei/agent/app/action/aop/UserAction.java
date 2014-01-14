package com.keicei.agent.app.action.aop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.domain.entity.User;
import com.keicei.agent.domain.manager.UserManager;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class UserAction extends ActionSupport {

	private static final Logger log = Logger.getLogger(UserAction.class);
	private static final long serialVersionUID = 1962310734088325548L;

	@Resource
	private UserManager userManager;
	private String myid;
	private String oldpwd, newpwd;
	private String errmsg;
	private int errcode;

	public void setOldpwd(String oldpwd) {
		this.oldpwd = oldpwd;
	}

	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getMyid() {
		return myid;
	}

	public void setMyid(String myid) {
		this.myid = myid;
	}

	public String changePassword() {
		if (oldpwd == null) {
			return INPUT;
		} else {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION_NAME);
			User u = userManager.select(user.getId());
			if (DigestUtils.md5Hex(oldpwd).equals(u.getPassword())) {
				// 修改密码
				u.setPassword(DigestUtils.md5Hex(newpwd));
				userManager.changePassword(u);

				// 更新session里的密码
				user.setPassword(u.getPassword());
				// session.setAttribute(Constant.USER_SESSION_NAME, user);

				return SUCCESS;
			} else {
				errmsg = "原密码输入不正确";
				return ERROR;
			}
		}
	}

	private List<User> userList;
	private User user;

	public List<User> getUserList() {
		return userList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String list() {
		if (user != null) {
			String id = user.getId();
			Map<String, Object> param = new HashMap<String, Object>();
			if (id != null && id.length() > 0) {
				param.put("id", "%" + id + "%");
			}
			userList = userManager.list(param);
			myid = ((User) ServletActionContext.getRequest().getSession()
					.getAttribute(Constant.USER_SESSION_NAME)).getId();
		}
		return SUCCESS;
	}

	public String delete() {
		if (user != null && user.getId() != null) {
			userManager.delete(user.getId());
		}
		return SUCCESS;
	}

	public String add() {
		if (user == null) {
			return INPUT;
		} else {
			String password = pwd();
			user.setPassword(DigestUtils.md5Hex(password));
			try {
				userManager.insert(user);
				errmsg = "添加用户成功，用户的初始密码为 " + password;
				errcode = 0;
			} catch (Exception e) {
				log.error(e, e);
				errmsg = "添加用户失败，请注意用户ID不能重复";
				errcode = 1;
			}
			return SUCCESS;
		}
	}

	private static final String pwd() {
		StringBuilder buf = new StringBuilder(6);
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			buf.append(random.nextInt(10));
		}
		return buf.toString();
	}

	public String resetPwd() {
		String password = pwd();
		user.setPassword(DigestUtils.md5Hex(password));
		userManager.changePassword(user);
		errmsg = password;
		return SUCCESS;
	}

	public String modifyInput() {
		user = userManager.select(user.getId());
		return INPUT;
	}

	public String modify() {
		userManager.update(user);
		return SUCCESS;
	}
}
