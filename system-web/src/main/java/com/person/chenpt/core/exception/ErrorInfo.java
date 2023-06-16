package com.person.chenpt.core.exception;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorInfo {
    private String uri;
    private String message;
    private String exceptionClazz;
    private String exceptionStacktrace;

    public ErrorInfo(Exception e, String uri) {
        this.uri = uri;
        this.message = StrUtil.subPre(e.getMessage(),100)+"...";
        this.exceptionClazz = e.getClass().getName();
        this.exceptionStacktrace = ExceptionUtil.stacktraceToString(e,1024)+"...";
    }
}
