package com.akanemurakawa.kaguya.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * MD5 encryption
 */
@Slf4j
public class MD5Utils {

    /**
     * MD5加密
     */
	public final static String getMD5(String s){
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		
		try{
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for(int i = 0; i < j; i++){
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		}catch (Exception e){
		    log.error("MD5加密失败，error: {}", e);
			return null;
		}
	}
}
