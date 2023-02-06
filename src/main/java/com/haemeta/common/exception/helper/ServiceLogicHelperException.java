package com.haemeta.common.exception.helper;

import com.haemeta.common.enums.ResultStatusCodes;
import com.haemeta.common.exception.HaemetaException;

public class ServiceLogicHelperException extends HaemetaException {
    public ServiceLogicHelperException(String nullParam) {
        super(
                ResultStatusCodes.SYSTEM_NULL_DATA,
                nullParam + " ,参数为空,请检查配置。"
        );
    }
}
