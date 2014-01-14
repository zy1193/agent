package com.keicei.agent.common;

import static com.keicei.util.ServletUtil.contains;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.domain.entity.User;

/**
 * 权限过滤
 * 
 * @author zhyan
 */
public class UserAuthenticatFilter implements Filter {

	/** 应用程序名字 **/
	private static final String WEBAPP_NAME = "agent";
	private static final int PREFIX_LEN = WEBAPP_NAME.length() + 1;
	/** 不需要登录即可访问的地址 **/
	private static final List<String> FREE_URL = new ArrayList<String>(7);
	/** 管理员才能访问的地址 **/
	private static final List<String> ADMIN_URL = new ArrayList<String>(7);
	/** 监察员可访问的地址 **/
	private static final List<String> L2_URL = new ArrayList<String>(7);
	static {
		FREE_URL.add("login.action");
		FREE_URL.add("logout.action");
		FREE_URL.add("tabsFrame.action");
		FREE_URL.add("logo.action");
		FREE_URL.add("index.action");
		FREE_URL.add("menu.action");
		FREE_URL.add("content.action");
		
		
		
		
		ADMIN_URL.add("provide.action");
		ADMIN_URL.add("user.action");
		ADMIN_URL.add("deleteuser.action");
		ADMIN_URL.add("modifyuser.action");
		ADMIN_URL.add("modifyuserinput.action");
		ADMIN_URL.add("resetpwd.action");
		ADMIN_URL.add("adduser.action");
		

		
		L2_URL.add("audit.action");
		L2_URL.add("frozenlist.action");
		L2_URL.add("frozen.action");
		L2_URL.add("frozenacctlist.action");
		L2_URL.add("frozenacct.action");
		L2_URL.add("resetagentpwd.action");
		L2_URL.add("deleteagent.action");
		
		L2_URL.add("querycardlist.action");
		L2_URL.add("cardinfoaop.action");
		L2_URL.add("cardfrozen.action");
		L2_URL.add("queryserialcardlist.action");
		L2_URL.add("serialcardfrozen.action");
	}
	/** 会话过期或未登录 **/
	private static RequestDispatcher expire, deny;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		expire = filterConfig.getServletContext().getRequestDispatcher(
				"/expire.html");
		deny = filterConfig.getServletContext().getRequestDispatcher(
				"/deny.html");
	}

	/**
	 * 登录验证
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse rsp = (HttpServletResponse) response;
		rsp.setHeader("Pragma", "No-cache");
		rsp.setHeader("Cache-Control", "no-cache");
		rsp.setHeader("Cache-Control", "no-store");
		rsp.setDateHeader("Expires", 0);

		HttpServletRequest req = (HttpServletRequest) request;

		String url = req.getRequestURI().substring(PREFIX_LEN);

		if (contains(FREE_URL, url)) {
			chain.doFilter(request, response);
			return;
		}
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constant.USER_SESSION_NAME);
		if (user == null) {
			expire.forward(request, response);
			return;
		}

		String role = user.getRole();
		if ("2".equals(role)) {
			if (contains(ADMIN_URL,url)) {
				deny.forward(request, response);
				return;
			}
		} else if ("1".equals(role)) {
			if (contains(ADMIN_URL,url) || contains(L2_URL,url)) {
				deny.forward(request, response);
				return;
			}
		}
		chain.doFilter(request, response);
		return;
	}

	@Override
	public void destroy() {
		expire = null;
		deny = null;
		FREE_URL.clear();
		ADMIN_URL.clear();
		L2_URL.clear();
	}
}
