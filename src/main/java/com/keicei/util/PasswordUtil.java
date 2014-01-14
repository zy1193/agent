package com.keicei.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;


/**
 * 密码工具，对消息报文\temp_register\AuthInfo里的密码进行加密和解密
 * 
 * @author ZHANGYAN
 * 
 */
public class PasswordUtil {

	public static final String RC4_AMS_KEY = "1bb762f7ce24ceee";

	public static final byte[] RC4_AMS_KEY_BYTE = RC4_AMS_KEY.getBytes();

	/**
	 * 报文密码加密(rc4)
	 * 
	 * @param src
	 * @return
	 */
	public static final String rc4Encrypt(String src) {
		byte[] dest;
		try {
			dest = Rc4.encrypt(src.getBytes(), RC4_AMS_KEY_BYTE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new String(Hex.encodeHex(dest));
	}

	/**
	 * 报文密码解密(rc4)
	 * 
	 * @param src
	 * @return
	 */
	public static final String rc4Decrypt(String src) {
		byte[] src1;
		try {
			src1 = Hex.decodeHex(src.toCharArray());
		} catch (DecoderException e) {
			throw new RuntimeException(e);
		}
		byte[] dest;
		try {
			dest = Rc4.decrypt(src1, RC4_AMS_KEY_BYTE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new String(dest);
	}
}
