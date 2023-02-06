package com.haemeta.common.entity.vo;

import com.github.pagehelper.PageHelper;
import com.haemeta.common.utils.lang.DateUtil;
import com.haemeta.common.utils.lang.MapUtil;

import java.util.*;
import java.util.function.*;

public class Page<T> {

    protected Collection<T> pages;

    /**
     * 查询条件
     * 与其说是 条件，我愿意称之为 因果，因为所以
     */
    protected Map<String, Object> cause;

    /**
     * 第几页
     */
    protected Integer page;

    /**
     * 第几页
     */
    protected Integer pageSize;

    /**
     * 一共多少数据
     */
    protected Long total;

    public Page() {
        this.pages = new ArrayList<>();
    }

    /**
     * 是否分页
     *
     * @return
     */
    public boolean paginate() {
        return page != null && pageSize != null;
    }

    /**
     * get Cause 中的参数
     *
     * @param key key
     * @return
     */
    public Object get(String key) {
        if (cause == null) return null;
        return cause.get(key);
    }

    public String getString(String key) {
        Object val = cause.get(key);
        if (val == null) return null;
        if(val instanceof String)
            return (String) val;
        else
            return val.toString();
    }

    public Date getDate(String key) {
        if (cause == null) return null;
        Object obj = cause.get(key);

        if (obj instanceof Date)
            return (Date) cause.get(key);
        try {
            if (obj instanceof String) {
                return parseDate((String) obj);
            } else if (obj instanceof Long) {
                return new Date((Long) obj);
            } else return null;
        } catch (Exception e) {
            return null;
        }
    }

    private Date parseDate(String dateStr) {
        Date date = DateUtil.toDate(DateUtil.yyyy_MM_dd_HH_mm_ss, dateStr);
        return date == null ? DateUtil.toDate(DateUtil.yyyy_MM_dd, dateStr) : date;
    }

    public Integer getInteger(String key) {
        if (cause == null) return null;
        return (Integer) cause.get(key);
    }

    public Long getLong(String key) {
        if (cause == null) return null;
        Object ob = cause.get(key);
        return ob == null ? null : Long.valueOf(ob.toString());
    }

    public Boolean getBoolean(String key) {
        if (cause == null) return null;
        return (Boolean) cause.get(key);
    }

    /**
     * get Cause 中的参数
     *
     * @param key        key
     * @param defaultVal 如果为空的默认值
     * @param valClass   返回类型
     * @param <Val>
     * @return
     */
    public <Val> Val get(String key, Val defaultVal, Class<Val> valClass) {
        if (cause == null) return defaultVal;
        return Optional.ofNullable(MapUtil.get(cause, key, valClass)).orElse(defaultVal);
    }

    /**
     * put cause 中的参数
     *
     * @param key
     * @param val
     * @param <Val>
     * @return
     */
    public <Val> Page<T> put(String key, Val val) {
        if (cause == null) cause = new HashMap<>();
        cause.put(key, val);
        return this;
    }

    /**
     * 如果不是空的则执行 valCall
     *
     * @param key
     * @param tClass
     * @param valCall
     * @param <Val>
     * @return
     */
    public <Val> void nonNull(String key, Class<Val> tClass, Consumer<Val> valCall) {
        Object val = cause.get(key);
        if (val != null) valCall.accept((Val) val);
    }

    public void setCause(Map<String, Object> cause) {
        this.cause = cause;
        Object page = cause.get("page");
        Object pageSize = cause.get("pageSize");

        if (Objects.nonNull(page) && Objects.nonNull(pageSize)) {
            this.setPage((Integer) page);
            setPageSize((Integer) pageSize);
        }
    }

    public Map<String, Object> getCause() {
        return cause;
    }

    public Integer getPage() {
        if (isPageHelper()) return parsePageHelper().getPageNum();
        return page;
    }

    public Integer getPageSize() {
        if (isPageHelper()) return parsePageHelper().getPageSize();
        return pageSize;
    }

    public Page<T> setPage(Integer page) {
        if (page != null && pages instanceof com.github.pagehelper.Page)
            parsePageHelper().pageNum(page);
        this.page = page;
        return this;
    }

    public <P extends Page<T>, T> P setPageSize(Integer pageSize) {
        if (isPageHelper())
            parsePageHelper().pageSize(pageSize);
        this.pageSize = pageSize;
        return (P) this;
    }

    public Collection<T> getData() {
        return pages;
    }

    public Page<T> setData(Collection<T> pages) {
        this.pages = pages;
        if (isPageHelper())
            this.total = parsePageHelper().getTotal();
        return this;
    }

    public Long getTotal() {
        if (isPageHelper())
            return parsePageHelper().getTotal();
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public boolean isPageHelper() {
        return pages != null && pages instanceof com.github.pagehelper.Page;
    }

    public com.github.pagehelper.Page parsePageHelper() {
        return ((com.github.pagehelper.Page) this.pages);
    }

    public Page<T> push(T t) {
        if (pages == null)
            this.pages = new ArrayList<>();
        pages.add(t);
        return this;
    }

    public <Val> Page<T> pushIfCauseNonNull(String cause, Function<Val, T> call) {
        if (pages == null)
            this.pages = new ArrayList<>();

        if (this.cause != null) {
            Val val = (Val) this.cause.get(cause);
            if (Objects.nonNull(val))
                pages.add(call.apply(val));
        }
        return this;
    }

    public void autoPage() {
        if (paginate()) {
            put("startPage", (page - 1) * pageSize);
            put("pageSize", pageSize);
        }
    }

    public void pageHelper(){
        if(paginate())
            PageHelper.startPage(page,pageSize);
    }

}
