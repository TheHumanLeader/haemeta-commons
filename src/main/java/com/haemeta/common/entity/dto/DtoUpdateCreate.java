package com.haemeta.common.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

public class DtoUpdateCreate {

    protected Long createUser;
    @TableField(exist = false)
    protected String createUserName;
    protected Date createTime;
    @TableField(exist = false)
    protected String createTimeChar;

    protected Long updateUser;
    @TableField(exist = false)
    protected String updateUserName;
    protected Date updateTime;
    @TableField(exist = false)
    protected String updateTimeChar;

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

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTimeChar() {
        return updateTimeChar;
    }

    public void setUpdateTimeChar(String updateTimeChar) {
        this.updateTimeChar = updateTimeChar;
    }
}
