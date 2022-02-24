package com.study.core.ro;

import com.study.core.base.RdfaObject;
import com.study.core.constants.RdfaResultConstants;

import java.io.Serializable;

/**
 * ClassName: RdfaResult
 * Description:
 * @Author: luohx
 * Date: 2022/2/22 下午5:00
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
final public class RdfaResult<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success = true;

    private String code;

    private String message;

    private T data;

//    private Throwable cause;


    /**
     * Constructs a new RdfaResult with no parameters.
     */
    public RdfaResult() {
        super();
    }


    /**
     * Constructs a new RdfaResult with the specified code and message.
     *
     * @param code    the return code.The detail message is saved for
     *                later retrieval by the {@link #getCode()} method.
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public RdfaResult(String code, String message) {
        this(true, code, message);
    }

    /**
     * Constructs a new RdfaResult with the specified success, code and message.
     *
     * @param success the success remark. The success is saved for later retrieval by
     *                the {@link #isSuccess()} method.
     * @param code    the return code.The detail code is saved for
     *                later retrieval by the {@link #getCode()} method.
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public RdfaResult(boolean success, String code, String message) {
        this(success, code, message, null, null);
    }

    /**
     * Constructs a new RdfaResult with the specified success, code, message and data.
     *
     * @param success the success remark. The success is saved for later retrieval by
     *                the {@link #isSuccess()} method.
     * @param code    the return code.The detail code is saved for
     *                later retrieval by the {@link #getCode()} method.
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     * @param data    the return data. The detail data is saved for
     *                later retrieval by the {@link #getData()}  method.
     */
    public RdfaResult(boolean success, String code, String message, T data) {
        this(success, code, message, data, null);
    }

    /**
     * Constructs a new RdfaResult with the specified success, code, message, data and exception
     *
     * @param success   the return result is defined by the user. The detail
     *                  success is saved for later retrieval by the {@link #isSuccess()} method.
     * @param code      the return code.The detail code is saved for
     *                  later retrieval by the {@link #getCode()} method.
     * @param message   the detail message. The detail message is saved for
     *                  later retrieval by the {@link #getMessage()} method.
     * @param data      the return data. The detail data is saved for
     *                  later retrieval by the {@link #getData()}  method.
     */
    public RdfaResult(boolean success, String code, String message, T data, Throwable cause) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
//        this.cause = cause;
    }


    public static <T extends Serializable>  RdfaResult<T> success(String code,String message,T data) {
        RdfaResult rest = new RdfaResult();
        rest.setCode(code);
        rest.setMessage(message);
        rest.setData(data);
        rest.setSuccess(true);
        return rest;
    }

    public static <T extends Serializable>  RdfaResult<T> success(T data) {
        RdfaResult rest = new RdfaResult();
        rest.setCode(RdfaResultConstants.SUCCESS_CODE);
        rest.setMessage(RdfaResultConstants.SUCCESS_MESSAGE);
        rest.setData(data);
        rest.setSuccess(true);
        return rest;
    }

    public static<T extends Serializable> RdfaResult<T> fail(String code,String message) {
        RdfaResult<T> rest = new RdfaResult<T>();
        rest.setCode(code);
        rest.setMessage(message);
        rest.setSuccess(false);
        return rest;
    }


    public String toLog() {
        int length=20;
        String data_str=null;
        if(code!=null){
            length +=code.length();
        }
        if(message!=null){
            length +=message.length();
        }
        if(data != null){
            if(data instanceof RdfaObject) {
                data_str = ((RdfaObject) data).toLog();
                length += data_str.length();
            } else {
                data_str = data.toString();
                length +=data_str.length();
            }
        }
        StringBuilder sb = new StringBuilder(length);
        sb.append("code:");
        sb.append(code);
        sb.append(" message:");
        sb.append(message);
        sb.append(" data:");
        sb.append(data_str);

        return  sb.toString();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}