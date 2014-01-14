package com.keicei.agent.domain.manager.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.domain.entity.OperateLog;
import com.keicei.agent.domain.manager.OperateLogManager;
import com.keicei.agent.persistence.mapper.OperateLogMapper;

@Service("operateLogManager")
public class OperateLogManagerImpl implements OperateLogManager {
	private static final ExecutorService threadpool = Executors
			.newCachedThreadPool();
	@Resource
	private OperateLogMapper operateLogMapper;

	@SuppressWarnings("unused")
	@PreDestroy
	private void release() {
		threadpool.shutdown();
	}

	@Transactional
	private int log(OperateLog operateLog) {
		return operateLogMapper.insert(operateLog);
	}

	@Override
	public void asyncLog(OperateLog operateLog) {
		threadpool.execute(new LogRunnable(operateLog));
	}

	class LogRunnable implements Runnable {
		private OperateLog operateLog;

		LogRunnable(OperateLog operateLog) {
			this.operateLog = operateLog;
		}

		@Override
		public void run() {
			log(operateLog);
		}
	}
}
