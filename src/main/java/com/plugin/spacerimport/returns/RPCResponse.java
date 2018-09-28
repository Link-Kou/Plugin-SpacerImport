package com.plugin.spacerimport.returns;

import com.plugin.spacerimport.error.ErrorType;

import java.lang.reflect.Type;

/**
 * Created by LK .
 * 公开的以接口形式的返回对象
 * @author LK
 * @version 1.0
 * @data 2017-05-20 12:14
 */
public interface RPCResponse<T> extends Type {

	/**
	 * 错误原因
	 * @return
	 */
	Exception getException();

	/**
	 * 错误结果
	 * @return
	 */
	ErrorType getErrorType();

	/**
	 * body Json String
	 * @return
	 */
	String getJsonString();

	/**
	 * 成功状态
	 * @return
	 */
	boolean isSuccessful();

	/**
	 * 返回对象
	 * @return
	 */
	T getModel();

}
