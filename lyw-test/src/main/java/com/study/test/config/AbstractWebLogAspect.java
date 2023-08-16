package com.study.test.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 抽象webLogAspect
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2023/8/16 下午5:20
 * @menu 抽象webLogAspect
 */
@Slf4j
public abstract class AbstractWebLogAspect {
    /**
     * 拦截的method
     */
    private static final Set<String> METHOD_SET = Sets.newHashSet("POST", "PUT", "PATCH", "DELETE");

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 打印请求日志
     *
     * @version 1.0
     */
    void printRequestMsg(HttpServletRequest request, JoinPoint joinPoint) {
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
        //打印拦截的方法全路径
        log.info("【classMethod】:" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }

    /**
     * 打印响应日志
     *
     * @param response 响应
     */
    void printResponseMsg(Object response) {
        // 处理完请求，返回内容
        log.info("【RESPONSE】: " + JSON.toJSONString(response));
        log.info("【SPEND TIME】: " + (System.currentTimeMillis() - startTime.get()) + "ms");
    }

    /**
     * 获取请求参数
     *
     * @param joinPoint
     * @param request
     * @return
     * @version 1.0
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
            } else {
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

}
