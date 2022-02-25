package mapper.base;

import com.study.core.po.BasePo;
import mapper.delete.DeleteMapper;

/**
 * ClassName: BaseDeleteMapper
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午9:58
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface BaseDeleteMapper<T extends BasePo> extends DeleteMapper<T> {

}
