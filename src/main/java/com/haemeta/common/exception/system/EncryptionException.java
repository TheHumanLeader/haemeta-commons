package com.haemeta.common.exception.system;

import com.haemeta.common.enums.ResultStatusCodes;
import com.haemeta.common.exception.HaemetaException;

/**
 * @author 佘纪凡
 * 系统异常：加密异常
 */
public class EncryptionException extends HaemetaException {


    public EncryptionException(Exception e){
        super(e);
        this.code = ResultStatusCodes.SYSTEM_CIPHERTEXT_ERROR;
        this.msg = this.code.getMsg();
    }

    public EncryptionException(String msg){
        super(msg);
        this.code = ResultStatusCodes.SYSTEM_CIPHERTEXT_ERROR;
        this.msg = msg;
    }

}
