package com.test.httputils;

/**
 * @author Joe
 * @date 2019/9/18.
 * description：
 */
public class CommonResult<T> {

    private String status;
    private T data;
    private String errCode;
    private String errorMsg;

    public CommonResult(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errCode;
    }

    public void setErrorCode(String errorCode) {
        this.errCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}