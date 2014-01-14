package com.keicei.agent.domain.rpc;

public interface UserRechargeService {
	/**
	 * 查询代充用户信息：UID
	 * 
	 * @param account
	 *            :用户账号
	 * @param accountType
	 *            ：账号类型
	 * @param brandid
	 */
	String findUserInfoByUid(String account, String brandid);

	/**
	 * 查询代充用户信息:邮件
	 * 
	 * @param account
	 *            :用户账号
	 * @param accountType
	 *            ：账号类型
	 * @param brandid
	 */
	String findUserInfoByEmail(String account, String brandid);

	/**
	 * 查询代充用户信息:手机
	 * 
	 * @param account
	 *            :用户账号
	 * @param accountType
	 *            ：账号类型
	 * @param brandid
	 */
	String findUserInfoByMobile(String account, String brandid);

	/**
	 * 代用户充值
	 * 
	 * @param uid
	 * @param brandid
	 * @param sum
	 *            ：金额
	 * @return
	 */
	String userRecharge(String uid, String brandid, String orderSn,String sum);

}
