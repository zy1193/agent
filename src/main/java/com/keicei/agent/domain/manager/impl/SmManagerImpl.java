package com.keicei.agent.domain.manager.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.keicei.agent.domain.manager.SmManager;
import com.keicei.agent.domain.rpc.SmService;


@Service("smManager")
public class SmManagerImpl implements SmManager {

	@Resource
	private SmService smService;

	@Override
	public boolean mt(String number, String content) {

		return smService.mt(number, content);

	}

}
