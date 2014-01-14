package com.keicei.agent.domain.rpc;

/**
 * 短信服务
 * 
 * @author ZHANGYAN
 * 
 */
public interface SmService {

	/**
	 * 发送下行短信
	 * 
	 * @param number 手机号码
	 * @param content 短信内容
	 */
	boolean mt(String number, String content);

}
