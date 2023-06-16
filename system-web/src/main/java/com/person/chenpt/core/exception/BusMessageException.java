package com.person.chenpt.core.exception;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 业务提示异常
 */
public class BusMessageException extends RuntimeException{
    private static final long serialVersionUID = 8247610319171014183L;

    public BusMessageException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public BusMessageException(String message) {
        super(message);
    }

    public BusMessageException(String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params));
    }

    public BusMessageException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BusMessageException(Throwable throwable, String messageTemplate, Object... params) {
        super(StrUtil.format(messageTemplate, params), throwable);
    }
}
