package service.impl;

import com.google.common.collect.Lists;
import com.study.core.po.BasePo;
import com.study.core.utils.ReflectUtils;
import mapper.Mapper;
import org.springframework.transaction.annotation.Transactional;
import service.impl.base.AbstractService;

import java.util.List;

/**
 * ClassName: BaseService
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:33
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public class BaseService<T extends BasePo, M extends Mapper<T>> extends AbstractService<T, M> {

    @Override
    public T selectById(Long primaryKey) {
        T t = super.getInstance();
        ReflectUtils.SetPrimaryKey(t, primaryKey);
        return super.selectOne(t);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(Long primaryKey) {
        T t = super.getInstance();
        ReflectUtils.SetPrimaryKey(t, primaryKey);
        return super.delete(t);
    }

    @Override
    public int deleteById(List<Long> primaryKeys) {
        List<T> list = Lists.newArrayList();
        primaryKeys.forEach(x->{
            T t = super.getInstance();
            ReflectUtils.SetPrimaryKey(t, x);
            list.add(t);
        });
        return deleteList(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteLogicById(Long primaryKey) {
        T t = super.getInstance();
        ReflectUtils.SetDelVal(t, primaryKey);
        return this.update(t);
    }

    @Override
    public int deleteLogicById(List<Long> primaryKeys) {
        List<T> list = Lists.newArrayList();
        primaryKeys.forEach(x->{
            T t = super.getInstance();
            ReflectUtils.SetDelVal(t, x);
            list.add(t);
        });
        return updateList(list);
    }
}