package com.study.core.enums;

/**
 * ClassName: ExceptionType
 * Description: 异常类型
 *
 * @Author: luohx
 * Date: 2022/3/29 下午2:24
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0             异常类型
 */
public enum ExceptionType {
    FAILURE("-99000", ""),
    METHOD_ARGUMENT_NOT_VALID("-99001", "参数错误"),
    METHOD_ARGUMENT_TYPE_MISMATCH("-99002", "参数类型错误"),
    MESSAGE_NOT_READABLE("-99003", "参数解析错误"),
    MISSING_REQUEST_HEADER("-99004", "缺少请求头"),
    REQUEST_METHOD_NOT_SUPPORTED("-99005", "请求方法不支持"),
    MEDIA_TYPE_NOT_SUPPORTED("-99006", "不支持的媒体类型");

    private String code;
    private String message;

    ExceptionType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
