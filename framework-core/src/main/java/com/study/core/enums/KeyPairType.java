package com.study.core.enums;

/**
 * ClassName: KeyPairType
 * Description:
 * @Author: luohx
 * Date: 2022/2/23 下午4:25
 * History:
 * <author>          <time>          <version>          <desc>
 * luohx            修改时间           1.0
 */
public enum KeyPairType {

    RSA2048("RSA", 2048),
    EC256("EC", 256);

    private String algorithm;
    private int keysize;

    KeyPairType(String algorithm, int keysize) {
        this.algorithm = algorithm;
        this.keysize = keysize;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public int getKeysize() {
        return keysize;
    }

}