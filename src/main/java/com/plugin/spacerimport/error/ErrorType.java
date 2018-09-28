package com.plugin.spacerimport.error;

public enum ErrorType {

    /**
     * 公共异常
     */
    Exception,
    /**
     * 无法查询到bean
     */
    NotBean,
    /**
     * 无没有相关的方法
     */
    NotMethod,
    /**
     *转换异常
     */
    ContrastError,
    /**
     * 返回类型异常
     */
    ReturnNotRPCResponse,

}
