package com.keicei.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * 得到当前日期/时间字符串
	 * 
	 * @return 返回日期/时间字符串
	 */
	public static String getNowDateTimeString(String type) {
		Date date = new Date();
		SimpleDateFormat formattxt = new SimpleDateFormat(type);
		return formattxt.format(date);
	}
}
