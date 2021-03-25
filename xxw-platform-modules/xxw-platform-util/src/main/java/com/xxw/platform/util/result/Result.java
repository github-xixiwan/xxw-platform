package com.xxw.platform.util.result;

import com.xxw.platform.util.exception.BaseException;
import com.xxw.platform.util.exception.ErrorEnumInterface;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 申通Http返回的result
 *
 * @author ethan
 * @since 2019/11/14
 */
@ApiModel("响应对象")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("请求是否成功响应")
    private Boolean success;

    @ApiModelProperty("错误码")
    private Integer errorCode;

    @ApiModelProperty("错误信息")
    private String errorMsg;

    @ApiModelProperty("实际响应数据")
    private T data;

    private Result() {
    }

    public static <T> Result<T> success(T data) {
        Result<T> resp = new Result<>();
        resp.setSuccess(true);
        resp.setData(data);
        return resp;
    }

    public static <T> Result<T> failure(BaseException ex) {
        Result<T> resp = new Result<>();
        resp.setSuccess(false);
        resp.setErrorCode(ex.getCode());
        resp.setErrorMsg(ex.getMessage());
        return resp;
    }

    public static <T> Result<T> failure(ErrorEnumInterface errorEnum) {
        Result<T> resp = new Result<>();
        resp.setSuccess(false);
        resp.setErrorCode(errorEnum.getCode());
        resp.setErrorMsg(errorEnum.getMessage());
        return resp;
    }

    public static <T> Result<T> failure(Integer errorCode, String errorMsg) {
        Result<T> resp = new Result<>();
        resp.setSuccess(false);
        resp.setErrorCode(errorCode);
        resp.setErrorMsg(errorMsg);
        return resp;
    }

    public Boolean getSuccess() {
        return success;
    }

    private void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    private void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    private void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    private void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
