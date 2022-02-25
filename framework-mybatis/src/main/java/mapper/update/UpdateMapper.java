package mapper.update;

import com.study.core.po.BasePo;

import java.util.List;

/**
 * ClassName: UpdateMapper
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:12
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface UpdateMapper<T extends BasePo> {
    /**
     * 更新对象(此方法只根据主键ID进行更新)
     *
     * @param entity
     * @return @
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