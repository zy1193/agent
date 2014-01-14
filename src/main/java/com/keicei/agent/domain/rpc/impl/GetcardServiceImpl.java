package com.keicei.agent.domain.rpc.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import com.keicei.agent.common.GlobalConfig;
import com.keicei.agent.domain.entity.Card;
import com.keicei.agent.domain.rpc.GetcardService;
import com.keicei.util.HttpUtil;

@Service("getcardService")
public class GetcardServiceImpl implements GetcardService {

	private static final String BUYCARD_RPC_URL = "?sn=%s&goodsid=%s&mount=%d&agent=%s&sign=%s&brandid=%s";
	private static final Logger log = Logger
			.getLogger(GetcardServiceImpl.class);

	@Resource
	private GlobalConfig globalConfig;

	@Override
	public List<Card> buycard(String brandid, String orderSn, String agentId,
			String productId, int count) {
		StringBuilder signSrc = new StringBuilder();
		signSrc.append(agentId).append(orderSn)
				.append(globalConfig.get("getcardKey"));
		String url = String.format(globalConfig.get("getcardUrl")
				+ BUYCARD_RPC_URL, orderSn, productId, count, agentId,
				DigestUtils.md5Hex(signSrc.toString()), brandid);
		String rsp;
		try {
			rsp = HttpUtil.get(url);
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}

		List<Card> cards = null;
		try {
			cards = parse(rsp);
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}
		return cards;
	}

	private List<Card> parse(String xml) throws DocumentException {
		Document document = DocumentHelper.parseText(xml);
		Element root = document.getRootElement();
		Element result = root.element("result");
		if (result.getText().equals("0")) {
			List<Card> cards = new ArrayList<Card>();
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			Iterator<Element> goods = root.elementIterator("goods");
			Element e;
			Card card;
			while (goods.hasNext()) {
				e = goods.next();
				card = new Card();
				card.setProductId(e.elementText("goodsid"));
				card.setNo(e.elementText("cardno"));
				card.setPassword(e.elementText("cardpassword"));
				try {
					card.setExpireDate(df.parse(e.elementText("sactiveenddate")));
				} catch (Exception exception) {
					log.error(exception, exception);
				}
				cards.add(card);
			}
			return cards;
		} else {
			return null;
		}
	}

}
