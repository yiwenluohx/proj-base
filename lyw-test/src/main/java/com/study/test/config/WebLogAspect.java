package com.study.test.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2023/8/16 下午5:29
 * @menu
 */
@Aspect
@Component
@Order(1)
public class WebLogAspect extends AbstractWebLogAspect {

//    private static final String POINT_CUT_WEB = "execution(public * cn.ygyg.quality.trace.interfaces.web..*.*(..))";
//    private static final String POINT_CUT_MOBILE = "execution(public * cn.ygyg.quality.trace.interfaces.mobile..*.*(..))";

    /**
     * 拦截包或者子包中定义的方法
     */
    private static final String POINT_CUT_WEB = "execution(public * com.study.test.controller.*.*(..))";

    /**
     * 定义POINT_CUT
     *
     * @version 1.0
     */
    @Pointcut(POINT_CUT_WEB)
    public void cutWebLog() {
    }

//    /**
//     * mobile POINT_CUT
//     *
//     * @version 1.0
//     */
//    @Pointcut(POINT_CUT_MOBILE)
//    public void cutMobileLog() {
//    }

    /**
     * 切面开始
     *
     * @version 1.0
     */
//    @Before("cutWebLog() || cutMobileLog()")
    @Before("cutWebLog()")
    public void doBefore(JoinPoint joinPoint) {
        //设置请求开始时间
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //打印参数日志
        printRequestMsg(request, joinPoint);
    }

    /**
     * AfterReturning（pointcut = "cutWebLog() || cutMobileLog()"）
     *
     * @version 1.0
     */
    @AfterReturning(returning = "response", pointcut ="cutWebLog()")
    public void doAfterReturning(Object response) {
        printResponseMsg(response);
    }
}
