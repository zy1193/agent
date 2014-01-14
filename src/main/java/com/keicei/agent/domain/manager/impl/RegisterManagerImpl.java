package com.keicei.agent.domain.manager.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.keicei.agent.domain.manager.RegisterManager;
import com.keicei.agent.domain.rpc.RegisterService;

/**
 * 
 * @ClassName: registerManager
 * @Description: TODO(用户手机注册，调用AMS系统)
 * @author lifh
 * @date 2012-4-26
 * 
 * @最后修改人： lifh
 * @最后修改时间： 2012-4-26
 * @版本： V1.0
 * 
 */
@Service("registerManager")
public class RegisterManagerImpl implements RegisterManager {
	@Resource
	private RegisterService registerService;

	/**
	 * 手机号码注册
	 */
	@Override
	public String autoRegisterForMobile(String loginIp, String mobileNo,
			String invitedby, String invitedflag, String from, String location,String brandid) {
		return registerService.autoRegisterForMobile(loginIp, mobileNo, invitedby,
				invitedflag, from, location, brandid);
	}
	
	/**
	 * 手机号码注册
	 */
	@Override
	public String registerForMobile(String loginIp, String mobileNo,
			String invitedby, String invitedflag, String from, String location,String brandid) {
		return registerService.registerForMobile(loginIp, mobileNo, invitedby,
				invitedflag, from, location, brandid);
	}
	
}
