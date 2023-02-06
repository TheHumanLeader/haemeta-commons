package com.haemeta.common.utils.lang;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MapUtil {

    /**
     * 快速创建Map
     * @param keys
     * @param values
     * @param linked
     * @param <Key>
     * @param <Value>
     * @return
     */
    public static <Key, Value> Map toMap(Key[] keys, Value[] values, boolean linked) {
        Map<Key, Value> res;
        if (linked) res = new LinkedHashMap<>();
        else res = new HashMap<>();

        for (int i = 0; i < keys.length; i++) {
            res.put(keys[i], values[i]);
        }

        return res;
    }

    /**
     * 快速创建Map
     * @param key
     * @param value
     * @param linked
     * @param <Key>
     * @param <Value>
     * @return
     */
    public static <Key, Value> Map toMap(Key key, Value value, boolean linked) {
        Map<Key, Value> res;
        if (linked) res = new LinkedHashMap<>();
        else res = new HashMap<>();

        res.put(key, value);

        return res;
    }


    public static <Key, Value> Map toMap(Key[] keys, Value[] values) {
        return toMap(keys, values, false);
    }

    public static <Key, Value> Map toMap(Key key, Value value) {
        return toMap(key, value, false);
    }

    public static <Key,Val> Val get(Map map,Key key,Class<Val> valClass){
        return (Val) map.get(key);
    }


}
