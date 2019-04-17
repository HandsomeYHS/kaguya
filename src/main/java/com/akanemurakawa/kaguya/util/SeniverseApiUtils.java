package com.akanemurakawa.kaguya.util;

import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 1.心知天气API，生成 API URL
 * 2.使用Java（no other dependences）生成签名，直接运行文件将会输出最终应该调用的 URL
 * 3.之后将该 URL 传递至前端通过 JSONP 进行调用，或在后端 server 内调用
 * 4.URL 参数等参见官网API。
 * 5.官方API地址：https://www.seniverse.com/doc
 * 6.注意：首先应该在下面修改你的TIANQI_API_SECRET_KEY和TIANQI_API_USER_ID
 */
@Slf4j
public class SeniverseApiUtils {

	private String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/weather/daily.json";

    private String TIANQI_API_SECRET_KEY = "xxxxxxxxx"; // 我的API密钥

    private String TIANQI_API_USER_ID = "xxxxxxxxx"; // 我的用户ID

    /**
     * Generate HmacSHA1 signature with given data string and key
     */
    private String generateSignature(String data, String key) throws SignatureException {
        String result;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            result = new sun.misc.BASE64Encoder().encode(rawHmac);
        }
        catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

    /**
     * Generate the URL to get diary weather
     * 
     * @param location 地区城市ID 
     * 1.例如：location=WX4FBXXFKE4F
     * 2.城市中文名 例如：location=北京
     * 3.省市名称组合 例如：location=辽宁朝阳、location=北京朝阳
     * 4.城市拼音/英文名 例如：location=beijing（如拼音相同城市，可在之前加省份和空格，例：shanxi yulin）
     * 5.经纬度 例如：location=39.93:116.40（格式是 纬度:经度，英文冒号分隔）
     * IP地址 例如：location=220.181.111.86（某些IP地址可能无法定位到城市）
     * “ip”两个字母 自动识别请求IP地址，例如：location=ip
     * @param language 多语言支持，默认值zh-Hans简体中文
     * @param unit 数据单位，两个可选： c和f
     * @param start 起始日期， start=-2 代表前天、start=-1 代表昨天、start=0 代表今天、start=1 代表明天
     * @param days 天数 (可选) 返回从start算起days天的结果。默认为你的权限允许的最多天数
     */
    public String generateGetDiaryWeatherURL(
            String location,
            String language,
            String unit,
            String start,
            String days
    )  throws SignatureException, UnsupportedEncodingException {
        String timestamp = String.valueOf(new Date().getTime());
        String params = "ts=" + timestamp + "&ttl=30&uid=" + TIANQI_API_USER_ID;
        String signature = URLEncoder.encode(generateSignature(params, TIANQI_API_SECRET_KEY), "UTF-8");
        return TIANQI_DAILY_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location + "&language=" + language + "&unit=" + unit + "&start=" + start + "&days=" + days;
    }

    /**
     * generator生成 API URL
     */
    public static String getURL(){
    	String url = null;
        SeniverseApiUtils api = new SeniverseApiUtils();
        try {
            url = api.generateGetDiaryWeatherURL(
                    "ip",
                    "zh-Hans",
                    "c",
                    "0",
                    "1"
            );
        } catch (Exception e) {
           log.error("心知天气生成 API URL失败，error:{}", e);
        }
        return url;
    }
}
