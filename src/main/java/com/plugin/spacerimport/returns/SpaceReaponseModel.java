package com.plugin.spacerimport.returns;

import com.plugin.spacerimport.error.ErrorType;

/**
 * @author LK
 * @date 2018-05-29 17:29
 */
public class SpaceReaponseModel implements RPCResponse<Object>{


    /**
     * 错误原因
     */
    private Exception exception;
    /**
     * 错误类型
     */
    private ErrorType errortype;
    /**
     * json文本
     */
    private String jsonstring;
    /**
     * 是否成功
     */
    private boolean successful;
    /**
     * 返回对象
     */
    private Object model;

    /**
     * 错误原因
     */
    public SpaceReaponseModel setException(Exception exception) {
        this.exception = exception;
        return this;
    }

    /**
     * 错误类型
     */
    public SpaceReaponseModel setErrortype(ErrorType errortype) {
        this.errortype = errortype;
        return this;
    }

    /**
     * json文本
     */
    public SpaceReaponseModel setJsonstring(String jsonstring) {
        this.jsonstring = jsonstring;
        return this;
    }

    /**
     * 是否成功
     */
    public SpaceReaponseModel setSuccessful(boolean successful) {
        this.successful = successful;
        return this;
    }

    /**
     * 返回对象
     */
    public SpaceReaponseModel setModel(Object model) {
        this.model = model;
        return this;
    }

    /**
     * 错误原因
     *
     * @return
     */
    @Override
    public Exception getException() {
        return this.exception;
    }

    /**
     * 错误结果
     *
     * @return
     */
    @Override
    public ErrorType getErrorType() {
        return this.errortype;
    }

    /**
     * body Json String
     *
     * @return
     */
    @Override
    public String getJsonString() {
        return this.jsonstring;
    }

    /**
     * 成功状态
     *
     * @return
     */
    @Override
    public boolean isSuccessful() {
        return this.successful;
    }

    /**
     * 返回对象
     *
     * @return
     */
    @Override
    public Object getModel() {
        return this.model;
    }
}