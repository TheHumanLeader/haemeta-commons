package com.haemeta.common.entity;

public class TestEntity {
    private String msg;
    private Integer code;

    public TestEntity() {
    }

    public TestEntity(String msg) {
        this.msg = msg;
    }

    public TestEntity(Integer code) {
        this.code = code;
    }

    public TestEntity(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return "TestEntity{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }
}
