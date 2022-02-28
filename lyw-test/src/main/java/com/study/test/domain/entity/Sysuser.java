package com.study.test.domain.entity;

import com.alibaba.fastjson.JSON;
import com.study.core.po.BasePo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


/**
* ClassName: Sysuser
* Description: sysuser
* @Author: luohongxiao
* Date: 2022年02月28日
* History:
*<author>    <time>    <version>        <desc>
* luohongxiao  2022年02月28日     1.0       sysuser - Sysuser
*/
@Table(name = "sysuser")
public class Sysuser extends BasePo {
    public static final String TABLE_ALIAS = "Sysuser";

    /**
    * 
    * Code generator automatic generation
    */
        @Id
    @Column(name = "user_id")
    private Long userId;
    /**
    * 账号
    * Code generator automatic generation
    */
    @Column(name = "account")
    private String account;
    /**
    * 手机号
    * Code generator automatic generation
    */
    @Column(name = "mobile")
    private String mobile;
    /**
    * 
    * Code generator automatic generation
    */
    @Column(name = "email")
    private String email;
    /**
    * 是否启用
    * Code generator automatic generation
    */
    @Column(name = "is_enable")
    private Boolean isEnable;

    /**
    * Code generator automatic generation
    */
    public Long getUserId() {
       return userId;
    }

    /**
    * Code generator automatic generation
    */
    public void setUserId(Long userId) {
       this.userId = userId;
    }
    /**
    * Code generator automatic generation
    */
    public String getAccount() {
       return account;
    }

    /**
    * Code generator automatic generation
    */
    public void setAccount(String account) {
       this.account = account;
    }
    /**
    * Code generator automatic generation
    */
    public String getMobile() {
       return mobile;
    }

    /**
    * Code generator automatic generation
    */
    public void setMobile(String mobile) {
       this.mobile = mobile;
    }
    /**
    * Code generator automatic generation
    */
    public String getEmail() {
       return email;
    }

    /**
    * Code generator automatic generation
    */
    public void setEmail(String email) {
       this.email = email;
    }
    /**
    * Code generator automatic generation
    */
    public Boolean getIsEnable() {
       return isEnable;
    }

    /**
    * Code generator automatic generation
    */
    public void setIsEnable(Boolean isEnable) {
       this.isEnable = isEnable;
    }

    /**
    * Code generator automatic generation
    */
    @Override
    public String toString()
    {
       return JSON.toJSONString(this);
    }

    /**
    * Code generator automatic generation
    */
    @Override
    public String toLog() {
       return JSON.toJSONString(this);
    }
}