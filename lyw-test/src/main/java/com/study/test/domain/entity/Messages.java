package com.study.test.domain.entity;

import com.alibaba.fastjson.JSON;
import com.study.core.po.BasePo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


/**
* ClassName: Messages
* Description: messages
* @Author: luohongxiao
* Date: 2022年02月28日
* History:
*<author>    <time>    <version>        <desc>
* luohongxiao  2022年02月28日     1.0       messages - Messages
*/
@Table(name = "messages")
public class Messages extends BasePo {
    public static final String TABLE_ALIAS = "Messages";

    /**
    * 
    * Code generator automatic generation
    */
        @Id
    @Column(name = "msg_id")
    private Long msgId;
    /**
    * 发言id
    * Code generator automatic generation
    */
    @Column(name = "from_id")
    private Long fromId;
    /**
    * 被评论id
    * Code generator automatic generation
    */
    @Column(name = "to_id")
    private Long toId;
    /**
    * 评论内容
    * Code generator automatic generation
    */
    @Column(name = "content")
    private String content;
    /**
    * 是否已读
    * Code generator automatic generation
    */
    @Column(name = "is_read")
    private Boolean isRead;

    /**
    * Code generator automatic generation
    */
    public Long getMsgId() {
       return msgId;
    }

    /**
    * Code generator automatic generation
    */
    public void setMsgId(Long msgId) {
       this.msgId = msgId;
    }
    /**
    * Code generator automatic generation
    */
    public Long getFromId() {
       return fromId;
    }

    /**
    * Code generator automatic generation
    */
    public void setFromId(Long fromId) {
       this.fromId = fromId;
    }
    /**
    * Code generator automatic generation
    */
    public Long getToId() {
       return toId;
    }

    /**
    * Code generator automatic generation
    */
    public void setToId(Long toId) {
       this.toId = toId;
    }
    /**
    * Code generator automatic generation
    */
    public String getContent() {
       return content;
    }

    /**
    * Code generator automatic generation
    */
    public void setContent(String content) {
       this.content = content;
    }
    /**
    * Code generator automatic generation
    */
    public Boolean getIsRead() {
       return isRead;
    }

    /**
    * Code generator automatic generation
    */
    public void setIsRead(Boolean isRead) {
       this.isRead = isRead;
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