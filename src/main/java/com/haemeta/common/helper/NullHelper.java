package com.haemeta.common.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class NullHelper<Target> {

    protected Target target;
    protected List<Judge> existList;

    public static <Target> NullHelper<Target> createHelper(Target target) {
        return new NullHelper<Target>(target);
    }

    protected NullHelper(Target target) {
        this.target = target;
        this.existList = new ArrayList<>();
    }

    /**
     * 检查目标是否空
     * @param judge
     * @param <Return>
     */
    public <Return> NullHelper<Target> exist(Function<? super Target, ? extends Return> judge) {
        existList.add(new Judge(0,judge));
        return this;
    }

    /**
     * 检查String是否为空
     */
    public NullHelper<Target> judge(Predicate<? super Target> judge) {
        existList.add(new Judge(1,judge));
        return this;
    }

    protected static class Judge<T>{
        //0 Function , 1 Predicate
        protected int type;
        protected T method;
        protected Judge(int type,T method){
            this.type = type;
            this.method = method;
        }
    }

}
