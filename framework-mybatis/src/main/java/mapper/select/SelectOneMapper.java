package mapper.select;

import com.study.core.po.BasePo;

/**
 * ClassName: SelectOneMapper
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:11
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface SelectOneMapper<T extends BasePo> {
    /**
     * 根据 T 查询数据
     *
     * @param entity
     * @return
     */
    T selectOne(T entity);
}