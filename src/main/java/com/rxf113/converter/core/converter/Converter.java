package com.rxf113.converter.core.converter;

/**
 * @author rxf113
 *
 */
public interface Converter {
    /**
     * 转换 原始sql->目标sql
     *
     * @param sql 入参sql
     * @return 转换后的sql
     */
    String convert(String sql);
}
