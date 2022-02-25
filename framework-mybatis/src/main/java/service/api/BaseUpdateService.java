package service.api;

import com.study.core.po.BasePo;
import service.api.update.UpdateService;

/**
 * ClassName: BaseUpdateService
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:21
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface BaseUpdateService<T extends BasePo> extends UpdateService<T> {
}