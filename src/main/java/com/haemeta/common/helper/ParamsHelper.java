package com.haemeta.common.helper;

import java.util.function.Function;

public class ParamsHelper<T,Query> extends NullHelper<T>{

    private Query query;

    public static <T,Query> ParamsHelper<T,Query> createHelper(T t,Query query){
        return new ParamsHelper<>( t, query);
    }

    protected ParamsHelper(T t,Query query) {
        super(t);
        this.query = query;
    }

    public <Return> ParamsHelper<T,Query> notNull(Function<? super T, ? extends Return> judge) {
        existList.add(new Judge(0,judge));
        return this;
    }

    public ParamsHelper<T,Query> switchOne(Function<? super T,?>...judge){
        existList.add(new Judge(0,judge));
        return this;
    }

}
