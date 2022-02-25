package service;

import com.google.common.collect.Lists;
import com.study.core.po.BasePo;
import service.api.delete.DeleteService;
import service.api.insert.InsertService;
import service.api.select.SelectListAndCountService;
import service.api.select.SelectService;
import service.api.update.UpdateService;

import java.util.List;

/**
 * ClassName: IBaseService
 * Description: 提供此类给xml的namespace为mapper路径使用，新版本
 * @Author: luohx
 * Date: 2022/2/25 上午10:13
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0      提供此类给xml的namespace为mapper路径使用，新版本
 */
public interface IBaseService<T extends BasePo>
        extends InsertService<T>, DeleteService<T>, UpdateService<T>, SelectService<T>, SelectListAndCountService<T> {

    /**
     * 根据主键查询实体
     * @param primaryKey
     * @return
     */
    T selectById(Long primaryKey);
    /**
     * 根据主键删除数据
     * @param primaryKey
     * @return
     */
    @Deprecated
    int deleteById(Long primaryKey);
    /**
     * 根据主键批量删除数据
     * @param primaryKeys
     * @return
     */
    @Deprecated
    default int deleteById(Long[] primaryKeys){
        return this.deleteById(Lists.newArrayList(primaryKeys));
    }
    /**
     * 根据主键批量删除数据
     * @param primaryKeys
     * @return
     */
    @Deprecated
    int deleteById(List<Long> primaryKeys);

    /**
     * 根据主键逻辑删除数据 del_Flag = true
     * @param primaryKey
     * @return
     */
    int deleteLogicById(Long primaryKey);
    /**
     * 根据主键批量逻辑删除数据 del_Flag = true
     * @param primaryKeys
     * @return
     */
    int deleteLogicById(List<Long> primaryKeys);
    /**
     * 根据主键批量逻辑删除数据 del_Flag = true
     * @param primaryKeys
     * @return
     */
    default int deleteLogicById(Long[] primaryKeys){
        return this.deleteLogicById(Lists.newArrayList(primaryKeys));
    }
}