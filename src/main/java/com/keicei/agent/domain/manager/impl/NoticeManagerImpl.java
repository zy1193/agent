package com.keicei.agent.domain.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keicei.agent.domain.entity.Notice;
import com.keicei.agent.domain.manager.NoticeManager;
import com.keicei.agent.persistence.mapper.NoticeMapper;

/**
 * @author guoqidi
 * @date 2011-12-28 下午04:38:30
 * @version V1.1
 */
@Service("noticeManager")
public class NoticeManagerImpl implements NoticeManager {

	@Resource
	private NoticeMapper noticeMapper;

	@Override
	@Transactional
	public int insert(Notice entity) {
		return noticeMapper.insert(entity);
	}

	@Override
	@Transactional
	public int update(Notice entity) {
		return noticeMapper.update(entity);
	}

	@Override
	public Notice select(String brandid) {
		return noticeMapper.select(brandid);
	}

}
