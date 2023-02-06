package com.haemeta.common.entity.lang;

import com.haemeta.common.utils.json.JsonUtil;

import java.util.*;


public class JsonMap implements Map<String,Object> {

    private Map<String,Object> map;

    public JsonMap(){
        this.map = new HashMap<>();
    }

    public String toJsonString(){
        return JsonUtil.toString(map);
    }

    public JsonMap(boolean linked){
        if(linked)
            this.map = new LinkedHashMap<>();
        else
            this.map = new HashMap<>();
    }

    public <T> T get(Object key,Class<T> tClass) {
        if(tClass.equals(String.class))
            return (T) getString(key);
        return (T) map.get(key);
    }

    public Integer getInteger(String key) {
        Object ob = map.get(key);
        return ob == null ? null : Integer.valueOf(ob.toString());
    }

    public Long getLong(String key) {
        Object ob = map.get(key);
        return ob == null ? null : Long.valueOf(ob.toString());
    }

    public Boolean getBoolean(String key) {
        Object ob = map.get(key);
        return ob == null ? null : Boolean.valueOf(ob.toString());
    }

    public String getString(Object key){
        Object val = map.get(key);
        if(val == null) return null;
        return val.toString();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return map.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return map.put(key,value);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<String> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<Object> values() {
        return map.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return map.entrySet();
    }
}
