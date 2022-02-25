package service.api.insert;

import com.study.core.po.BasePo;

import java.util.List;

/**
 * ClassName: InsertService
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:27
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface InsertService<T extends BasePo> {
    /**
     * 插入数据
     *
     * @param entity
     * @return
     */
    Long insert(T entity);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    Integer insertList(List<T> list);
}