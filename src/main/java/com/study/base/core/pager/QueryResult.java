package com.study.base.core.pager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * ClassName: QueryResult
 * Description: 分页查询结果集
 * Author: luohx
 * Date: 2022/2/23 下午4:15
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           分页查询结果集
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryResult<T> implements Serializable {

    private static final long serialVersionUID = 461900815434592315L;

    private List<T> list;

    private Long total = 0L;

}
