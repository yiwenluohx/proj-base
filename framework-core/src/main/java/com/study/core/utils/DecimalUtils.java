package com.study.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: DecimalUtils
 * Description: 精度计算工具
 * @Author: luohx
 * Date: 2022/2/23 下午4:40
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0           精度计算工具
 */
public abstract class DecimalUtils {

    /**
     * 向“最接近的”数字舍入  四舍五入
     */
    private static final int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;

    /**
     * 默认保留两位小数
     */
    private static final int SCALE = 2;


    /**
     * 保留两位数四舍五入
     *
     * @param bd
     * @return
     */
    public static BigDecimal round(BigDecimal bd) {
        return bd.setScale(SCALE, ROUND_HALF_UP);
    }

    /**
     * 按指定小数位四舍五入
     *
     * @param bd
     * @param scale
     * @return
     */
    public static BigDecimal round(BigDecimal bd, int scale) {
        return bd.setScale(scale, ROUND_HALF_UP);
    }


    /**
     * 相加运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal add(BigDecimal d1, BigDecimal d2) {
        return d1.add(d2);
    }

    /**
     * 计算累加值
     *
     * @param values
     * @return
     */
    public static BigDecimal sum(List<BigDecimal> values) {
        BigDecimal bd = BigDecimal.ZERO;
        if (values == null) {
            return bd;
        }

        for (BigDecimal temp : values) {
            if (temp != null) {
                bd = bd.add(temp);
            }
        }
        return bd;
    }

    /**
     * 字符串计算总和
     *
     * @param strList
     * @return
     */
    public static String sumString(List<String> strList) {
        List<BigDecimal> values = new ArrayList<>();
        for (String str : strList) {
            if (StringUtils.isBlank(str)) {
                continue;
            }
            BigDecimal bd = new BigDecimal(str);
            values.add(bd);
        }
        return sum(values).toString();
    }


    /**
     * 相减运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal sub(BigDecimal d1, BigDecimal d2) {
        return d1.subtract(d2);
    }

    /**
     * 乘法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal mul(BigDecimal d1, BigDecimal d2) {
        return d1.multiply(d2);
    }


    /**
     * 除法运算
     *
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal div(BigDecimal d1, BigDecimal d2, int scale) {
        return d1.divide(d2, scale, ROUND_HALF_UP);
    }

}