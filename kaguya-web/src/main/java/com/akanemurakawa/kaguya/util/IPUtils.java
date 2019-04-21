package com.akanemurakawa.kaguya.util;

import javax.servlet.http.HttpServletRequest;

/**
 * ip获取，获得用户登录的真实ip地址
 */
public class IPUtils {

	public static String getIpAddr(HttpServletRequest request) { 
		String ip = request.getHeader("x-forwarded-for"); 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("Proxy-Client-IP"); 
		} 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getHeader("WL-Proxy-Client-IP"); 
		} 
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
			ip = request.getRemoteAddr(); 
		} 
		return ip; 
	} 
}
