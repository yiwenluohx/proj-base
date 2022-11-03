package com.study.test.config;

import java.lang.annotation.*;

/**
 * 权限自定义注解
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2022/11/2 下午5:57
 * @menu
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Authorize {
    enum Type {
        /**
         * 场景owner
         */
        OWNER,
        /**
         * 单据详情
         */
        BILL_DETAIL,
        /**
         * 单据操作
         */
        BILL_ACTION,
        /**
         * 场景owner\单据详情\单据操作 有其一即可
         */
        ONCE_AUTH,
        /**
         * 其他场景
         */
        OTHER
    }
    ;
    /**
     * 鉴权类型
     *
     * @return
     */
    Type type() default Type.OWNER;

    /**
     * 单据所属企业ID
     * 无eidExpress有效
     *
     * @return
     */
    String eid() default "";

    /**
     * 获取eid的表达式
     * 优先级比eid高
     *
     * @return
     */
    String eidExpress() default "";

    /**
     * 单据ID
     *
     * @return
     */
    String dataId() default "";


    /**
     * 异常信息
     *
     * @return
     */
    String message() default "暂无权限操作,请联系管理员";
}
