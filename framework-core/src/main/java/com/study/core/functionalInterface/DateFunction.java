package com.study.core.functionalInterface;

/**
 * ClassName: DateFunction
 * Description: 获取需要的年月日时分秒
 * @Author: luohx
 * Date: 2022/2/23 上午9:30
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0            获取需要的年月日时分秒
 */
@FunctionalInterface
public interface DateFunction<Y, MM, D, H, M, S, R> {
    /**
     * 返回需要的信息
     * @param y  年
     * @param mm 月
     * @param d  日
     * @param h  时
     * @param m  分
     * @param s  秒
     * @return
     */
    R apply(Y y, MM mm, D d, H h, M m, S s);
}
