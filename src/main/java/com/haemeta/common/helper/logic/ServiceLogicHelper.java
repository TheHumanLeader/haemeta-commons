package com.haemeta.common.helper.logic;

import com.haemeta.common.entity.TestEntityVo;
import com.haemeta.common.entity.TestInfoEntity;
import com.haemeta.common.entity.pojo.BeansMix;
import com.haemeta.common.entity.vo.Result;
import com.haemeta.common.exception.HaemetaException;
import com.haemeta.common.exception.helper.ServiceLogicHelperException;
import com.haemeta.common.lambda.FunctionPlus;
import com.haemeta.common.service.TestService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ServiceLogicHelper<Service, Data> extends LogicHelper<Data, Service> {

    private Service service;
    /**
     * 逻辑判断是否通过
     */
    private boolean judgePass;

    private ServiceLogicHelper(Service service, Function<? super Service, Data> dataApply) throws HaemetaException {
        if (service == null) throw new ServiceLogicHelperException("Service");
        if (dataApply == null) throw new ServiceLogicHelperException("get data function");
        this.service = service;
        this.dataApply = dataApply;
        this.judgePass = true;

    }


    /**
     * 创建服务层逻辑辅助者
     *
     * @param service
     * @param dataApply
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> ServiceLogicHelper<S, T> createHelper(S service, Function<S, T> dataApply) throws HaemetaException {
        return new ServiceLogicHelper<S, T>(service, dataApply);
    }

    /**
     * 判定
     * 是否执行
     *
     * @param judgeApply
     * @return
     */
    public <Exp extends Exception> ServiceLogicHelper<Service, Data> judge(Supplier<Boolean> judgeApply, Exp exp) throws Exception {
        if (!judgeApply.get()) throw exp;
        return this;
    }

    /**
     * 判定
     * 是否执行
     * @param judgeApply
     * @return
     */
    public ServiceLogicHelper<Service, Data> judge(Supplier<Boolean> judgeApply) {
        if (!judgePass) return this;
        if (!judgeApply.get()) judgePass = false;
        return this;
    }

    /**
     * 对 参数 进行操作
     * @param operation
     * @return
     */
    public ServiceLogicHelper<Service, Data> operation(Consumer<Data> operation) {
        loadData();
        operation.accept(run());
        return this;
    }


    public Data run() {
        if (judgePass && this.data == null)
            this.loadData();
        return data;
    }

    /**
     * 加载数据
     */
    public void loadData() {
        if (data == null)
            this.data = this.dataApply.apply(service);
    }

    /**
     * 强制重载数据
     */
    public void reloadData() {
        this.data = this.dataApply.apply(service);
    }

    /**
     * 如果逻辑判断 不通过，或者 数据本身就是 空的
     * 以其他类型返回
     * @param orData
     * @param dataOperation
     * @param <DataOr>
     * @return
     */
    public <DataOr> DataOr orElseOther(DataOr orData,Function<Data,DataOr> dataOperation){
        if(!judgePass) return orData;
        loadData();
        if(this.data == null) return orData;
        return dataOperation.apply(data);
    }

    public Data orElse(Data orData){
        if(!judgePass) return orData;
        loadData();
        if(this.data == null) return orData;
        return data;
    }

    public static void main(String[] args) throws Exception, HaemetaException {
        TestService service = new TestService();
//        List list = createHelper(service, s -> s.selectEntity(2, 4, 8, 13))
//                .judge(() -> true, new Exception("a"))
//                .operation(res -> {
//
//                    BeansMix.createMix(res,service.selectInfo(2, 4, 8, 13))
//                            .setParameter(TestEntityVo::setInfoEntity)
//                            .sourceConditions(TestEntityVo::getCode)
//                            .getParameter(o->o)
//                            .mixConditions(TestInfoEntity::getCode)
//                            .run();
//
//                }).getData();

        Integer[] arr = new Integer[]{};
        //创建 辅助者
        Result rs = createHelper(service, s -> s.selectEntity(arr))
                //逻辑判断 ， 只有逻辑判断通过，才会执行  s -> s.selectEntity(arr)
                .judge(() -> arr != null && arr.length > 0)
                //如果是 逻辑判断是不通过的， 或 数据是空的
                //以其他类型返回
                .orElseOther(Result.fail("没有"),(data)->Result.suc(data));

        List list = createHelper(service, s -> s.selectEntity(arr))
                //逻辑判断 ， 只有逻辑判断通过，才会执行  s -> s.selectEntity(arr)
                .judge(() -> arr != null && arr.length > 0)
                //如果是 逻辑判断是不通过的， 或 数据是空的
                //以其他类型返回
                .orElse(Collections.EMPTY_LIST);

        System.out.println(list);
    }

}
