package com.keicei.agent.common;

import static com.keicei.util.ServletUtil.contains;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.domain.entity.Agent;

/**
 * 权限过滤
 * 
 * @author zhyan
 */
public class AgentAuthenticatFilter implements Filter {

	/** 应用程序名字 **/
	private static final int PREFIX_LEN = "/agent".length();
	/** 不需要登录即可访问的地址 **/
	private static final List<String> FREE_URL = new ArrayList<String>(8);
	private static final List<String> FROZEN_URL = new ArrayList<String>(6);
	static {
		FREE_URL.add("register.action");
		FREE_URL.add("login.action");
		FREE_URL.add("logout.action");
		FREE_URL.add("tabsFrame.action");
		FREE_URL.add("logo.action");
		FREE_URL.add("index.action");
		FREE_URL.add("menu.action");
		FREE_URL.add("content.action");

		FROZEN_URL.add("provide.action");
		FROZEN_URL.add("buycard.action");
		FROZEN_URL.add("cardstatus.action");
		FROZEN_URL.add("cardlist.action");
		FROZEN_URL.add("accredited.action");
		FROZEN_URL.add("changestatus.action");
		FROZEN_URL.add("userrecharge.action");
		FROZEN_URL.add("userrechargelist.action");

	}
	/** 会话过期或未登录 **/
	private static RequestDispatcher expire, frozen;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ServletContext ctx = filterConfig.getServletContext();
		expire = ctx.getRequestDispatcher("/expire.html");
		frozen = ctx.getRequestDispatcher("/frozen.html");
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
		Agent agent = (Agent) req.getSession().getAttribute(
				Constant.AGENT_SESSION_NAME);
		if (agent == null) {
			expire.forward(request, response);
			return;
		}

		/** 冻结帐号限制 **/
		String acctStatus = (String) req.getSession().getAttribute(
				Constant.ACCT_STATUS);
		if (("1".equals(agent.getStatus()) || !"0".equals(acctStatus))
				&& contains(FROZEN_URL, url)) {
			frozen.forward(request, response);
			return;
		} else {
			chain.doFilter(request, response);
			return;
		}
	}

	@Override
	public void destroy() {
		expire = null;
		frozen = null;
		FREE_URL.clear();
		FROZEN_URL.clear();
	}
}
