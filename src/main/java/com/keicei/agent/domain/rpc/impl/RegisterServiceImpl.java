package com.keicei.agent.domain.rpc.impl;

import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.keicei.agent.common.GlobalConfig;
import com.keicei.agent.domain.rpc.RegisterService;
import com.keicei.util.DateUtil;
import com.keicei.util.HttpUtil;

/**
 * 
 * @ClassName: RegisterImpl
 * @Description: TODO(手机号码自动注册，调用AMS系统)
 * @author lifh
 * @date 2012-4-26
 * 
 * @最后修改人： lifh
 * @最后修改时间： 2012-4-26
 * @版本： V1.0
 * 
 */
@Service("registerService")
public class RegisterServiceImpl implements RegisterService {
	private static final Logger log=Logger.getLogger(RegisterServiceImpl.class);
	@Resource
	private GlobalConfig globalConfig;
	/**
	 * 手机号码自动注册
	 */
	@Override
	public String autoRegisterForMobile(String loginIp, String mobileNo,
			String invitedby, String invitedflag, String from,String location,String brandid) {
		String registerformobile_rpc_url = globalConfig.get("RPC_AMS_ADDR")
				+ "automobilereg.act?ip=%s&number=%s&invitedby=%s&invitedflag=%s&from=%s&location=%s&macip=%s&macdate=%s&macrand=%s&mac=%s&brandid=%s";

		Random random = new Random();
		String rand = String.valueOf(random.nextInt(20));
		String macdate = DateUtil.getNowDateTimeString("yyyyMMdd");
		String macrand = DateUtil.getNowDateTimeString("HHmmssSSS") + rand;
		String mac = DigestUtils.md5Hex(globalConfig.get("AMS_SERVICE_IP") + macdate
				+ macrand + globalConfig.get("AMS_SERVICE_KEY"));

		String url = String.format(registerformobile_rpc_url, loginIp,
				mobileNo, invitedby, invitedflag, from,location,
				globalConfig.get("AMS_SERVICE_IP"), macdate, macrand, mac,brandid);
		String rsp;
		try {
			rsp = HttpUtil.get(url);
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}
		return rsp;
	}
	
	
	/**
	 * 手机号码注册
	 */
	@Override
	public String registerForMobile(String loginIp, String mobileNo,
			String invitedby, String invitedflag, String from,String location,String brandid) {
		String registerformobile_rpc_url = globalConfig.get("RPC_AMS_ADDR")
				+ "mobilereg.act?ip=%s&number=%s&invitedby=%s&invitedflag=%s&from=%s&location=%s&macip=%s&macdate=%s&macrand=%s&mac=%s&brandid=%s";

		Random random = new Random();
		String rand = String.valueOf(random.nextInt(20));
		String macdate = DateUtil.getNowDateTimeString("yyyyMMdd");
		String macrand = DateUtil.getNowDateTimeString("HHmmssSSS") + rand;
		String mac = DigestUtils.md5Hex(globalConfig.get("AMS_SERVICE_IP") + macdate
				+ macrand + globalConfig.get("AMS_SERVICE_KEY"));

		String url = String.format(registerformobile_rpc_url, loginIp,
				mobileNo, invitedby, invitedflag, from,location,
				globalConfig.get("AMS_SERVICE_IP"), macdate, macrand, mac,brandid);
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
