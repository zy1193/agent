package com.keicei.agent.domain.manager;

import org.json.JSONObject;


public interface UserLoginManager {
	public JSONObject getLoginInfo(String loginType,String account,String password,String clientIp,String from,String brandid);
}
