package service.api;

import com.study.core.po.BasePo;
import service.api.insert.InsertService;

/**
 * ClassName: BaseInsertService
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:16
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface BaseInsertService<T extends BasePo> extends InsertService<T> {
}