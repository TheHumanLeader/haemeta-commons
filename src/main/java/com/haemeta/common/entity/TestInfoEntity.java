package com.haemeta.common.entity;

public class TestInfoEntity {
    private Integer code;
    private String info;
    public TestInfoEntity(Integer code){this.code=code;}
    public TestInfoEntity(String info){this.info=info;}
    public TestInfoEntity(Integer code,String info){this.code=code;this.info=info;}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
