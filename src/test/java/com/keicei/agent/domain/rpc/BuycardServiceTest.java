package com.keicei.agent.domain.rpc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.keicei.agent.domain.entity.Card;
import com.keicei.util.HttpUtil;
import com.keicei.util.SequenceUtil;

@Ignore
@ContextConfiguration(locations = { "classpath:spring-jdbc.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class BuycardServiceTest {

	
	String brandid="kc";
	
	@Resource
	private GetcardService buycardService;

	@Test
	public void testBuycard() {
		int count = 1;
		List<Card> cards = buycardService.buycard(brandid,SequenceUtil.id(), "zhyan",
				"20010", count);
		Assert.assertNotNull(cards);
		Assert.assertEquals(count, cards.size());
	}

	@Test
	public void testCheckcard() {
		String pwd = "828459499582";
		String url = "http://127.0.0.1/kc_card/check_card.php";
		Map<String, String> map = new HashMap<String, String>();
		map.put("cmd", "back_submit");
		map.put("orderid", SequenceUtil.id());
		map.put("cardpwd", pwd);
		map.put("subuser", "80997487");
		map.put("returnurl", "http://127.0.0.1/show.php");
		map.put("desc", "");
		map.put("sign", sign(map));
		
		try {
			HttpUtil.get(url, map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private String sign(Map<String, String> map) {
		StringBuilder sign = new StringBuilder();
		sign.append("orderid=").append(map.get("orderid"));
		sign.append("cardpwd=").append(map.get("cardpwd"));
		sign.append("key=cardie28sdl298safdk239wadlsa3");
		String mysign = DigestUtils.md5Hex(sign.toString());
		return mysign.toUpperCase();
	}
}
