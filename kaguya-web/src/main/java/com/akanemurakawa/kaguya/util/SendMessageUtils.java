package com.akanemurakawa.kaguya.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.Properties;

import lombok.Data;
import lombok.Getter;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Value;

/**
 * 1.发送短信-中国网建SMS短信通
 * 2.注意：需要先配置sms-config.properties文件
 */
public class SendMessageUtils {

    /**
     * Uid为 http://www.webchinese.com.cn 网站注册时的用户名
     */
    @Value("{sms.uid}")
	private static String uid ;

    /**
     * Key为注册时填写的接口秘钥（可到用户平台修改接口秘钥）
     */
    @Value("{sms.key}")
	private static String key = null;
	
	private int status;
	
	private int result;

	@Getter
	private String statusCode;
	
	private PostMethod post;
	
	private static boolean isSuccess = true;
	
	private static HttpClient client = null;

	/**
	 * load config-file,构造函数的时候进行初始化判断
	 * @throws MalformedURLException
	 */
	public SendMessageUtils() throws MalformedURLException {
		if(isSuccess == false) {
			statusCode = "sms-config.properties文件加载失败,配置出现错误!";
		}
	}
	
	/**
	 * send发送短信
	 * @param telePhoneNum
	 * @param sendContent
	 * @return
	 */
	public String SendSMSPost(String telePhoneNum, String sendContent) {
		/*
		 * FilterDispatch，过滤处理
		 */
		if("".equals(uid) || "".equals(key) || uid == null ||key == null ) {
			statusCode = "sms-config.properties文件加载失败,配置出现错误!";
			return statusCode;
		}
		if(telePhoneNum == null || sendContent == null) {
			statusCode = "手机号和内容不能为空!";
			return statusCode;
		}
		
		try {
			client = new HttpClient();
			post = new PostMethod("http://sms.webchinese.cn/web_api/");
			// 在头文件中设置转码
			post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");
			/*
			 * configuration 
			 */
			NameValuePair[] data = {
					new NameValuePair("Uid", uid),
					new NameValuePair("Key", key),
					new NameValuePair("smsMob", telePhoneNum),
					new NameValuePair("smsText", sendContent)};
			// 设置post请求的携带数据到请求体中
			post.setRequestBody(data); 
			// execute 
			client.executeMethod(post);
		} catch (HttpException e) {
			statusCode = "出现未知错误,发送失败!";
		} catch (IOException e) {
			statusCode = "出现未知错误,发送失败!";
		}
		/*
		 * status
		 */
		status = post.getStatusCode();
		if(status == 200) {
			statusCode = "result:"+result+"  发送成功!";
		}
		try {
			result = Integer.parseInt(new String(post.getResponseBodyAsString().getBytes("gbk")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		/*
		 * return result
		 */
		if(result > 0) {
			statusCode = "成功发送"+result+"条信息!";
		}else if(result == -1) {
			statusCode = "返回:"+result+"  没有该用户账户!";
		}else if(result == -2) {
			statusCode = "返回:"+result+"  接口密钥不正确!";
		}else if(result == -3) {
			statusCode = "返回:"+result+"  短信数量不足!";
		}else if(result == -4) {
			statusCode = "返回:"+result+"  手机号格式不正确!";
		}else if(result == -6) {
			statusCode = "返回:"+result+"  IP限制!";
		}else if(result == -11) {
			statusCode = "返回:"+result+"  该用户被禁用!";
		}else if(result == -14) {
			statusCode = "返回:"+result+"  短信内容出现非法字符!";
		}else if(result == -21) {
			statusCode = "返回:"+result+"  MD5接口密钥加密不正确!";
		}else if(result == -41) {
			statusCode = "返回:"+result+"  手机号码为空!";
		}else if(result == -42) {
			statusCode = "返回:"+result+"  短信内容为空!";
		}else if(result == -51) {
			statusCode = "返回:"+result+"  短信签名格式不正确!";
		}
		// Realse Resource
		post.releaseConnection();
		// 返回发送结果状态
		return statusCode;
	}
}
