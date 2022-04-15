package com.study.test.config;

import com.study.core.exception.AuthException;
import com.study.core.exception.ServiceException;
import com.study.core.exception.WebExceptionAdvice;
import com.study.core.ro.RdfaResult;
import com.study.core.ro.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: GlobalExceptionAdvice
 * Description: 全局异常处理
 * @Author: luohx
 * Date: 2022/3/29 下午2:32
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionAdvice extends WebExceptionAdvice {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    /**
     * ServiceException异常拦截器
     *
     * @param e
     * @return
     * @version 1.0
     */
    @ExceptionHandler({ServiceException.class})
    public RdfaResult<String> handleServiceException(ServiceException e) {
        int code = 0;
        try {
            code = Integer.parseInt(e.getErrorCode());
        } catch (NumberFormatException ex) {
            return Resp.error(e.getErrorCode(), e.getMessage());
        }
        if (code > 0) {
            return Resp.ok(e.getErrorCode(), e.getMessage(), null);
        } else {
            if (e.getLogLevel().equals(Level.ERROR)) {
                this.logger.error("Service操作异常 {}", e);
            } else if (e.getLogLevel().equals(Level.WARN)) {
                this.logger.warn("Service操作异常 {}", e);
            } else if (e.getLogLevel().equals(Level.INFO)) {
                this.logger.info("Service操作异常 {}", e);
            } else if (e.getLogLevel().equals(Level.DEBUG)) {
                this.logger.debug("Service操作异常 {}", e);
            } else if (e.getLogLevel().equals(Level.TRACE)) {
                this.logger.trace("Service操作异常 {}", e);
            } else {
                this.logger.error("Service操作异常 {}", e);
            }
            return Resp.error(e.getErrorCode(), e.getMessage());
        }
    }

    /**
     * 鉴权失败
     *
     * @param e
     * @return
     * @version 1.0
     */
    @ExceptionHandler({AuthException.class})
    public ResponseEntity<RdfaResult<String>> AuthException(AuthException e) {
        RdfaResult<String> error = Resp.error("-403", e.getMessage());
        return ResponseEntity.status(403).body(error);
    }

    /**
     * 缺少请求参数拦截器
     *
     * @param e
     * @return
     * @version 1.0
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public RdfaResult<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        this.logger.warn("参数错误 {},参数名称：{}", e.getMessage(), e.getParameterName());
        return Resp.error("请求缺少参数");
    }

    /**
     * 参数校验异常拦截器
     *
     * @param e
     * @return
     * @version 1.0
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public RdfaResult<String> handleConstraintViolationException(ConstraintViolationException e) {
        this.logger.warn("参数错误 {}", e.getMessage());
        List<String> errmsg = e.getConstraintViolations().stream().map(x -> x.getMessageTemplate()).collect(Collectors.toList());
        return Resp.error(errmsg.get(0));
    }

    /**
     * 不合法的参数异常拦截
     *
     * @param e
     * @return
     * @version 1.0
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public RdfaResult<String> handleIllegalArgumentException(IllegalArgumentException e) {
        this.logger.warn("参数错误 {}", e.getMessage());
        return Resp.error(e.getMessage());
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @Override
    public RdfaResult<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.warn("参数错误 {}", e.getMessage());
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        return Resp.error(error.getDefaultMessage());
    }
}
