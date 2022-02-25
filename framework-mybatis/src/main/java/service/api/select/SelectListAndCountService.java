package service.api.select;

import com.study.core.pager.QueryResult;
import com.study.core.po.BasePo;

/**
 * ClassName: SelectListAndCountService
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:29
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface SelectListAndCountService<T extends BasePo> {
    /**
     * 此方法返回数据结果集和count
     *
     * @param entity           参数对象
     * @param pageNum          页码
     * @param pageSize         每页记录数
     * @param orderBy          排序字段(例：'id desc')
     * @return
     */
    QueryResult<T> selectListAndCount(T entity, Integer pageNum, Integer pageSize, String orderBy);
}