package com.study.test.common.threadLocal;

/**
 * 当前登录用户信息
 *
 * @author luohx
 * @version 1.0.0
 * @date: 2022/05/23 12:38
 */
public class UserInfo {

    /**
     * 公司ID
     */
    private Long eid;
    /**
     * 用户ID
     */
    private Long userId;


    /**
     * functionCode
     */
    private String functionCode;

    /**
     * uri-path
     */
    private String uri;

    /**
     * Gets the value of eid.
     *
     * @return the value of eid
     */
    public Long getEid() {
        return eid;
    }

    /**
     * Sets the value of eid.
     *
     * @param eid eid
     */
    public void setEid(Long eid) {
        this.eid = eid;
    }

    /**
     * Gets the value of userId.
     *
     * @return the value of userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the value of userId.
     *
     * @param userId userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the value of functionCode.
     *
     * @return the value of functionCode
     */
    public String getFunctionCode() {
        return functionCode;
    }

    /**
     * Sets the value of functionCode.
     *
     * @param functionCode functionCode
     */
    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    /**
     * Gets the value of uri.
     *
     * @return the value of uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * Sets the value of uri.
     *
     * @param uri uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }
}
