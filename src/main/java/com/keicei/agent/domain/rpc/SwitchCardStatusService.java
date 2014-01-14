package com.keicei.agent.domain.rpc;

/**
 * 调用远程接口更新充值卡状态
 * 
 * @author ZHANGYAN
 * 
 */
public interface SwitchCardStatusService {
	/**
	 * 
	 * @param passwords 充值卡的密码，多个密码用逗号分隔。最多50个充值卡密码
	 * @param status 状态 0=正常 1=冻结
	 * @return
	 */
	boolean switchCardStatus(String brandid, String passwords, String status);
}
