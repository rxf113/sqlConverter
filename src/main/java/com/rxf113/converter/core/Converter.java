package com.rxf113.converter.core;

/**
 * @author rxf113
 */
public interface Converter<E> {

    E sqlConvertObj(String sqlStr);

}
