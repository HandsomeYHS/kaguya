package com.akanemurakawa.kaguya.model.entity.data;

import com.akanemurakawa.kaguya.constant.BaseConstant;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 1.首先调用success()或者fail()的方法设置status和code属性，
 * 2.需要添加数据则使用add()
 */
@Data
public class Msg {

	/**
	 * status:状态码
	 * 200: success成功
	 * 400: fail失败
	 */
	private int status;
	
	/**
     * 提示信息
     */
	private String code;
	
	/**
     * 返回给浏览器的数据
     */
	private Map<String, Object> data = new HashMap<String, Object>();
	
	/**
	 * 执行成功
	 */
	public static Msg success() {
		Msg result = new Msg();
		result.setStatus(BaseConstant.SUCCESS_STATUS);
		result.setCode(BaseConstant.SUCCESS_CODE);
		return result;
	}
	
	/**
	 * 执行失败
	 */
	public static Msg fail() {
		Msg result = new Msg();
		result.setStatus(BaseConstant.FAIL_STATUS);
		result.setCode(BaseConstant.FAIL_CODE);
		return result;
	}
	
	/**
	 * 执行成功，自定义code
	 */
	public static Msg success(String code) {
		Msg result = new Msg();
		result.setStatus(BaseConstant.SUCCESS_STATUS);
		result.setCode(code);
		return result;
	}
	
	/**
	 * 执行失败，自定义code
	 */
	public static Msg fail(String code) {
		Msg result = new Msg();
		result.setStatus(BaseConstant.FAIL_STATUS);
		result.setCode(code);
		return result;
	}
	
	/**
	 * 返回一个Msg，将数据放入到Msg中，这里Data就是得到要传的数据。
	 */
	public Msg add(String key, Object value) {
		this.getData().put(key, value);
		return this;
	}
	
	public Msg() {
		super();
	}

	public Msg(int status, String code) {
		super();
		this.status = status;
		this.code = code;
	}
	
	public Msg(String code) {
		super();
		this.code = code;
	}
}