package mapper.delete;

import com.study.core.po.BasePo;

import java.util.List;

/**
 * ClassName: DeleteMapper
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:08
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface DeleteMapper<T extends BasePo> {
    /**
     * 删除
     *
     * @param entity
     * @return @
     */
    Integer delete(T entity);
    /**
     * 批量删除
     *
     * @param list
     * @return
     */
    Integer deleteList(List<T> list);
}