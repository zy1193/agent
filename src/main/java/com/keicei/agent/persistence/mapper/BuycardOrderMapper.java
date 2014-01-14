package com.keicei.agent.persistence.mapper;

import com.keicei.agent.domain.entity.BuycardOrder;
import com.keicei.common.BaseMapper;

public interface BuycardOrderMapper extends BaseMapper<BuycardOrder, Integer> {
	/** 修改状态 **/
	int updateStatus(BuycardOrder buycardOrder);
}
