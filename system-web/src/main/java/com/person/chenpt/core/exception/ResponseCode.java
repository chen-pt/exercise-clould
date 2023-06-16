package com.person.chenpt.core.exception;


import com.person.chenpt.core.base.model.Code;

/**
 * 拦截统一响应
 * @author ding.haiyang
 * @since 2020/7/3
 */
public enum ResponseCode implements Code<String> {
    /**
     * 未知
     */
    ERROR_UNKNOWN("advice-error-unknown", "未知异常"),
    ERROR_REQUEST_PARAM("advice-error-request-param", "请求参数异常"),
    ERROR_DATA("advice-error-data", "数据异常"),
    ERROR_PERMISSION("advice-error-permission", "无权限"),
    ERROR_BUS("advice-error-business", "业务操作异常"),
    ;
    private final String code;
    private final String description;

    ResponseCode(final String code, final String description) {
        this.code = code;
        this.description = description;
    }
    @Override
    public String getCode() {
        return code;
    }
    @Override
    public String getName() {
        return null;
    }
    @Override
    public String getDescription() {
        return description;
    }
}
