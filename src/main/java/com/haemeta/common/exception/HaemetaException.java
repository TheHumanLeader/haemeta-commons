package com.haemeta.common.exception;

import com.haemeta.common.enums.ResultStatusCodes;
import com.haemeta.common.interfaces.HaemetaExceptionInterface;

public class HaemetaException extends Throwable implements HaemetaExceptionInterface {

    protected ResultStatusCodes code;
    protected String msg;

    public HaemetaException(){
        this.code = ResultStatusCodes.SYSTEM_ERROR;
        this.msg = this.code.getMsg();
    }

    public HaemetaException(Throwable e){
        super(e);
        this.code = ResultStatusCodes.SYSTEM_ERROR;
        this.msg = this.code.getMsg();
    }

    public HaemetaException(ResultStatusCodes code){
        this.code = code;
        this.msg = code.getMsg();
    }

    public HaemetaException(ResultStatusCodes code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public HaemetaException(String msg){
        this.msg = msg;
    }

    public Integer getCodeVal() {
        return code.getCode();
    }
    public ResultStatusCodes getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public HaemetaException code(ResultStatusCodes code){
        this.code = code;
        return this;
    }

    public HaemetaException msg(String msg){
        this.msg = msg;
        return this;
    }

}
