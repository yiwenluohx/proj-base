package com.study.core.utils;

import com.study.core.exception.ServiceException;
import com.study.core.po.BasePo;

import javax.persistence.Id;
import java.lang.reflect.Field;

/**
 * ClassName: ReflectUtils
 * Description: Po工具类
 * @Author: luohx
 * Date: 2022/2/22 下午3:57
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0              Po工具类
 */
public class ReflectUtils {

    /**
     * 给实体Po设置主键值
     *
     * @param entity
     * @param primaryKey
     * @param <T>
     */
    public static <T> void SetPrimaryKey(T entity, Long primaryKey) {
        SetVal(entity, primaryKey, false);
    }

    /**
     * 组合逻辑删除实体
     *
     * @param entity
     * @param primaryKey
     * @param <T>
     */
    public static <T> void SetDelVal(T entity, Long primaryKey) {
        SetVal(entity, primaryKey, true);
    }

    /**
     * 给实体Po设置值
     *
     * @param entity
     * @param primaryKey
     * @param <T>
     */
    public static <T> void SetVal(T entity, Long primaryKey, boolean isDel) {
        if (primaryKey == null) {
            throw new ServiceException("SetPrimaryKey->primaryKey不能为null");
        }
        if (entity instanceof BasePo) {
            Class<?> cls = entity.getClass();
            Field[] fields = cls.getDeclaredFields();
            Id annotation = null;
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                annotation = field.getAnnotation(Id.class);
                if (annotation != null) {
                    try {
                        field.set(entity, primaryKey);
                    } catch (Exception e) {
                        throw new ServiceException("设置主键值报错");
                    }
                }
                if (isDel && field.getName().equals("deleteFlag")) {
                    try {
                        field.set(entity, 1);
                    } catch (Exception e) {
                        throw new ServiceException("设置deleteFlag报错");
                    }
                }
            }
        } else {
            throw new ServiceException("暂不支持的实体类型");
        }
    }

    /**
     * 根据PO中添加的@Id返回主键值，如有多个的只返回第一个
     *
     * @param entity
     * @param effectRows
     * @param <T>
     * @return
     */
    public static <T> Long RetId(T entity, int effectRows) {
        if (effectRows <= 0) {
            throw new ServiceException("插入数据异常");
        }
        if (entity instanceof BasePo) {
            Class<?> cls = entity.getClass();
            Field[] fields = cls.getDeclaredFields();
            Id annotation = null;
            for (Field field : fields) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                annotation = field.getAnnotation(Id.class);
                if (annotation != null) {
                    try {
                        return Long.parseLong(field.get(entity).toString());
                    } catch (Exception e) {
                        throw new ServiceException("获取主键值报错");
                    }
                }
            }
            if (null == annotation) {
                throw new ServiceException("实体未设置主键");
            }
        }
        throw new ServiceException("暂不支持的实体类型");
    }
}