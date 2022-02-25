package mapper.insert;

import com.study.core.po.BasePo;

import java.util.List;

/**
 * ClassName: InsertMapper
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:09
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface InsertMapper<T extends BasePo> {
    /**
     * 插入一条记录
     *
     * @param entity
     * @return @
     */
    Integer insert(T entity);
    /**
     * 批量插入数据
     *
     * @param list
     * @return @
     */
    Integer insertList(List<T> list);
}