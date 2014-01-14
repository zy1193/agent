package com.keicei.agent.domain.rpc;

public interface UserLoginService {
	public String getLoginInfo(String loginType, String userName,
			String password, String ip, String from,String brandid);
}
