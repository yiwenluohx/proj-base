package com.study.test.domain.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * ClassName: UserParam
 * Description: 添加用户入参
 *
 * @Author: luohx
 * Date: 2022/3/1 下午3:43
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           添加用户入参
 */
@Data
public class UserParam implements Serializable {
    @NotBlank(message = "账号不能为空")
    private String account;
    @NotBlank(message = "手机号不能为空")
    private String mobile;
    private String email;
    private Boolean isEnable;
    @NotNull(message = "数量不能为空")
    private Integer num;
}
