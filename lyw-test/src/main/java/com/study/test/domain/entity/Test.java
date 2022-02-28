package com.study.test.domain.entity;

import com.alibaba.fastjson.JSON;
import com.study.core.po.BasePo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


/**
* ClassName: Test
* Description: test
* @Author: luohongxiao
* Date: 2022年02月28日
* History:
*<author>    <time>    <version>        <desc>
* luohongxiao  2022年02月28日     1.0       test - Test
*/
@Table(name = "test")
public class Test extends BasePo {
    public static final String TABLE_ALIAS = "Test";

    /**
    * 
    * Code generator automatic generation
    */
        @Id
    @Column(name = "test_id")
    private Long testId;
    /**
    * 
    * Code generator automatic generation
    */
    @Column(name = "num")
    private Integer num;

    /**
    * Code generator automatic generation
    */
    public Long getTestId() {
       return testId;
    }

    /**
    * Code generator automatic generation
    */
    public void setTestId(Long testId) {
       this.testId = testId;
    }
    /**
    * Code generator automatic generation
    */
    public Integer getNum() {
       return num;
    }

    /**
    * Code generator automatic generation
    */
    public void setNum(Integer num) {
       this.num = num;
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