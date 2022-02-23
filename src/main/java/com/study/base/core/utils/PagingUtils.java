package com.study.base.core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: PagingUtils
 * Description: 分页工具类
 * Author: luohx
 * Date: 2022/2/23 下午4:48
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           分页工具类
 */
public abstract class PagingUtils {


    /**
     * 获取分页偏移量
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static int getOffset(int pageNum, int pageSize) {
        if (pageNum <= 0) {
            return 0;
        }

        return (pageNum - 1) * pageSize;
    }

    /**
     * 内存分页
     *
     * @param <T>
     * @param list
     * @param pageNum
     * @return
     */
    public static <T> List<T> paging(List<T> list, int pageNum, int pageSize) {
        if (list == null || list.size() == 0) {
            return list;
        }

        List<T> result = new ArrayList<T>();
        List<List<T>> partitionList = ListUtils.partition(list, pageSize);
        if (pageNum > partitionList.size()) {
            return result;
        }
        return partitionList.get(pageNum - 1);
    }
}