package com.keicei.agent.domain.manager;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.keicei.agent.domain.entity.UserRechargeLog;

public interface UserRechargeManager {
	/**
	 * 查询代充用户信息
	 * 
	 * @param account
	 *            :用户账号
	 * @param accountType
	 *            ：账号类型
	 * @param brandid
	 */
	JSONObject findUserInfo(String account, String accountType, String brandid);

	/**
	 * 代用户充值
	 * 
	 * @param agentId
	 * @param account
	 * @param accountType
	 * @param brandid
	 * @param sum
	 *            ：金额
	 * @return
	 */
	int userRecharge(String agentId, String uid, String account, String accountType,String brandid, String firstUse, String sum) ;

	/**
	 * 生成用户代充充值记录
	 * 
	 * @param userRechargeLog
	 * @return
	 */
	int insertUserRechargeLog(UserRechargeLog userRechargeLog);

	/**
	 * 更新充值记录状态
	 * 
	 * @param userRechargeLog
	 * @return
	 */
	int updateUserRechargeLogStatus(UserRechargeLog userRechargeLog);

	/**
	 * 查询代充充值记录_条数
	 * @param map
	 * @return
	 */
	public Map<String,Object> findUserRerchargeLogListCount(Map<String, Object> map) ;
	
	/**
	 * 查询代充充值记录
	 * 
	 * @param map
	 * @return
	 */
	List<UserRechargeLog> findUserRerchargeLogList(Map<String, Object> map, int page, int pageSize);

}
