package com.study.test.convert;

import com.study.test.domain.entity.Sysuser;
import com.study.test.domain.param.UserParam;
import org.mapstruct.Mapper;

/**
 * ClassName: SysuserAppConverter
 * Description:
 *
 * @Author: luohx
 * Date: 2022/4/15 下午4:57
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
@Mapper(componentModel = "spring")
public interface SysuserAppConverter {

    /**
     * 转换
     *
     * @param param
     * @param userId
     * @return
     */
    Sysuser toSysuser(UserParam param, Long userId);

}
