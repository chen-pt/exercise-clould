package com.person.chenpt.core.util;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 响应包装类
 * @author: ding.haiyang
 */
public class Result<T> implements Serializable {
    @ApiModelProperty("编码")
    protected String code;
    @ApiModelProperty("消息")
    protected String msg;
    @ApiModelProperty("数据")
    private T data;

    public Result() {
    }
    public Result(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> build(String code, String msg, T data) {
        return new Result(code, msg, data);
    }
    public static <T> Result<T> build(int code, String msg, T data) {
        return new Result(String.valueOf(code), msg, data);
    }
    public static <T> Result<T> success() {
        return success(null);
    }
    public static <T> Result<T> success(T data) {
        return success(null,data);
    }
    public static <T> Result<T> successMsg(String msg) {
        return new Result(Code.DEFAULT_SUCCESS, msg,null);
    }
    public static <T> Result<T> success(String msg,T data) {
        return new Result(Code.DEFAULT_SUCCESS, msg, data);
    }

    public static <T> Result<T> failure() {
        return failure(null);
    }
    public static <T> Result<T> failure(T data) {
        return failure(null,data);
    }
    public static <T> Result<T> failureMsg(String msg) {
        return failure(msg,null);
    }
    public static <T> Result<T> failure(String msg,T data) {
        return new Result(Code.DEFAULT_FAIL, msg, data);
    }


    /**
     * 比较两个字符串是否相等
     * @param a
     * @param b
     * @return
     */
    private static boolean compareString(String a,String b){
        return null != a && a.equals(b);
    }

    public String getCode() {
        return code;
    }

    public  Result setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
    public interface Code{
        String DEFAULT_SUCCESS = "0";
        String DEFAULT_FAIL = "1";
    }
}
