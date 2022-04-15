package com.study.test.handler;

import com.study.snowflake.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ClassName: SnowflakeHandler
 * Description: 雪花算法
 *
 * @Author: luohx
 * Date: 2022/3/3 下午5:51
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0              雪花算法
 */
@Component
public class SnowflakeHandler {

    @Resource
    private SnowflakeGenerator snowflakeGenerator;

    /**
     * 默认本地雪花算法，方便将来扩展使用
     *
     * @return
     */
    public long nextId() {
        return snowflakeId();
    }

    /**
     * 本地唯一雪花ID
     *
     * @return
     */
    public long snowflakeId() {
        return snowflakeGenerator.nextId();
    }

}
