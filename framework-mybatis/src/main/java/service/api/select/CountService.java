package service.api.select;

import com.study.core.po.BasePo;

/**
 * ClassName: CountService
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:29
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface CountService<T extends BasePo> {
    /**
     * 查询符合条件的总条数
     * @param entity
     * @return
     */
    Integer count(T entity);
}