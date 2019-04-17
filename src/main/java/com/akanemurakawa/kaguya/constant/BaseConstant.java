package com.akanemurakawa.kaguya.constant;

/**
 * 基础常量
 */
public interface BaseConstant {

    /**
     * 成功提示信息
     */
    String SUCCESS_CODE = "OK";

    /**
     * 失败提示信息
     */
    String FAIL_CODE = "errors";

    /**
     * 成功状态码
     */
    int SUCCESS_STATUS = 200;

    /**
     * 失败状态码
     */
    int FAIL_STATUS = 400;

    /**
     * 在线人数Session
     */
    String SESSION_ON_LINE_USER = "onlineUser";

    /**
     * 验证码Session
     */
    String SESSION_VERIFY_CODE = "verifyCode";
}
