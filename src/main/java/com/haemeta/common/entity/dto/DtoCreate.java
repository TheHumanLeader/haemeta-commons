package com.haemeta.common.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

public class DtoCreate {

    protected Long createUser;
    @TableField(exist = false)
    protected String createUserName;
    protected Date createTime;
    @TableField(exist = false)
    protected String createTimeChar;

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeChar() {
        return createTimeChar;
    }

    public void setCreateTimeChar(String createTimeChar) {
        this.createTimeChar = createTimeChar;
    }
}
