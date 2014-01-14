package com.keicei.agent.domain.rpc.impl;

import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.keicei.agent.common.GlobalConfig;
import com.keicei.agent.domain.rpc.UserRechargeService;
import com.keicei.util.DateUtil;
import com.keicei.util.HttpUtil;

@Service("userRechargeService")
public class UserRechargeServiceImpl implements UserRechargeService {
	private static final Logger log = Logger.getLogger(UserRechargeServiceImpl.class);
	@Resource
	GlobalConfig globalConfig;

	@Override
	public String userRecharge(String uid, String brandid, String orderSn, String sum) {
		String recharge_rpc_url = globalConfig.get("KC_BC_ADDR")
				+ "charge?uid=%s&order=%s&key=%s&sum=%s&brandid=%s";
		;
		// md5
		Long sum_yuan =Long.valueOf(sum)*100;
		String key = DigestUtils.md5Hex("uid=" + uid + "&sum=" + sum_yuan + "&order=" + orderSn);

		String url = String.format(recharge_rpc_url, uid, orderSn, key, sum_yuan, brandid);
		try {
			return HttpUtil.get(url);
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}
	}

	@Override
	public String findUserInfoByUid(String account, String brandid) {
		return findUserInfo(account, "kc", brandid);
	}

	@Override
	public String findUserInfoByMobile(String account, String brandid) {
		return findUserInfo(account, "mobile", brandid);
	}

	@Override
	public String findUserInfoByEmail(String account, String brandid) {
		String accountinfo_rpc_url = globalConfig.get("RPC_AMS_ADDR")
				+ "ein.act?email=%s&macip=%s&macdate=%s&macrand=%s&mac=%s&brandid=%s";

		Random random = new Random();
		String rand = String.valueOf(random.nextInt(20));
		String macdate = DateUtil.getNowDateTimeString("yyyyMMdd");
		String macrand = DateUtil.getNowDateTimeString("HHmmssSSS") + rand;
		String mac = DigestUtils.md5Hex(globalConfig.get("AMS_SERVICE_IP") + macdate + macrand
				+ globalConfig.get("AMS_SERVICE_KEY"));

		String url = String.format(accountinfo_rpc_url, account, globalConfig.get("AMS_SERVICE_IP"), macdate, macrand,
				mac, brandid);
		String rsp;
		try {
			rsp = HttpUtil.get(url);
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}
		return rsp;
	}

	private String findUserInfo(String account, String accountType, String brandid) {
		String accountinfo_rpc_url = globalConfig.get("RPC_AMS_ADDR")
				+ "info.act?accounttype=%s&account=%s&macip=%s&macdate=%s&macrand=%s&mac=%s&brandid=%s";

		Random random = new Random();
		String rand = String.valueOf(random.nextInt(20));
		String macdate = DateUtil.getNowDateTimeString("yyyyMMdd");
		String macrand = DateUtil.getNowDateTimeString("HHmmssSSS") + rand;
		String mac = DigestUtils.md5Hex(globalConfig.get("AMS_SERVICE_IP") + macdate + macrand
				+ globalConfig.get("AMS_SERVICE_KEY"));

		String url = String.format(accountinfo_rpc_url, accountType, account, globalConfig.get("AMS_SERVICE_IP"),
				macdate, macrand, mac, brandid);
		String rsp;
		try {
			rsp = HttpUtil.get(url);
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}
		return rsp;
	}

}
