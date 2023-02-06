package com.haemeta.common.entity.pojo;

import com.haemeta.common.utils.lang.ListUtil;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class BeansMix<Source, Mix, Parameter,ConditionParameter> {

    /**
     * 目标：被填入的List对象
     */
    private List<Source> sourceList;
    /**
     * 填充物：向 sourceList 中填充的参数list
     */
    private List<Mix> mixList;
    /**
     * sourceList 填充属性的方法
     */
    private BiConsumer<Source, Parameter> setMethod;
    /**
     * 从填充
     */
    private Function<? super Mix, Parameter> getMethod;
    /**
     * 循环填充的判断条件
     */
    private BiFunction<Source, Mix, Boolean> circleConditions;

    /**
     * Source 填充时， 从cacheMap 中获取填充物的 key
     * @param sourceList
     * @param mixList
     */
    private Function<? super Source,ConditionParameter> sourceConditions;

    /**
     * Mix 生成 cacheMap时，cacheMap<Key,Value>，key的生成方法
     * Value的生成方法 是 getMethod
     * @param sourceList
     * @param mixList
     */
    private Function<? super Mix,ConditionParameter> mixConditions;

    private BeansMix(List<Source> sourceList, List<Mix> mixList) {
        this.sourceList = sourceList;
        this.mixList = mixList;
    }


    /**
     * 创建 mix 实体
     * @param sourceList      被填值的  list
     * @param mixList     要填入的  list
     * @param <Source>
     * @param <Mix>
     * @param <Parameter>
     * @return
     */
    public static <Source, Mix, Parameter> BeansMix<Source, Mix, ?,?> createMix(List<Source> sourceList, List<Mix> mixList) {
        return new BeansMix(sourceList, mixList);
    }

    /**
     * 在 Source 中设置混入的属性 的方法
     * source.set(parameter)
     * @param setMethod
     * @return
     */
    public <Param> BeansMix<Source, Mix, Param,?> setParameter(BiConsumer<Source, Param> setMethod) {
        this.setMethod = (BiConsumer<Source, Parameter>) setMethod;
        return (BeansMix<Source, Mix, Param, ?>) this;
    }

    /**
     * 从 Mix 中获取混入属性的 方法
     * parameter = mix.get();
     * @param getMethod
     * @return
     */
    public <Param> BeansMix<Source, Mix, Parameter,?> getParameter(Function<? super Mix, Param> getMethod) {
        this.getMethod = (Function<? super Mix, Parameter>) getMethod;
        return this;
    }

    public BeansMix<Source, Mix, Parameter,?> fixConditions(BiFunction<Source, Mix, Boolean> conditions) {
        this.circleConditions = conditions;
        return this;
    }

    public <ConditionParam> BeansMix<Source, Mix, Parameter,ConditionParam> sourceConditions(Function<? super Source, ConditionParam> sourceConditions){
        this.sourceConditions = (Function<? super Source, ConditionParameter>) sourceConditions;
        return (BeansMix<Source, Mix, Parameter, ConditionParam>) this;
    }

    public <ConditionParam> BeansMix<Source, Mix, Parameter,ConditionParam> mixConditions(Function<? super Mix, ConditionParam> mixConditions){
        this.mixConditions = (Function<? super Mix, ConditionParameter>) mixConditions;
        return (BeansMix<Source, Mix, Parameter, ConditionParam>) this;
    }

    /**
     * 执行
     * @return
     */
    public List<Source> run() {
        //遍历次数大于40w 建议使用map
        if (circleConditions == null){
            ListUtil.mix(
                    sourceList,
                    mixList,
                    setMethod,
                    getMethod,
                    sourceConditions,
                    mixConditions
            );
        }else{
            ListUtil.mix(
                    sourceList,
                    mixList,
                    setMethod,
                    getMethod,
                    circleConditions
            );
        }
        return sourceList;
    }

    /**
     * 执行并返回一个参数
     * @return
     */
    public Source runBackSingle() {
        if (ListUtil.isEmpty(sourceList)) return null;
        return sourceList.get(0);
    }


}
