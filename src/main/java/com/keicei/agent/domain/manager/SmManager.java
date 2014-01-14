package com.keicei.agent.domain.manager;

public interface SmManager {

	/**
	 * 发送下行短信
	 * 
	 * @param number 手机号码
	 * @param content 短信内容
	 */
	boolean mt(String number, String content);

}
