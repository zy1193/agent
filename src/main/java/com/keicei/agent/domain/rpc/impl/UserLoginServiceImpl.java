package com.keicei.agent.domain.rpc.impl;

import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.keicei.agent.common.GlobalConfig;
import com.keicei.agent.domain.rpc.UserLoginService;
import com.keicei.util.DateUtil;
import com.keicei.util.HttpUtil;

@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {
	private static final Logger log = Logger
			.getLogger(UserLoginServiceImpl.class);

	@Resource
	private GlobalConfig globalConfig;

	@Override
	public String getLoginInfo(String loginType, String userName,
			String password, String ip, String from, String brandid) {
		String getlogininfo_rpc_url = globalConfig.get("RPC_AMS_ADDR")
				+ "login.act?loginType=%s&account=%s&password=%s&ip=%s&from=%s&macip=%s&macdate=%s&macrand=%s&mac=%s&brandid=%s";
		Random random = new Random();
		String rand = String.valueOf(random.nextInt(20));
		String macdate = DateUtil.getNowDateTimeString("yyyyMMdd");
		String macrand = DateUtil.getNowDateTimeString("HHmmssSSS") + rand;
		String mac = DigestUtils.md5Hex(globalConfig.get("AMS_SERVICE_IP")
				+ macdate + macrand + globalConfig.get("AMS_SERVICE_KEY"));

		String url = String.format(getlogininfo_rpc_url, loginType, userName,
				DigestUtils.md5Hex(password), ip, from,
				globalConfig.get("AMS_SERVICE_IP"), macdate, macrand, mac,
				brandid);
		String rsp;
		try {
			rsp = (HttpUtil.get(url));
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}
		return rsp;
	}
}
