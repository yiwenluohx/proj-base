package service.api.select;

import com.study.core.po.BasePo;

/**
 * ClassName: SelectService
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:31
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public interface SelectService<T extends BasePo>
        extends CountService<T>,SelectOneService<T>,SelectListService<T>,SelectListAndCountService<T>{
}