package com.haemeta.common.utils.lang;

import com.haemeta.common.exception.HaemetaException;
import com.haemeta.common.lambda.ConsumerThrow;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @author 佘纪凡
 * List工具类
 */
public class ListUtil {

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> List<T> merge(Collection<T>... lists) {
        return Stream.of(lists).flatMap(Collection::stream).distinct().collect(Collectors.toList());
    }

    public static <T> T[] merge(Class<T> cl,T[]... arrays) {
        List<T> all = new ArrayList<>();
        for (T[] array : arrays) {
            all.addAll(asList(array));
        }
        T[] res = (T[]) Array.newInstance(cl,all.size());
        for (int i = 0; i < all.size(); i++) {
            res[i] = all.get(i);
        }
        return res;
    }

    /**
     * Key 就是 Key
     * Value 就是Value
     * Function<Object,Key> ， 传入 Null,返回 Key 类型
     * 使用
     * Map<Integer,User> userMap = ListUtil.listToMap( new ArrayList<User>() , User::getId , User::getAccount )
     *
     * @param sourceList
     * @param keyFunction
     * @param valueFunction
     * @param <Key>
     * @param <Value>
     * @return
     */
    public static <Target, Key, Value> Map<Key, Value> toMap(Collection<Target> sourceList,
                                                                 Function<? super Target, ? extends Key> keyFunction,
                                                                 Function<? super Target, ? extends Value> valueFunction) {

        if (isEmpty(sourceList)) return new HashMap<>();
        return sourceList.stream().collect(Collectors.toMap(keyFunction, valueFunction));
    }

    public static <Target, Key, Value,MapType extends Map<Key, Value>> MapType toMap(Collection<Target> sourceList,
                                                             Function<? super Target, ? extends Key> keyFunction,
                                                             Function<? super Target, ? extends Value> valueFunction,
                                                             Class<MapType> mapTypeClass
    ) {

        try {
            if (isEmpty(sourceList)) return mapTypeClass.newInstance();
            return sourceList.stream().collect(Collectors.toMap(keyFunction, valueFunction, (a,b)->b,()->{
                MapType mp = null;
                try {
                    mp = mapTypeClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    mp = (MapType) new HashMap<Key,Value>();
                }
                return mp;
            }));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 方法同上，但是 vue 值是 Target 本身
     * java.lang.IllegalStateException: Duplicate key ;  key 重复
     *
     * @param sourceList
     * @param keyFunction
     * @param <Key>
     * @return
     */
    public static <Target, Key> Map<Key, Target> toMap(Collection<? extends Target> sourceList,
                                                       Function<? super Target, ? extends Key> keyFunction) {

        if (isEmpty(sourceList)) return new HashMap<>();
        return sourceList.stream().collect(Collectors.toMap(keyFunction, target -> target));
    }

    /**
     * List toMap 分组
     *
     * @param sourceList
     * @param keyFunction
     * @param <Target>
     * @param <Key>
     * @return
     */
    public static <Target, Key> Map<Key, List<Target>> groupBy(Collection<Target> sourceList,
                                                                   Function<? super Target, ? extends Key> keyFunction) {
        if (isEmpty(sourceList)) return new HashMap<>();
        return sourceList.stream().collect(Collectors.groupingBy(keyFunction));
    }

    /**
     * List (1000) 分页 toList[List(250),List(250),List(250),List(250)]
     * @param source
     * @param size
     * @param <Target>
     * @return
     */
    public static <Target> Collection<Collection<Target>> partition(List<Target> source, Integer size) {
        if (source == null) {
            throw new NullPointerException("List must not be null");
        } else if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        } else {
            return new Partition(source, size);
        }
    }

    /**
     * 分页调用优化版
     * @param source
     * @param size
     * @param <Target>
     * @return
     */
    public static <Target> void partition(List<Target> source, Integer size,Consumer<List<Target>> call) {
        if (source == null) {
            throw new NullPointerException("List must not be null");
        } else if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        } else {
            new Partition<Target>(source, size).forEach(list->{
                if(!isEmpty(list))
                    call.accept(list);
            });
        }
    }

    /**
     * 浅 复制，复制内存地址
     *
     * @param source
     * @param <Target>
     * @return
     */
    public static <Target> Collection<Target> copy(List<Target> source) {
        List<Target> copy = (List<Target>) Arrays.asList(new Object[source.size()]);
        Collections.copy(copy, source);
        return copy;
    }

    /**
     * array 转 list
     *
     * @param arrays
     * @param <Target>
     * @return
     */
    public static <Target> List<Target> asList(Target... arrays) {
        return Arrays.asList(arrays);
    }

    /**
     * 非空遍历
     * @param list
     * @param action
     * @param <Target>
     */
    public static <Target> Collection<Target> foreach(Collection<Target> list, Consumer<? super Target> action) {
        if (!isEmpty(list))
            list.forEach(action);
        return list;
    }
    public static <Target> Collection<Target> foreachPlus(Collection<Target> list, ConsumerThrow<? super Target> action) throws HaemetaException {
        if (!isEmpty(list)){
            Objects.requireNonNull(action);
            for (Target e : list) {
                action.accept(e);
            }
        }
        return list;
    }

    public static <Target, Parameter> List<Parameter> getList(Collection<Target> source,
                                                              Function<? super Target, ? extends Parameter> parameterFun) {
        if(isEmpty(source)) return Collections.EMPTY_LIST;
        return source.stream().map(parameterFun).collect(Collectors.toList());
    }

    /**
     * 将一个 list 的参数填入另一个 list 的参数中
     *
     * @param sourceList     被填入的参数
     * @param mixList        填入的参数
     * @param setFun         填入的方法
     * @param getFun         获取填入参数的方法
     * @param mixConditions  填入条件
     * @param <Source>
     * @param <Mix>
     * @param <MixParameter>
     */
    public static <Source, Mix, MixParameter> void mix(Collection<Source> sourceList,
                                                       Collection<Mix> mixList,
                                                       BiConsumer<? super Source, ? super MixParameter> setFun,
                                                       Function<? super Mix, MixParameter> getFun,
                                                       BiFunction<? super Source, ? super Mix, Boolean> mixConditions) {
        sourceList.forEach(target -> {
            mixList.forEach(mix -> {
                if (mixConditions.apply(target, mix)) {
                    setFun.accept(target, getFun.apply(mix));
                }
            });
        });
    }

    /**
     * 将一个 list 的参数填入另一个 list 的参数中
     *
     * @param sourceList     被填入的参数
     * @param mixList        填入的参数
     * @param setFun         填入的方法
     * @param getFun         获取填入参数的方法
     * @param mixConditions  填入条件
     * @param <Source>
     * @param <Mix>
     * @param <MixParameter>
     */
    public static <Source, Mix, MixParameter, ConditionParameter> void mix(Collection<Source> sourceList,
                                                                           Collection<Mix> mixList,
                                                                           BiConsumer<? super Source, ? super MixParameter> setFun,
                                                                           Function<? super Mix, MixParameter> getFun,
                                                                           Function<? super Source, ConditionParameter> sourceConditions,
                                                                           Function<? super Mix, ConditionParameter> mixConditions
    ) {
        Map<ConditionParameter, MixParameter> mixCache = toMap(mixList, mixConditions, getFun);
        sourceList.forEach(source -> {
            setFun.accept(source, mixCache.get(sourceConditions.apply(source)));
        });
    }

    /**
     * list to tree
     * 不允许 pid 存在 null 值
     *
     * @param source
     * @param id          id
     * @param pid         parent id
     * @param childrenSet 设置children的方法
     * @param rootPid     最根部 id
     * @param <T>
     * @param <ID>
     * @return
     */
    public static <T, ID> Collection<T> toTreeUsingGroup(
            Collection<T> source,
            Function<? super T, ID> id,
            Function<? super T, ID> pid,
            BiConsumer<? super T, List<T>> childrenSet,
            Supplier<ID> rootPid
    ) {
        //根据 parent id 分组
        Map<ID, List<T>> pidMap = groupBy(
                source,
                pid
        );
        //遍历设置 children
        source.forEach(t -> {
            List<T> childList = pidMap.get(id.apply(t));
            //从 parent id 分组中找，看看当前tree 是否存在 子项
            if (CollectionUtils.isEmpty(childList)) {
                //为了方便前段，空子统一 空数组
                childrenSet.accept(t, new ArrayList<>());
            } else
                childrenSet.accept(t, childList);
        });
        //留下 父级id 为 根id root id 的
        return source.stream().filter(t -> Objects.equals(pid.apply(t),rootPid.get())).collect(Collectors.toList());
    }


    /**
     * list to tree
     * 不允许 pid 存在 null 值
     *
     * @param source
     * @param id          id
     * @param pid         parent id
     * @param rootPid     最根部 id
     * @param <T>
     * @param <ID>
     * @return
     */
    public static <T, ID> List<T> toTree(
            Collection<T> source,
            Function<? super T, ID> id,
            Function<? super T, ID> pid,
            BiConsumer<? super T, T> addChild,
            Supplier<ID> rootPid
    ) {
        //根据 parent id 分组
        Map<ID, T> map = toMap(
                source,
                id
        );
        //遍历 addChild
        source.forEach(t -> {
            T p = map.get(pid.apply(t));
            if(Objects.nonNull(p))
                addChild.accept(p,t);
        });

        //留下 父级id 为 根id root id 的
        return source.stream().filter(t -> Objects.equals(pid.apply(t),rootPid.get())).collect(Collectors.toList());
    }

    /**
     * 内遍历交叉求集
     * @apiNote 本算法由 Sjf 提供，看不懂拉倒
     * @param lists
     * @param res
     * @param call
     * @param <T>
     */
    public static <T> void innerForeach(List<List<T>> lists, List<T> res, Consumer<List<T>> call) {
        // res 非空
        res = Optional.ofNullable(res).orElse(new ArrayList<>());
        //当前处于 lists [1,2,3] 的位置，假设当前位置为 1 (2)
        Integer index = res.size();
        // 获取当前位置的 参数集
        List<T> current = lists.get(index);
        //遍历参数集中的 参数
        for (int j = 0; j < current.size(); j++) {
            //向结果集中添加 当前参数
            res.add(current.get(j));
            //看看当前集合 在 lists [1,2,3] 中，是否存在 下一个
            //位置 1(2) 存在下一个 2(3)
            if (index + 1 < lists.size()) {
                //迭代到下一层
                innerForeach(lists, res, call);
            } else {
                //如果处于 最终集中，即  当前结果集 与 lists 长度一致
                //解释： lists 有五层，即最后的结果必定有 5 个参数
                if (res.size() == lists.size())
                    call.accept(res);
            }
            //结束当前循环后移除最后一个目标，即重置最后一个参数
            //如果当前 层级 已经完全遍历，则会返回上一层级
            res.remove(res.size() - 1);
        }
    }

    /**
     * 遍历 将 null 设置为 指定值
     * @param sourceList
     * @param elemGet
     * @param elemSet
     * @param reset
     * @param <Source>
     * @param <Elem>
     * @return
     */
    public static <Source, Elem> Collection<Source> nullSet(Collection<Source> sourceList,
                                                            Function<? super Source, Elem> elemGet,
                                                            BiConsumer<? super Source, Elem> elemSet,
                                                            Supplier<Elem> reset) {
        sourceList.forEach(item -> {
            elemSet.accept(
                    item,
                    Optional.ofNullable(elemGet.apply(item)).orElseGet(reset)
            );
        });
        return sourceList;
    }

    public static <Target> List<Target> filter(Collection<Target> source,Predicate<? super Target> predicate){
        return source.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 去重(<span style="color:rgb(0,172,193)">深</span>)
     * @param sourceList
     * @param get
     * @param <T>
     * @param <U>
     * @return
     */
    public static <T,U extends Comparable<? super U>> List<T> removeDuplicate(Collection<T> sourceList,Function<T,U> get){
        return sourceList.stream().collect(
                collectingAndThen(
                        toCollection(
                                () -> new TreeSet<T>(Comparator.comparing(get))
                        ),
                        ArrayList::new
                )
        );
    }

    /**
     * 去重(<span style="color:rgb(0,172,193)">浅</span>)
     * @param sourceList
     * @param <T>
     * @return
     */
    public static <T> List<T> removeDuplicate(Collection<T> sourceList){
        return sourceList.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 合并 ， 并去重 (<span style="color:rgb(0,172,193)">深</span>)
     * @param source
     * @param append
     * @param judge
     * @param <T>
     * @param <Judge>
     */
    public static  <T,Judge> void mergeAndDeDuplication(List<T> source, List<T> append , Function<T,Judge> judge){
        if(append.isEmpty()) return;

        List<Judge> appendIds = ListUtil.getList(append,judge);
        List<T> intersection = ListUtil.filter(source,t->appendIds.contains(judge.apply(t)));

        source.addAll(append);
        if(!intersection.isEmpty())
            source.removeAll(intersection);
    }

    private static class Partition<T> extends AbstractList<List<T>> {
        private final List<T> list;
        private final int size;

        private Partition(List<T> list, int size) {
            this.list = list;
            this.size = size;
        }

        @Override
        public List<T> get(int index) {
            int listSize = this.size();
            if (index < 0) {
                throw new IndexOutOfBoundsException("Index " + index + " must not be negative");
            } else if (index >= listSize) {
                throw new IndexOutOfBoundsException("Index " + index + " must be less than size " + listSize);
            } else {
                int start = index * this.size;
                int end = Math.min(start + this.size, this.list.size());
                return this.list.subList(start, end);
            }
        }

        public int size() {
            return (int) Math.ceil((double) this.list.size() / (double) this.size);
        }

        public boolean isEmpty() {
            return this.list.isEmpty();
        }
    }



}
