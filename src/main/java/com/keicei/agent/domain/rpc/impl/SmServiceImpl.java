package com.keicei.agent.domain.rpc.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.keicei.agent.common.GlobalConfig;
import com.keicei.agent.domain.rpc.SmService;
import com.keicei.util.HttpUtil;
/**
 * 调用短信接口发送短信
 * 
 * @author lifh
 * 
 */
@Service("smService")
public class SmServiceImpl implements SmService {

	private static final Logger log = Logger.getLogger(SmServiceImpl.class);
	@Resource
	private GlobalConfig globalConfig;

	@Override
	public boolean mt(String number, String content) {
		String MT_URL =globalConfig.get("smsurl");
		String username = globalConfig.get("smsusername");
		String password = globalConfig.get("smspassword");
		String subuser = globalConfig.get("smssubuser");
		String url;
		try {
			url = String.format(MT_URL, username, password, subuser, number,
					URLEncoder.encode(content, "GBK"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		String code;
		try {
			code = HttpUtil.get(url);
		} catch (Exception e) {
			code = "9999";
			log.error(e, e);
		}
		if (code.equals("1000")) {
			return true;
		} else {
			log.info("发送短信失败,number=" + number + ",content=" + content
					+ ",code=" + code);
			return false;
		}
	}
}
