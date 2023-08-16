package com.study.test.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.study.core.utils.BearerTokenUtils;
import com.study.core.utils.ExceptionUtils;
import com.study.core.utils.JWTUtils;
import com.study.test.filter.RequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * 抽象权限拦截器
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2022/11/2 下午5:05
 * @menu
 */
@Slf4j
public class AbstractAuthInterceptorAdapter extends HandlerInterceptorAdapter {

    /**
     * 上下文对象实例
     */
    @Autowired
    public ApplicationContext applicationContext;

    /**
     * 获取functionCode
     *
     * @param request
     * @return
     */
    public String getFunctionCode(HttpServletRequest request) {
        return request.getHeader("functionCode");
    }

    /**
     * 获取uriPath
     *
     * @param request
     * @return
     */
    public String getUriPath(HttpServletRequest request) {
        return request.getHeader("uri-path");
    }

    /**
     * 获取token
     *
     * @param request
     * @return
     */
    public String getToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        return BearerTokenUtils.getToken(authorization);
    }

    /**
     * 获取userId
     *
     * @param request
     * @return
     */
    public Long getUserId(HttpServletRequest request) {
        try {
            return JWTUtils.getSubjectId(getToken(request));
        } catch (Exception e) {
            log.error("[AbstractAuthInterceptorAdapter]->获取用户失败,请求路径:{},异常信息:{}", request.getRequestURI(), e.getMessage());
        }
        return 1L;
    }

    /**
     * 获取X-EID
     *
     * @param request
     * @return
     */
    public Long getXeid(HttpServletRequest request) {
        try {
            return Long.parseLong(request.getHeader("x-eid"));
        } catch (Exception e) {
            log.error("[AbstractAuthInterceptorAdapter]->获取Header里面的x-eid异常,请求路径:{},异常信息:{}", request.getRequestURI(), e.getMessage());
        }
        return null;
    }

    /**
     * 获取单据ID
     *
     * @param request
     * @param annotation
     * @return
     **/
    public Long getDataId(HttpServletRequest request, Authorize annotation) {
        if (StringUtils.isBlank(annotation.dataId())) {
//            throw new AuthException("鉴权失败，缺少单据ID");
            return 0L;
        }
        try {
            return Long.parseLong(parseSpEl(request, annotation.dataId()));
        } catch (NumberFormatException e) {
            log.info("[AbstractAuthInterceptorAdapter]->获取dataId异常,请求路径:{},异常信息:{}", request.getRequestURI(), e.getMessage());
        }
        return null;
    }

    /**
     * 获取企业信息
     *
     * @param request
     * @param annotation
     * @return
     */

    public Long getEid(HttpServletRequest request, Authorize annotation) {
        //优先使用eid表达式
        if (StringUtils.isNotBlank(annotation.eidExpress())) {
            ExpressionParser parser = new SpelExpressionParser();
            StandardEvaluationContext context = (StandardEvaluationContext) context(request);
            context.setBeanResolver(new BeanFactoryResolver(applicationContext));
            String expression = parseSpEl(request, annotation.eidExpress());
            return parser.parseExpression(expression).getValue(context, Long.class);
        } else if (StringUtils.isNotBlank(annotation.eid())) {
            Long value = ExceptionUtils.handler(() -> Long.parseLong(parseSpEl(request, annotation.eid())), "parseSpEl获取eid异常");
            return value;
        } else {
            return null;
        }
    }

    /**
     * 解析parseSpEl
     *
     * @param request
     * @param spEl
     * @since: 2021/11/30 9:10 下午
     * @version 1.0
     */
    public String parseSpEl(HttpServletRequest request, String spEl) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        EvaluationContext context = context(request);

        return expressionParser.parseExpression(spEl).getValue(context, String.class);
    }

    /**
     * 得到EvaluationContext
     *
     * @param request
     * @return
     * @since: 2021/11/30 11:44 下午
     * @version 1.0
     */
    public EvaluationContext context(HttpServletRequest request) {
        EvaluationContext context = new StandardEvaluationContext();
        Map<String, String[]> args = request.getParameterMap();
        Set<Map.Entry<String, String[]>> entry = args.entrySet();
        Iterator<Map.Entry<String, String[]>> it = entry.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String[]> me = it.next();
            String key = me.getKey();
            String value = me.getValue()[0];
            context.setVariable(key, value);
        }
        parseBodyParam(request, context);
        return context;
    }

    /**
     * 解析RequestBody
     *
     * @param request
     * @param context
     * @since: 2021/11/30 8:53 下午
     * @version 1.0
     */
    public void parseBodyParam(HttpServletRequest request, EvaluationContext context) {
        String method = request.getMethod().toUpperCase();
        String type = request.getContentType();
        if ("POST".equals(method) && (StringUtils.isNotBlank(type) && type.toUpperCase(Locale.ROOT).contains("application/json".toUpperCase()))) {
            RequestWrapper requestWrapper = new RequestWrapper(request);
            String body = requestWrapper.getBodyString();
            JSONObject jsonObject = JSONObject.parseObject(body, Feature.OrderedField);
            for (Map.Entry entry : jsonObject.entrySet()) {
                context.setVariable(entry.getKey().toString(), entry.getValue());
            }
        }
    }
}
