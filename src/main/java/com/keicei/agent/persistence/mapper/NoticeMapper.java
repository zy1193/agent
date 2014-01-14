package com.keicei.agent.persistence.mapper;

import com.keicei.agent.domain.entity.Notice;

/**
 * @author guoqidi
 * @date 2011-12-28 下午04:35:57
 * @version V1.1
 */
public interface NoticeMapper {
	int insert(Notice notice);

	int update(Notice notice);

	Notice select(String brandid);
}
