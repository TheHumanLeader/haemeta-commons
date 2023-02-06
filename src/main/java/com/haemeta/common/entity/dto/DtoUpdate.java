package com.haemeta.common.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

public class DtoUpdate {

    protected Long updateUser;
    @TableField(exist = false)
    protected String updateUserName;
    protected Date updateTime;
    @TableField(exist = false)
    protected String updateTimeChar;

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
