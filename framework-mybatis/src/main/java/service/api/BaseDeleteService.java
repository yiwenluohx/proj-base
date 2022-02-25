package service.api;

import com.study.core.po.BasePo;
import service.api.delete.DeleteService;

/**
 * ClassName: BaseDeleteService
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:15
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface BaseDeleteService<T extends BasePo> extends DeleteService<T> {
}
