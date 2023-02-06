package com.haemeta.common.interfaces;

import com.haemeta.common.enums.ResultStatusCodes;

public interface HaemetaExceptionInterface<T> {

    public Integer getCodeVal();
    public ResultStatusCodes getCode();
    public String getMsg();

    public T msg(String msg);

    public T code(ResultStatusCodes code);
}
