package com.akanemurakawa.kaguya.util;

/**
 * Html转为纯文本
 */
public class HTMLToTextUtils {

	public static String getSimpleText(String html){
		// 过滤html标签，转换为纯文本格式
		String regexHtml = "<[^>]+>";
		return html.replaceAll(regexHtml, "");
	}
}
