package service.api.select;

import com.study.core.po.BasePo;

import java.util.List;

/**
 * ClassName: SelectListService
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:30
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface SelectListService<T extends BasePo> {
    /**
     * 根据条件查询列表
     *
     * @param entity
     * @return
     */
    List<T> selectList(T entity);

    /**
     * 根据主键数组，查询列表
     * @param list
     * @return
     */
    List<T> selectList(List<Long> list);
    /**
     * 根据条件查询列表
     *
     * @param entity
     * @param orderBy
     * @return
     */
    List<T> selectList(T entity, String orderBy);
    /**
     * 根据条件查询列表并分页
     *
     * @param entity
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<T> selectList(T entity, int pageNum, int pageSize);
    /**
     * 根据条件查询列表并分页
     *
     * @param entity
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    List<T> selectList(T entity, int pageNum, int pageSize, String orderBy);
}