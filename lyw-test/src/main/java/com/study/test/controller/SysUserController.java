package com.study.test.controller;

import com.study.core.ro.RdfaResult;
import com.study.core.ro.Resp;
import com.study.test.domain.param.UserParam;
import com.study.test.service.impl.SysuserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: SysUserController
 * Description:
 *
 * @Author: luohx
 * Date: 2022/2/28 下午2:26
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
@Validated
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysuserServiceImpl userService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public RdfaResult insert(@Validated @RequestBody UserParam param) {
        userService.addUser(param);
        return Resp.ok();
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public RdfaResult getInfo() {
        return Resp.ok("llll");
    }

}
