package com.study.core.pager;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * ClassName: PagerModel
 * Description: 分页
 *
 * @Author: luohx
 * Date: 2022/2/22 下午3:40
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
@Data
public class PagerModel {
    @Min(value = 1, message = "当前页不能小于1")
    @NotNull(message = "页码不能为空")
    private Integer pageNum = 1;

    @Min(value = 1, message = "页码大小不能小于1")
    @NotNull(message = "每页条数不能为空")
    private Integer pageSize = 20;
}
