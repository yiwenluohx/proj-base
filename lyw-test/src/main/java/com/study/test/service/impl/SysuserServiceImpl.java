package com.study.test.service.impl;

import com.study.core.exception.ServiceException;
import com.study.test.domain.entity.Sysuser;
import com.study.test.domain.entity.Test;
import com.study.test.domain.param.UserParam;
import com.study.test.handler.SnowflakeHandler;
import com.study.test.repository.SysuserMapper;
import com.study.test.repository.TestMapper;
import com.study.test.service.api.SysuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.impl.BaseService;

import javax.annotation.Resource;

/**
 * @author luohongxiao
 * @Description: sysuser
 * @date 2022年02月28日
 */
@Service
public class SysuserServiceImpl extends BaseService<Sysuser, SysuserMapper> implements SysuserService {

    @Autowired
    private SnowflakeHandler snowflakeHandler;

    @Resource
    private TestMapper testMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addUser(UserParam param) {
        Sysuser user = new Sysuser();
        user.setUserId(snowflakeHandler.nextId());
        user.setAccount(param.getAccount());
        user.setEmail(param.getEmail());
        user.setMobile(param.getMobile());
        user.setIsEnable(false);
        this.getMapper().insert(user);
        this.insertNum(param.getNum());
    }

    private void insertNum(Integer num) {
        Test test = new Test();
        test.setTestId(snowflakeHandler.nextId());
        test.setNum(num);
        testMapper.insert(test);
    }
}