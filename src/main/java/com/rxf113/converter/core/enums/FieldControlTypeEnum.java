package com.rxf113.converter.core.enums;

public enum FieldControlTypeEnum {

    /*包含*/
    INCLUDE(2),
    /*排除*/
    EXCLUDE(1);

    private int val;

    FieldControlTypeEnum(int val) {
        this.val = val;
    }
    public int getVal() {
        return val;
    }
}
