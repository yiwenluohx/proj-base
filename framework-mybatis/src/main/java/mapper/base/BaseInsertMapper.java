package mapper.base;

import com.study.core.po.BasePo;
import mapper.insert.InsertMapper;

/**
 * ClassName: BaseInsertMapper
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午9:58
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface BaseInsertMapper<T extends BasePo> extends InsertMapper<T> {
}