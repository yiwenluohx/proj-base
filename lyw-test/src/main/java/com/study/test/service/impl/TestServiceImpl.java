package com.study.test.service.impl;

import com.study.test.domain.entity.Test;
import com.study.test.repository.TestMapper;
import com.study.test.service.api.TestService;
import org.springframework.stereotype.Service;
import service.impl.BaseService;

/**
* @Description: test
* @author luohongxiao
* @date 2022年02月28日
*/
@Service
public class TestServiceImpl extends BaseService<Test,TestMapper> implements TestService {

}