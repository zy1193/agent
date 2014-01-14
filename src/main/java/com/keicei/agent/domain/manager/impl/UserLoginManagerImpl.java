package com.keicei.agent.domain.manager.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.keicei.agent.domain.manager.UserLoginManager;
import com.keicei.agent.domain.rpc.UserLoginService;

/**
 * 
 * @ClassName: UserLoginManager
 * @Description: (登录)
 * @author lifh
 * @date 2012-04-18
 * 
 * @最后修改人： lifh
 * @最后修改时间：2012-04-18
 * @版本： V1.0
 * 
 */
@Service("userLoginManager")
public class UserLoginManagerImpl implements UserLoginManager {

	private static final Logger log = Logger
			.getLogger(UserLoginManagerImpl.class);

	@Resource
	private UserLoginService userLoginService;

	@Override
	public JSONObject getLoginInfo(String loginType, String account,
			String password, String clientIp, String from, String brandid) {
		JSONObject json = new JSONObject();
		String rsp = "";
		try {
			rsp = userLoginService.getLoginInfo(loginType, account, password,
					clientIp, from, brandid);
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}
		json = analyzeStr(rsp);
		return json;
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
	public JSONObject analyzeStr(String str) {
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
