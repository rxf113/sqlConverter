package com.rxf113.converter.core.enums;

/**
 * @author rxf113
 */

public enum ControlType {

    /*字段控制 + 条件添加*/
    ALL(3),

    /*字段控制*/
    FIELDS_CONTROL(2),

    /*条件添加*/
    ADD_CONDITION(1);

    private int val;

    ControlType(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }
}
