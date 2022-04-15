package com.study.core.exception;

import com.study.core.enums.ExceptionType;
import com.study.core.ro.RdfaResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ValidationException;

/**
 * ClassName: WebExceptionAdvice
 * Description:
 * @Author: luohx
 * Date: 2022/3/29 下午2:22
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
@ResponseBody
@ControllerAdvice
public class WebExceptionAdvice {
    private final Logger logger = LoggerFactory.getLogger(WebExceptionAdvice.class);

    private RdfaResult<String> buildResult(ExceptionType exceptionType) {
        return RdfaResult.fail(exceptionType.code(), exceptionType.message());
    }

    private RdfaResult<String> buildResult(ExceptionType exceptionType, String message) {
        return RdfaResult.fail(exceptionType.code(), message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RdfaResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.error("参数错误 {}", e.getMessage());
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        return buildResult(ExceptionType.METHOD_ARGUMENT_NOT_VALID, error.getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RdfaResult<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        logger.error("参数类型错误 {}", e.getMessage());
        return buildResult(ExceptionType.METHOD_ARGUMENT_TYPE_MISMATCH);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RdfaResult<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("参数解析错误 {}", e.getMessage());
        return buildResult(ExceptionType.MESSAGE_NOT_READABLE);
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public RdfaResult<String> handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        logger.error("缺少请求头 {}", e.getMessage());
        return buildResult(ExceptionType.MISSING_REQUEST_HEADER, e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public RdfaResult<String> handleBindException(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();

        RdfaResult<String> result = buildResult(ExceptionType.FAILURE, e.getMessage());
        if (fieldError != null) {
            logger.error("参数绑定失败 {}", fieldError.getDefaultMessage());
            result.setMessage(fieldError.getDefaultMessage());
        }
        return result;
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RdfaResult<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        logger.error("不支持的请求方法 {}", e.getMessage());
        RdfaResult<String> result = buildResult(ExceptionType.REQUEST_METHOD_NOT_SUPPORTED);
        String message = "接口不支持 " + e.getMethod() + " 请求";
        if (e.getSupportedMethods() != null && e.getSupportedMethods().length > 0) {
            message += ", 请使用" + String.join(",", e.getSupportedMethods());
        }
        result.setMessage(message);
        return result;
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public RdfaResult<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        logger.error("{}: {}", ExceptionType.MEDIA_TYPE_NOT_SUPPORTED.message(), e.getMessage());
        return buildResult(ExceptionType.MEDIA_TYPE_NOT_SUPPORTED);
    }

    @ExceptionHandler(BusinessException.class)
    public RdfaResult<String> handleBusinessException(BusinessException e) {
        logger.error("业务异常 ", e);
        return RdfaResult.fail(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(ExcelReadException.class)
    public RdfaResult<String> handleExcelReadException(ExcelReadException e) {
        logger.error(e.getMessage(), e);
        return buildResult(ExceptionType.FAILURE, e.getMessage());
    }

    @ExceptionHandler(ExcelWriteException.class)
    public RdfaResult<String> handleExcelWriteException(ExcelWriteException e) {
        logger.error(e.getMessage(), e);
        return buildResult(ExceptionType.FAILURE, e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public RdfaResult<String> handleValidationException(ValidationException e) {
        logger.error(e.getMessage(), e);
        return buildResult(ExceptionType.FAILURE, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public RdfaResult<String> handleException(Exception e) {
        logger.error("服务异常", e);
        return buildResult(ExceptionType.FAILURE, "服务异常");
    }
}
