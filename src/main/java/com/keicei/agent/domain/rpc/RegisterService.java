package com.keicei.agent.domain.rpc;

public interface RegisterService {
	/**
	 * 手机号码自动注册
	 */
	public String autoRegisterForMobile(String loginIp, String mobileNo,
			String invitedby, String invitedflag, String from,String location,String brandid) ;
	/**
	 * 手机号码注册
	 */
	public String registerForMobile(String loginIp, String mobileNo,
			String invitedby, String invitedflag, String from,String location,String brandid);
}
