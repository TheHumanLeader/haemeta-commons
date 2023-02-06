package com.haemeta.common.helper.logic;

import com.haemeta.common.exception.response.ResMissingParameterException;
import com.haemeta.common.lambda.FunctionPlus;

import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class LogicHelper<Data,Source> {

    protected Data data;
    protected Function<? super Source,Data> dataApply;

}
