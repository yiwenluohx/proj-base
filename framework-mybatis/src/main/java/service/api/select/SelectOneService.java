package service.api.select;

import com.study.core.po.BasePo;

/**
 * ClassName: SelectOneService
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:31
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface SelectOneService<T extends BasePo> {
    /**
     * 根据查询条件查询一条数据
     *
     * @param entity
     * @return
     */
    T selectOne(T entity);
}