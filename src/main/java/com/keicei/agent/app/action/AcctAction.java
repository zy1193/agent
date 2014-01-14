package com.keicei.agent.app.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.domain.entity.Acct;
import com.keicei.agent.domain.entity.Agent;
import com.keicei.agent.domain.manager.AcctManager;
import com.keicei.util.PaginationUtil;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Controller
public class AcctAction extends ActionSupport {

	private static final long serialVersionUID = -3043612467663661965L;

	@Resource
	private AcctManager acctManager;

	private Acct acct;
	private List<Acct> accts;
	private int page, totalRecordCount;
	private String status;
	private long sumaprice = 0;
	private long sumfprice = 0;
	private int scount = 0;

	public int getScount() {
		return scount;
	}

	public void setScount(int scount) {
		this.scount = scount;
	}

	public long getSumaprice() {
		return sumaprice;
	}

	public void setSumaprice(long sumaprice) {
		this.sumaprice = sumaprice;
	}

	public long getSumfprice() {
		return sumfprice;
	}

	public void setSumfprice(long sumfprice) {
		this.sumfprice = sumfprice;
	}

	public void setSumfprice(int sumfprice) {
		this.sumfprice = sumfprice;
	}

	public void setAccts(List<Acct> accts) {
		this.accts = accts;
	}

	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Acct getAcct() {
		return acct;
	}

	public void setAcct(Acct acct) {
		this.acct = acct;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<Acct> getAccts() {
		return accts;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}

	/** 查询当前登录用户的余额 **/
	public String mybal() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		Agent agent = (Agent) session.getAttribute(Constant.AGENT_SESSION_NAME);
		acct = acctManager.select(agent.getId());
		return SUCCESS;
	}

	public String list() {

		if (page < 1)
			page = 1;

		Map<String, Object> parameters = new HashMap<String, Object>();
		if (acct != null && acct.getId() != null) {
			parameters.put("id", "%" + acct.getId() + "%");
			totalRecordCount = acctManager.count(parameters);
		} else {
			totalRecordCount = 0;
		}
		if (totalRecordCount > 0)
			accts = acctManager
					.list(parameters, page, PaginationUtil.PAGE_SIZE);

		return SUCCESS;
	}

	public String frozen() {
		acctManager.update(acct);
		return SUCCESS;
	}

	/** 余额统计列表 **/
	public String listbalstat() {
		if (page < 1)
			page = 1;
		Map<String, Object> map = new HashMap<String, Object>();
		if (status != null && !"999".equals(status)) {
			map.put("status", status);
		}
		totalRecordCount = acctManager.countbal(map);
		accts = acctManager.listbalstat(map, page, PaginationUtil.PAGE_SIZE);
		for (Acct ac : accts) {
			sumaprice += ac.getsAvailableBalance();
			sumfprice += ac.getsFrozenBalance();
			scount += ac.getCount();
		}
		return SUCCESS;
	}

}
