package com.keicei.agent.domain.manager.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.constant.Constant;
import com.keicei.agent.domain.entity.AcctFrozenLog;
import com.keicei.agent.domain.entity.AcctTransLog;
import com.keicei.agent.domain.entity.UserRechargeLog;
import com.keicei.agent.domain.manager.AcctManager;
import com.keicei.agent.domain.manager.UserRechargeManager;
import com.keicei.agent.domain.rpc.UserRechargeService;
import com.keicei.agent.persistence.mapper.UserRechargeLogMapper;
import com.keicei.util.PaginationUtil;
import com.keicei.util.SequenceUtil;

@Service("userRechargeManager")
public class UserRechargeManagerImpl implements UserRechargeManager {

	@Resource
	private UserRechargeService userRechargeService;
	@Resource
	private AcctManager acctManager;

	@Resource
	private UserRechargeLogMapper userRechargeLogMapper;
	private static Logger log = Logger.getLogger(UserRechargeManagerImpl.class);

	@Override
	public JSONObject findUserInfo(String account, String accountType, String brandid) {
		String rsp = null;
		if ("uid".equalsIgnoreCase(accountType)) {
			rsp = userRechargeService.findUserInfoByUid(account, brandid);
		} else if ("email".equalsIgnoreCase(accountType)) {
			rsp = userRechargeService.findUserInfoByEmail(account, brandid);
		} else if ("mobile".equalsIgnoreCase(accountType)) {
			rsp = userRechargeService.findUserInfoByMobile(account, brandid);
		}
		if (rsp != null && rsp.length() > 0) {
			return analyzeStr(rsp);
		}
		return null;
	}

	@Override
	public int userRecharge(String agentId, String uid, String account, String accountType,String brandid, String firstUse, String sum) {
		if (StringUtils.isBlank(sum) || StringUtils.isBlank(brandid)|| StringUtils.isBlank(agentId) || StringUtils.isBlank(uid)|| StringUtils.isBlank(account)|| StringUtils.isBlank(accountType)|| StringUtils.isBlank(firstUse)) {
			log.error("用户代充：传入之存在空值！");
			return Constant.RC_USERRECHARGE_OTHER;
		}
		if (!sum.matches("^[0-9]*[1-9][0-9]*$")) {
			log.error("用户代充：传入金额不为正整数！");
			return Constant.RC_USERRECHARGE_OTHER;
		}

		/** 订单号 **/
		String orderSn = SequenceUtil.id();
		String at="2";//默认是手机充值
		if("uid".equalsIgnoreCase(accountType)){
			at="1";
		}else if("mobile".equalsIgnoreCase(accountType)){
			at="2";
		}else if("email".equalsIgnoreCase(accountType)){
			at="3";
		}else{
			return Constant.RC_USERRECHARGE_OTHER;
		}
		long rechargeSum=Long.valueOf(sum)*1000;
		// Step 1 : 生成用户充值记录并保存
		UserRechargeLog userRechargeLog = new UserRechargeLog();
		userRechargeLog.setAgentId(agentId);
		userRechargeLog.setOrderSn(orderSn);
		userRechargeLog.setAccount(account);
		userRechargeLog.setAccountType(at);
		userRechargeLog.setBrandId(brandid);
		userRechargeLog.setRechargeAmount(rechargeSum);
		userRechargeLog.setFirstUse(firstUse);
		userRechargeLog.setStatus(Constant.USERRECHARGE_ORDER_STATUS_PROCESSING);
		userRechargeLog.setRmk("充值UID:" + uid);
		insertUserRechargeLog(userRechargeLog);

		// Step 2 : 冻结购卡金额
		AcctFrozenLog acctFrozenLog = new AcctFrozenLog();
		acctFrozenLog.setAcctId(agentId);
		acctFrozenLog.setFrozenType(Constant.FROZEN_TYPE_USERRECHARGE);
		acctFrozenLog.setFrozenAmount(rechargeSum);
		acctFrozenLog.setFrozenSn(orderSn);
		acctFrozenLog.setBrandid(brandid);
		if (!acctManager.frozen(acctFrozenLog)) {
			userRechargeLog.setStatus(Constant.USERRECHARGE_ORDER_STATUS_FAIL);
			updateUserRechargeLogStatus(userRechargeLog);
			return Constant.RC_USERRECHARGE_BAL_LESS;
		}

		// Step 3:远程充值
		String code = userRechargeService.userRecharge(uid, brandid, orderSn, sum);
		// 远程调用不成功
		if (code == null || !"0".equals(code)) {
			try {
				// 解冻 账户
				acctManager.thaw(acctFrozenLog);
				// 设置失败
				userRechargeLog.setStatus(Constant.USERRECHARGE_ORDER_STATUS_FAIL);
				updateUserRechargeLogStatus(userRechargeLog);
			} catch (Exception e) {
				log.error(e, e);
			}
			return Constant.RC_USERRECHARGE_RPC_ERR;
		}

		StringBuilder rmk = new StringBuilder();
		rmk.append("代用户充值:用户帐号(");
		rmk.append(uid).append(")");
		rmk.append("金额(").append(sum).append(")元");

		/** 支付代充金额 **/
		AcctTransLog acctTransLog = new AcctTransLog();
		acctTransLog.setAcctId(agentId);
		acctTransLog.setPayAmount(rechargeSum);
		acctTransLog.setIncomeAmount(0);
		acctTransLog.setTransSn(orderSn);
		acctTransLog.setTransType(Constant.ACCT_TRANS_TYPE_USERRECHARGE);
		acctTransLog.setRmk(rmk.toString());
		acctTransLog.setBrandid(brandid);
		acctManager.thawAndPay(acctFrozenLog, acctTransLog);

		/** 充值成功 **/
		userRechargeLog.setStatus(Constant.USERRECHARGE_ORDER_STATUS_SUCCESS);
		updateUserRechargeLogStatus(userRechargeLog);

		return Constant.RC_USERRECHARGE_SUCCESS;
	}

	@Transactional
	@Override
	public int insertUserRechargeLog(UserRechargeLog userRechargeLog) {
		return userRechargeLogMapper.insert(userRechargeLog);
	}

	@Transactional
	@Override
	public int updateUserRechargeLogStatus(UserRechargeLog userRechargeLog) {
		return userRechargeLogMapper.updateStatus(userRechargeLog);
	}

	@Transactional(readOnly = true)
	@Override
	public List<UserRechargeLog> findUserRerchargeLogList(Map<String, Object> map, int page, int pageSize) {
		PaginationUtil.generatePageParameter(map, page, pageSize);
		return userRechargeLogMapper.list(map);
	}

	public Map<String,Object> findUserRerchargeLogListCount(Map<String, Object> map) {
		return userRechargeLogMapper.rechargecount(map);
	}

	/**
	 * 
	 * @Title: analyzeStr
	 * @Description: TODO(解析AMS返回值)
	 * @param：@param str
	 * @param：@return
	 * @return JSONObject 返回类型
	 * @throws
	 */
	private JSONObject analyzeStr(String str) {
		str = str.replace("=", ":'");
		str = "{" + str.replace("&", "',") + "'}";
		JSONObject json = null;
		try {
			json = new JSONObject(str);
		} catch (JSONException e) {
			log.error(e, e);
			return null;
		}
		return json;
	}

}
