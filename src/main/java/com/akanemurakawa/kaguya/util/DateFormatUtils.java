package com.akanemurakawa.kaguya.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式
 */
public class DateFormatUtils {
	
	/**
	 * 返回当前时间，但是注意的是SimpleDateFormat类不是线程安全的
	 */
	public final static String getNowTime(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
}
