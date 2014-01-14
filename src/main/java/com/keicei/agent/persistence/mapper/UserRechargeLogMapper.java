package com.keicei.agent.persistence.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.keicei.agent.domain.entity.UserRechargeLog;
import com.keicei.common.BaseMapper;

@Repository("userRechargeLogMapper")
public interface UserRechargeLogMapper  extends BaseMapper<UserRechargeLog, Integer> {

	/** 修改状态 **/
	int updateStatus(UserRechargeLog userRechargeLog);
	
	Map<String,Object> rechargecount(Map<String,Object> map);
}
