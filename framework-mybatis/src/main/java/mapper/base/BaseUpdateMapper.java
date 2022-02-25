package mapper.base;

import com.study.core.po.BasePo;
import mapper.update.UpdateMapper;

/**
 * ClassName: BaseUpdateMapper
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午9:59
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface BaseUpdateMapper<T extends BasePo> extends UpdateMapper<T> {
}
