package com.keicei.agent.domain.manager;

import com.keicei.agent.domain.entity.OperateLog;

public interface OperateLogManager {

	void asyncLog(OperateLog operateLog);

}
