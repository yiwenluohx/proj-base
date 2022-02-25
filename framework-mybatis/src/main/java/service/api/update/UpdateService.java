package service.api.update;

import com.study.core.po.BasePo;

import java.util.List;

/**
 * ClassName: UpdateService
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:28
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface UpdateService<T extends BasePo> {
    /**
     * 更新数据
     *
     * @param entity
     * @return
     */
    Integer update(T entity);
    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    Integer updateList(List<T> list);
}