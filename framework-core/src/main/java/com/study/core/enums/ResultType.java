package com.study.core.enums;

/**
 * ClassName: ResultType
 * Description: 结果类型
 * Author: luohx
 * Date: 2022/2/23 下午4:29
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0              结果类型
 */
public enum ResultType implements ErrorType {

    SUCCESS("0", "成功"),
    FAILTURE("-1", "失败"),
    REPEAT_REQUEST("-2", "请求重复提交"),
    UNKNOWN_EXCEPTION("-6", "未知系统异常"),
    WITHOUT_LOGIN("-11", "未登录"),
    UNSUPPORTED_MEDIA_TYPE("-19", "不支持的媒体类型"),
    METHOD_NOT_ALLOWED("-20", "请求方法错误"),
    LOGIN_EXPIRATION("-21", "登录失效"),
    TOKEN_INVALID("-601", "token 不合法");

    private String code;
    private String message;

    ResultType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}