package com.keicei.agent.domain.repository;

/**
 * 缓存用户的品牌信息
 * 用户的品牌在刚注册时是*,审核通过以后就不会再变了,适合通过缓存访问
 * 
 * @author ZHANGYAN
 * 
 */
public interface BrandRepository {

	/** 返回某个代理商的品牌,null表示尚未审核 **/
	String brand(String agentId);

}
