package com.keicei.agent.domain.rpc.impl;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.keicei.agent.common.GlobalConfig;
import com.keicei.agent.domain.rpc.SwitchCardStatusService;
import com.keicei.util.HttpUtil;
import com.keicei.util.SequenceUtil;

@Service("switchCardStatusService")
public class SwitchCardStatusServiceImpl implements SwitchCardStatusService {

	/** 冻结卡 **/
	private static final String CONGEAL = "?cardpwd=%s&sign=%s&rand=%s&brandid=%s";
	/** 解冻卡 **/
	private static final String EFFECTIVE = "?cardpwd=%s&sign=%s&rand=%s&brandid=%s";
	private static final Logger log = Logger
			.getLogger(SwitchCardStatusServiceImpl.class);

	@Resource
	private GlobalConfig globalConfig;

	@Override
	public boolean switchCardStatus(String brandid, String passwords,
			String status) {
		String rand = SequenceUtil.id();
		String sign = DigestUtils.md5Hex("rand=" + rand + "key="
				+ globalConfig.get("statusKey"));
		String url;
		if ("0".equals(status)) {
			url = globalConfig.get("effectiveUrl") + EFFECTIVE;
		} else if ("1".equals(status)) {
			url = globalConfig.get("congealUrl") + CONGEAL;
		} else {
			throw new RuntimeException("错误的参数");
		}

		url = String.format(url, passwords, sign, rand, brandid);
		String rsp;
		try {
			rsp = HttpUtil.get(url);
		} catch (Exception e) {
			log.error(e, e);
			return false;
		}

		JSONObject json;
		try {
			json = new JSONObject(rsp);
			return json.getInt("result") == 0;
		} catch (Exception e) {
			log.error(e, e);
			return false;
		}
	}

}
