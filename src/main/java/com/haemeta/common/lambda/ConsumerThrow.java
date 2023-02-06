package com.haemeta.common.lambda;

import com.haemeta.common.exception.HaemetaException;

import java.util.Objects;
import java.util.function.Consumer;

public interface ConsumerThrow<T> {

    void accept(T t) throws HaemetaException;

}
