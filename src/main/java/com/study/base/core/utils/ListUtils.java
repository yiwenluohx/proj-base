package com.study.base.core.utils;

import java.util.AbstractList;
import java.util.List;

/**
 * ClassName: ListUtils
 * Description: List工具类
 * @Author: luohx
 * Date: 2022/2/23 下午4:47
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           List工具类
 */
public abstract class ListUtils {

    /**
     * List 分割
     *
     * @param <T>
     * @param list
     * @param pageSize
     * @return
     * @author caowuchao
     * @since 2021年1月29日
     */
    public static <T> List<List<T>> partition(final List<T> list, final int pageSize) {
        return new Partition<T>(list, pageSize);
    }

    private static class Partition<T> extends AbstractList<List<T>> {
        private final List<T> list;
        private final int pageSize;

        private Partition(final List<T> list, final int pageSize) {
            this.list = list;
            this.pageSize = pageSize;
        }

        @Override
        public List<T> get(final int index) {
            final int start = index * pageSize;
            final int end = Math.min(start + pageSize, list.size());
            return list.subList(start, end);
        }

        @Override
        public int size() {
            return (list.size() + pageSize - 1) / pageSize;
        }

        @Override
        public boolean isEmpty() {
            return list.isEmpty();
        }
    }

}