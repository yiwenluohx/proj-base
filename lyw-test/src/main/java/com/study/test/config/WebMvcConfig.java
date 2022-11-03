package com.study.test.config;

import com.study.test.interceptor.AuthorizeInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author luohx
 * @version 1.0.0
 * @date: 2022/11/3 下午3:57
 * @menu
 */
@Configuration
@ConditionalOnExpression("#{ (!'dev'.equalsIgnoreCase(environment['env']))  }")
public class WebMvcConfig implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor getAuthorizeInterceptor() {
        return new AuthorizeInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthorizeInterceptor())
                //排除以下链接的验证
                .excludePathPatterns("/amendsupport/**", "/api/open/**", "/rdfa-timer/**", "/integration/**", "/v2/api-docs", "/swagger-resources/**", "/**.html", "/module/**", "/webjars/**", "/monitor/**")
                .addPathPatterns("/**");
    }
}
