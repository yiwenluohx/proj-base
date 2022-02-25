package mapper.base;

import com.study.core.po.BasePo;
import mapper.select.CountMapper;
import mapper.select.SelectOneMapper;

import java.util.List;

/**
 * ClassName: BaseSelectMapper
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午9:59
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface BaseSelectMapper<T extends BasePo>
        extends
        CountMapper<T>,
        SelectOneMapper<T> {
    /**
     * 查询一个列表
     *
     * @param entity 实体对象
     * @return
     */
    List<T> selectList(T entity);

    /**
     * 根据主键数组查询对象实体
     *
     * @param list 主键数数组
     * @return
     */
    List<T> selectListByIds(List<Long> list);
}