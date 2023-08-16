package com.study.test.filter;

import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2023/8/16 下午2:46
 * @menu
 */
public class ReplaceStreamFilter implements Filter {

    private List<String> excludeUrl;

    public ReplaceStreamFilter(List<String> excludeUrl) {
        this.excludeUrl = excludeUrl;
    }


    /**
     * 可重复读取inputStream的request
     *
     * @param request  请求
     * @param response 响应
     * @param chain    链
     * @throws IOException      ioexception
     * @throws ServletException servlet异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String uri = httpServletRequest.getRequestURI();
        boolean isMatch = excludeUrl.stream().anyMatch(x -> {
            if (antPathMatcher.match(x, uri)) {
                return true;
            }
            return false;
        });

        if (isMatch) {
            chain.doFilter(request, response);
        } else {
            ServletRequest requestWrapper = new RequestWrapper(httpServletRequest);
            chain.doFilter(requestWrapper, response);
        }
    }
}
