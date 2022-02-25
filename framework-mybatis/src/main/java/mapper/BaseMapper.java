package mapper;

import com.study.core.po.BasePo;
import mapper.base.BaseDeleteMapper;
import mapper.base.BaseInsertMapper;
import mapper.base.BaseSelectMapper;
import mapper.base.BaseUpdateMapper;

/**
 * ClassName: BaseMapper
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午9:54
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface BaseMapper<T extends BasePo>
        extends
        BaseDeleteMapper<T>,
        BaseInsertMapper<T>,
        BaseSelectMapper<T>,
        BaseUpdateMapper<T> {

}