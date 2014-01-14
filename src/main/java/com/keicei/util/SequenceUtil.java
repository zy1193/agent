package com.keicei.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 序列工具
 * 
 * @author ZHANGYAN
 * 
 */
public class SequenceUtil {

	private static int seq = 0;
	private static final Lock lock = new ReentrantLock();

	/** 生成一个16位的唯一id值 **/
	public static final String id() {
		StringBuilder id = new StringBuilder(16);
		lock.lock();
		try {
			id.append(System.currentTimeMillis());
			seq++;
			if (seq > 999)
				seq = 0;
			if (seq > 99) {
				id.append(seq);
			} else if (seq > 9) {
				id.append('0').append(seq);
			} else {
				id.append("00").append(seq);
			}
		} finally {
			lock.unlock();
		}
		return id.toString();
	}
}
