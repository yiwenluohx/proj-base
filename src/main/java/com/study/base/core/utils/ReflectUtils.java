package com.study.base.core.utils;

import com.study.base.core.exception.ServiceException;
import com.study.base.core.po.BasePo;

import javax.persistence.Id;
import java.lang.reflect.Field;

/**
 * ClassName: ReflectUtils
 * Description:
 * @Author: luohx
 * Date: 2022/2/22 下午3:57
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public class ReflectUtils {
    public ReflectUtils() {
    }

    public static <T> void SetPrimaryKey(T entity, Long primaryKey) {
        SetVal(entity, primaryKey, false);
    }

    public static <T> void SetDelVal(T entity, Long primaryKey) {
        SetVal(entity, primaryKey, true);
    }

    public static <T> void SetVal(T entity, Long primaryKey, boolean isDel) {
        if (primaryKey == null) {
            throw new ServiceException("SetPrimaryKey->primaryKey不能为null");
        } else if (entity instanceof BasePo) {
            Class<?> cls = entity.getClass();
            Field[] fields = cls.getDeclaredFields();
            Id annotation = null;
            Field[] var6 = fields;
            int var7 = fields.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                Field field = var6[var8];
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }

                annotation = (Id)field.getAnnotation(Id.class);
                if (annotation != null) {
                    try {
                        field.set(entity, primaryKey);
                    } catch (Exception var11) {
                        throw new ServiceException("设置主键值报错");
                    }
                }

                if (isDel && field.getName().equals("deleteFlag")) {
                    try {
                        field.set(entity, 1);
                    } catch (Exception var12) {
                        throw new ServiceException("设置deleteFlag报错");
                    }
                }
            }

        } else {
            throw new ServiceException("暂不支持的实体类型");
        }
    }

    public static <T> Long RetId(T entity, int effectRows) {
        if (effectRows <= 0) {
            throw new ServiceException("插入数据异常");
        } else {
            if (entity instanceof BasePo) {
                Class<?> cls = entity.getClass();
                Field[] fields = cls.getDeclaredFields();
                Id annotation = null;
                Field[] var5 = fields;
                int var6 = fields.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    Field field = var5[var7];
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }

                    annotation = (Id)field.getAnnotation(Id.class);
                    if (annotation != null) {
                        try {
                            return Long.parseLong(field.get(entity).toString());
                        } catch (Exception var10) {
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
}