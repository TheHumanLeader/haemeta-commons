package com.haemeta.common.lambda;

import java.io.Serializable;
import java.util.function.Function;

@FunctionalInterface
public interface FunctionPlus<Target,Return> extends Function<Target,Return> , Serializable {
}
