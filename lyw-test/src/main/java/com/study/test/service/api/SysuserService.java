package com.study.test.service.api;

import com.study.test.domain.param.UserParam;
import service.IBaseService;
import com.study.test.domain.entity.Sysuser;

/**
 * @author luohongxiao
 * @Description: sysuser
 * @date 2022年02月28日
 */
public interface SysuserService extends IBaseService<Sysuser> {

    /**
     * 添加系统用户
     *
     * @param param
     */
    void addUser(UserParam param);

}