package com.keicei.agent.domain.entity;

import org.apache.commons.codec.binary.Hex;
import org.junit.Ignore;
import org.junit.Test;

import com.keicei.util.Des;

@Ignore
public class CardTest {

	@Test
	public void test() {
		Card card = new Card();
		card.setPassword("123456789012");
		System.out.println(card.getDespwd());

		card = new Card();
		card.setDespwd("b86d92ebeeec07f0093c3c059a695235");
		System.out.println(card.getPassword());
	}

	@Test
	public void testdes() {
		String src = "1234567890120000";
		byte[] key = { 1, 2, 3, 4, 5, 6, 7, 8 };
		byte[] dest = Des.encrypt(src.getBytes(), key);
		System.out.println(Hex.encodeHexString(dest));
		byte[] src1 = Des.decrypt(dest, key);
		String src2 = new String(src1);
		System.out.println(src2);

	}

}
