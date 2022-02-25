package service.api.delete;

import com.study.core.po.BasePo;

import java.util.List;

/**
 * ClassName: DeleteService
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:27
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface DeleteService<T extends BasePo>{
    /**
     * 删除
     *
     * @param entity
     * @return
     */
    @Deprecated
    Integer delete(T entity);

    /**
     * 批量删除
     *
     * @param list
     * @return
     */
    @Deprecated
    Integer deleteList(List<T> list);
}