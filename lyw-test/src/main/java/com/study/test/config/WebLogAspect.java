package com.study.test.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * ClassName: WebLogAspect
 * Description: web层日志切面
 * Author: luohx
 * Date: 2022/4/14 下午4:32
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0            web层日志切面
 */
@Aspect
@Component
@Order(1)
public class WebLogAspect {
    private final static Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    //设置POINT_CUT
//    private static final String POINT_CUT_WEB = "execution(public * cn.ygyg.quality.trace.interfaces.web..*.*(..))";
//    private static final String POINT_CUT_MOBILE = "execution(public * cn.ygyg.quality.trace.interfaces.mobile..*.*(..))";

    private static final String POINT_CUT_WEB = "execution(public * com.study.test..*.*(..))";
    //拦截的method
    private static final Set<String> METHOD_SET = Sets.newHashSet("POST", "PUT", "PATCH", "DELETE");

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
     * 打印请求日志
     *
     * @version 1.0
     */
    private void printRequestMsg(HttpServletRequest request, JoinPoint joinPoint) {
        String method = request.getMethod();
        //打印各种请求日志
        log.info("【full requestURL】:" + request.getRequestURL().toString());
        log.debug("【remoteAddr】:" + request.getRemoteAddr());
        log.debug("【remoteHost】:" + request.getRemoteHost());
        log.debug("【localAddr】:" + request.getLocalAddr());
        log.debug("【requestMethod】:" + method);
        log.info("【headers】:" + getHeadersInfo(request));
        log.info("【parameters】:" + this.getParam(request.getParameterMap()));
        //判断method是否满足定义拦截的请求类型
        if (METHOD_SET.contains(method)) {
            log.info("【" + request.getMethod() + "Params】:" + getRequestParam(joinPoint, request));
        }
        log.info("【classMethod】:" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    /**
     * 获取请求参数
     *
     * @version 1.0
     * @param joinPoint
     * @param request
     * @return
     */
    private Map<String, Object> getRequestParam(JoinPoint joinPoint, HttpServletRequest request) {
        Map<String, Object> paramMap = new LinkedHashMap<>();
        //获取连接点（Joint Point）的签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        //遍历paramNames
        for (int i = 0; i < parameterNames.length; i++) {
            if (args[i] instanceof HttpServletRequest) {
                Enumeration<String> params = request.getParameterNames();
                while (params.hasMoreElements()) {
                    String key = params.nextElement();
                    paramMap.put(key, request.getParameter(key));
                }
            } else if (args[i] instanceof ExtendedServletRequestDataBinder) {
                //不返回任何值
            }else {
                paramMap.put(parameterNames[i], JSON.toJSONString(args[i]));
            }
        }
        return paramMap;
    }

    /**
     * 拼接传递的参数
     *
     * @version 1.0
     */
    private String getParam(Map<String, String[]> map) {
        StringBuilder str = new StringBuilder();
        for (String key : map.keySet()) {
            if (!str.toString().equals(""))
                str.append("&");
            str.append(key + "= " + String.join(",", map.get(key)));
        }
        return str.toString();
    }

    /**
     * 从header里面获取headerNames
     *
     * @version 1.0
     */
    private JSONObject getHeadersInfo(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        Enumeration<String> headerNames = request.getHeaderNames();
        //循环遍历headerNames，组装map
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            jsonObject.put(key, value);
        }
        return jsonObject;
    }

    /**
     * AfterReturning
     *
     * @version 1.0
     */
    @AfterReturning(returning = "response", pointcut = "cutWebLog() || cutMobileLog()")
    public void doAfterReturning(Object response) {
        // 处理完请求，返回内容
        log.info("【RESPONSE】: " + JSON.toJSONString(response));
        log.info("【SPEND TIME】: " + (System.currentTimeMillis() - startTime.get()) + "ms");
    }
}
