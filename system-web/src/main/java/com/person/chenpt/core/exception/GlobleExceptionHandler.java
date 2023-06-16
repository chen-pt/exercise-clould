package com.person.chenpt.core.exception;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.person.chenpt.core.base.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 统一
 * 异常处理
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobleExceptionHandler {
    /**
     * 异常处理
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result<ErrorInfo> exceptionHandler(HttpServletRequest request, Exception e) {
        if (log.isInfoEnabled()) {
            log.info(e.getMessage(), e);
        } else if (log.isDebugEnabled()) {
            log.error(e.getMessage(), e);
        }
        ResponseCode code = ResponseCode.ERROR_UNKNOWN;
        String errorMsg = e.getMessage();
        if (e instanceof MethodArgumentNotValidException) {
            code = ResponseCode.ERROR_REQUEST_PARAM;
            errorMsg = buildMsgStrByBindingResult(((MethodArgumentNotValidException) e).getBindingResult());
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            errorMsg = buildMsgStrByBindingResult(ex.getBindingResult());
            code = ResponseCode.ERROR_REQUEST_PARAM;
        } else if (e instanceof IllegalArgumentException) {
            code = ResponseCode.ERROR_DATA;
        } else if (e instanceof DataIntegrityViolationException) {
            code = ResponseCode.ERROR_DATA;
            errorMsg = "违反唯一或完整性约束";
        } else if (e instanceof AuthorizationException) {
            code = ResponseCode.ERROR_PERMISSION;
        } else if (e instanceof BusMessageException) {
            code = ResponseCode.ERROR_BUS;
        } else if (e instanceof HttpMessageNotReadableException) {
            code = ResponseCode.ERROR_REQUEST_PARAM;
        }

        // errorMsg 前几个字符包含中文，则提示抛出异常的信息
        String msg = Validator.hasChinese(StrUtil.subPre(errorMsg, 10)) ? errorMsg : code.getDescription();
        ErrorInfo data = new ErrorInfo(e, request.getRequestURI());
        return Result.build(code.getCode(), msg, data);
    }

    private static String buildMsgStrByBindingResult(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder("参数校验失败。");
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            sb.append("[").append(buildObjectErrorMsg(error)).append("] ");
        }
        return sb.toString();
    }

    private static StringBuilder buildObjectErrorMsg(ObjectError error) {
        StringBuilder sb = new StringBuilder("");
        if (error instanceof FieldError) {
            FieldError e = (FieldError) error;
            if (e.contains(TypeMismatchException.class)) {
                sb.append(e.getField()).append(" 类型转化异常");
            } else {
                sb.append(e.getField()).append(e.getDefaultMessage());
            }
        }
        return sb;
    }
}