package com.study.test.service.impl;

import com.study.test.domain.entity.Messages;
import com.study.test.repository.MessagesMapper;
import com.study.test.service.api.MessagesService;
import org.springframework.stereotype.Service;
import service.impl.BaseService;

/**
* @Description: messages
* @author luohongxiao
* @date 2022年02月28日
*/
@Service
public class MessagesServiceImpl extends BaseService<Messages,MessagesMapper> implements MessagesService {

}