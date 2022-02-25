package service.impl.base;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.study.core.pager.QueryResult;
import com.study.core.po.BasePo;
import com.study.core.utils.ReflectUtils;
import lombok.SneakyThrows;
import mapper.Mapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import service.IBaseService;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * ClassName: AbstractService
 * Description:
 * @Author: luohx
 * Date: 2022/2/25 上午10:35
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public abstract class AbstractService<T extends BasePo,M extends Mapper> implements IBaseService<T> {

    @Autowired
    private M mapper;

    public M getMapper() {
        return this.mapper;
    }

    @SneakyThrows
    protected T getInstance() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class clazz = (Class<T>) type.getActualTypeArguments()[0];
        return  (T) clazz.newInstance();
    }

    @Override
    public Long insert(T entity) {
        int effectRows = this.mapper.insert(entity);
        return ReflectUtils.RetId(entity, effectRows);
    }

    @Override
    public Integer insertList(List<T> list) {
        return this.mapper.insertList(list);
    }

    @Override
    public Integer delete(T entity) {
        return this.mapper.delete(entity);
    }

    @Override
    public Integer deleteList(List<T> list) {
        return this.mapper.deleteList(list);
    }

    @Override
    public Integer update(T entity) {
        return this.mapper.update(entity);
    }

    @Override
    public Integer updateList(List<T> list) {
        return this.mapper.updateList(list);
    }

    @Override
    public Integer count(T entity) {
        return this.mapper.count(entity);
    }

    @Override
    public T selectOne(T entity) {
        return (T) this.mapper.selectOne(entity);
    }

    @Override
    public List<T> selectList(T entity) {
        return this.mapper.selectList(entity);
    }

    @Override
    public List<T> selectList(List<Long> list) {
        return this.mapper.selectListByIds(list);
    }

    @Override
    public List<T> selectList(T entity, String orderBy) {
        if(!StringUtils.isEmpty(orderBy))
        {
            PageHelper.orderBy(orderBy);
        }
        return this.mapper.selectList(entity);
    }

    @Override
    public List<T> selectList(T entity, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize, false);
        return this.mapper.selectList(entity);
    }

    @Override
    public List<T> selectList(T entity, int pageNum, int pageSize, String orderBy) {
        PageHelper.startPage(pageNum, pageSize, false);
        if(!StringUtils.isEmpty(orderBy))
        {
            PageHelper.orderBy(orderBy);
        }
        return this.mapper.selectList(entity);
    }

    @Override
    public QueryResult<T> selectListAndCount(T entity, Integer pageNum, Integer pageSize, String orderBy) {
        Page<?> page = PageHelper.startPage(pageNum, pageSize);
        if(!StringUtils.isEmpty(orderBy))
        {
            PageHelper.orderBy(orderBy);
        }
        List<T> list = (List<T>) this.mapper.selectList(entity);
        QueryResult<T> result = new QueryResult<>(list, page.getTotal());
        return result;
    }
}