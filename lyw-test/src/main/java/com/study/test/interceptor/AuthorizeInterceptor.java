package com.study.test.interceptor;

import com.study.core.exception.AuthException;
import com.study.core.utils.EnvUtils;
import com.study.test.common.threadLocal.LocalContext;
import com.study.test.config.AbstractAuthInterceptorAdapter;
import com.study.test.config.Authorize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;

/**
 * 权限拦截器
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2022/11/2 下午6:16
 * @menu 权限拦截器
 */
public class AuthorizeInterceptor extends AbstractAuthInterceptorAdapter {
    private final static Logger logger = LoggerFactory.getLogger(AuthorizeInterceptor.class);

    /**
     * 权限Handler
     */
//    @Autowired
//    private AuthHandler authHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {

            //将前端的eid和userid存入threadlocal
            Long xeid = Optional.ofNullable(getXeid(request)).orElse(null);
            Long userId = getUserId(request);
            LocalContext.setLocalContext(xeid, userId, getFunctionCode(request), getUriPath(request));

            HandlerMethod h = (HandlerMethod) handler;
            Authorize annotation = h.getMethodAnnotation(Authorize.class);
            if (null != annotation && !isDevEnv()) {
                // 企业ID
                Long eid = getEid(request, annotation);
                // 功能CODE
                String functionCode = getFunctionCode(request);
                // urlPath
                String urlPath = getUriPath(request);
                // 单据ID
                Long dataId = getDataId(request, annotation);
                // 注解鉴权类型
                Authorize.Type annotationType = annotation.type();
                logger.info("权限拦截校验，请求参数 --> 请求路径：{}，urlPath：{}，annotationType：{}，dataId：{}，functionCode：{}，userId：{}，eid：{}", request.getRequestURL(), urlPath, annotationType, dataId, functionCode, userId, eid);
                // 判断相应权限操作
                if (null == userId || null == eid) {
                    throw new AuthException(String.format("缺少鉴权参数，userId:%s,eid:%s", userId, eid));
                }
                if (dataId <= 0L && !annotationType.equals(Authorize.Type.OTHER)) {
                    // 单据dataId如果为空或者小于0，默认校验场景owner的权限
                    annotationType = Authorize.Type.OWNER;
                }
                if (annotationType.equals(Authorize.Type.OWNER)) {
                    logger.info("权限拦截校验 -- 场景owner权限校验");
                    // 判断当前用户是否是场景owner
//                    authHandler.isOwner(userId, functionCode, eid);
                } else if (annotationType.equals(Authorize.Type.BILL_DETAIL)) {
                    logger.info("权限拦截校验 -- 单据详情权限校验");
                    // 判断当前用户在当前功能CODE上是否有单据详情的查看权限
//                    authHandler.detailAuth(dataId, functionCode, userId, eid);
                } else if (annotationType.equals(Authorize.Type.BILL_ACTION)) {
                    logger.info("权限拦截校验 -- 单据操作权限校验");
                    // 判断当前用户在当前功能CODE上是否有单据的操作权限
//                    authHandler.operationAuth(dataId, functionCode, urlPath, userId, eid);
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 当前环境是否为dev
     *
     * @return boolean
     */
    private boolean isDevEnv() {
        return EnvUtils.getEnv().toLowerCase(Locale.ROOT).contains("dev");
    }

    /**
     * 获取企业信息
     *
     * @param request    请求
     * @param annotation 注释
     * @return {@link Long}
     */
    public Long getEid(HttpServletRequest request, Authorize annotation) {
        Long eid = super.getEid(request, annotation);
        eid = Optional.ofNullable(eid).orElse(LocalContext.getEid());
        Optional.ofNullable(eid).orElseThrow(() -> new AuthException("企业id未能成功获取"));
        return eid;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.info("ThreadLocal-------------触发删除用户信息----------");
        LocalContext.clear();
    }
}
