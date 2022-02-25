package mapper.select;

import com.study.core.po.BasePo;

/**
 * ClassName: CountMapper
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:10
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface CountMapper<T extends BasePo> {
    /**
     * 查询统计 根据对象条件统计
     *
     * @param entity
     * @return
     */
    Integer count(T entity);
}