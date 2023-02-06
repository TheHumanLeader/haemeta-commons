package com.haemeta.common.entity;

public class TestEntityVo extends TestEntity{
    private TestInfoEntity infoEntity;

    public TestEntityVo() {
    }

    public TestEntityVo(String msg) {
        super();
        super.setMsg(msg);
    }

    public TestEntityVo(Integer code) {
        super();
        super.setCode(code);
    }

    public TestEntityVo(String msg, Integer code) {
        super();
        super.setMsg(msg);
        super.setCode(code);
    }

    public TestInfoEntity getInfoEntity() {
        return infoEntity;
    }
    public void setInfoEntity(TestInfoEntity infoEntity) {
        this.infoEntity = infoEntity;
    }

}
