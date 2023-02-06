package com.haemeta.common.exception.system;

import com.haemeta.common.enums.ResultStatusCodes;
import com.haemeta.common.exception.HaemetaException;

/**
 * jack son mapper 写值时触发的错误
 */
public class JsonMapperWriteValueException extends HaemetaException {

    public JsonMapperWriteValueException() {
        super(ResultStatusCodes.SYSTEM_ERROR);
    }

    public JsonMapperWriteValueException(String msg) {
        super(ResultStatusCodes.SYSTEM_ERROR,msg);
    }

    public JsonMapperWriteValueException(ResultStatusCodes code) {
        super(code);
    }

    public JsonMapperWriteValueException(ResultStatusCodes code, String msg) {
        super(code, msg);
    }
}
