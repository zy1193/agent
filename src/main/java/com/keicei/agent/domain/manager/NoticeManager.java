package com.keicei.agent.domain.manager;

import com.keicei.agent.domain.entity.Notice;

/**
 * @author guoqidi
 * @date 2011-12-28 下午04:36:45
 * @version V1.1
 */
public interface NoticeManager {

	Notice select(String brandid);

	int insert(Notice notice);

	int update(Notice notice);

}
