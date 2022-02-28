package com.study.test.service.impl;

import com.study.test.domain.entity.Sysuser;
import com.study.test.repository.SysuserMapper;
import com.study.test.service.api.SysuserService;
import org.springframework.stereotype.Service;
import service.impl.BaseService;

/**
* @Description: sysuser
* @author luohongxiao
* @date 2022年02月28日
*/
@Service("sysuserService")
public class SysuserServiceImpl extends BaseService<Sysuser,SysuserMapper> implements SysuserService {

}