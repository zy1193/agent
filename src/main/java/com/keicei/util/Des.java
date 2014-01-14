package com.keicei.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Des {

	private static final String ALGORITHM = "Des"; // 3DES算法
	private static final String PADDING = "Des/ECB/Nopadding"; // 补位方式

	/**
	 * DES加密
	 * 
	 * @param src
	 *            明文
	 * @param key
	 *            8字节的密钥
	 * @return 密文
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] src, byte[] key) {
		SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
		try {
			Cipher cipher = Cipher.getInstance(PADDING);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipher.doFinal(src);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * DES解密
	 * 
	 * @param src
	 *            密文
	 * @param key
	 *            8字节的密钥
	 * @return 明文
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] src, byte[] key) {
		SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);
		try {
			Cipher cipher = Cipher.getInstance(PADDING);
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			return cipher.doFinal(src);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		} catch (IllegalBlockSizeException e) {
			throw new RuntimeException(e);
		} catch (BadPaddingException e) {
			throw new RuntimeException(e);
		}

	}
}
