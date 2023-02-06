package com.haemeta.common.entity.vo;

import com.haemeta.common.enums.ResultStatusCodes;
import com.haemeta.common.interfaces.TokenInterface;

public class Result<T> {

    /**
     * 没有任何问题，code 都为 0
     * 负面码：任何异常 都小于 0
     * 正面码：用去前段逻辑处理，大于0
     */
    private Integer code;

    private T data;

    private Integer page;

    private Integer pageSize;

    private Long total;

    private String msg;

    private boolean token_refresh;

    private String access_token;

    private Long expired_in;

    private Result() {
    }
    private Result(T data) {
        this.data = data;
    }

    public static Result suc(){
        return new Result().code(0);
    }

    public static <T> Result suc(T data){
        return new Result(data)
                .code(ResultStatusCodes.SUCCESS.getCode());
    }

    public static <T> Result suc(T data,String msg){
        return new Result(data)
                .code(ResultStatusCodes.SUCCESS.getCode())
                .msg(msg);
    }

    public static <T> Result suc(T data,Integer code){
        return new Result(data)
                .code(code);
    }

    public static <T> Result suc(T data,ResultStatusCodes code){
        return new Result(data)
                .code(code.getCode());
    }

    public static <T> Result suc(T data,Integer code,String msg){
        return new Result(data)
                .code(code)
                .msg(msg);
    }

    public static <T> Result suc(T data,ResultStatusCodes code,String msg){
        return new Result(data)
                .code(code.getCode())
                .msg(msg);
    }

    public static <Bean> Result page(Page<Bean> page){
        return new Result(page.getData())
                .total(page.getTotal())
                .page(page.getPage())
                .pageSize(page.getPageSize())
                .code(0);
    }

    public static <Bean,Tk extends TokenInterface> Result page(Page<Bean> page, Tk token){
        return new Result(page.getData())
                .total(page.getTotal())
                .page(page.getPage())
                .pageSize(page.getPageSize())
                .code(0);
    }

    public static <Bean> Result page(Page<Bean> page, Integer code){
        return new Result(page.getData())
                .total(page.getTotal())
                .page(page.getPage())
                .pageSize(page.getPageSize())
                .code(code);
    }

    public static <Bean> Result page(Page<Bean> page, ResultStatusCodes code){
        return new Result(page.getData())
                .total(page.getTotal())
                .page(page.getPage())
                .pageSize(page.getPageSize())
                .code(code.getCode())
                .msg(code.getMsg());
    }

    public static <Bean> Result page(Page<Bean> page,String msg){
        return new Result(page.getData())
                .total(page.getTotal())
                .page(page.getPage())
                .pageSize(page.getPageSize())
                .msg(msg)
                .code(0);
    }

    public static <Bean> Result page(Page<Bean> page,Integer code,String msg){
        return new Result(page.getData())
                .total(page.getTotal())
                .page(page.getPage())
                .pageSize(page.getPageSize())
                .msg(msg)
                .code(code);
    }

    public static <Bean> Result page(Page<Bean> page,ResultStatusCodes code,String msg){
        return new Result(page.getData())
                .total(page.getTotal())
                .page(page.getPage())
                .pageSize(page.getPageSize())
                .msg(msg)
                .code(code.getCode());
    }

    public static <Tk extends TokenInterface> Result token(Tk token){
        return new Result()
                .accessToken(token.token())
                .expiredIn(token.duration())
                .tokenRefresh()
                .code(0);
    }

    public static Result fail(){
        return new Result().code(-100);
    }

    public static Result fail(ResultStatusCodes code){
        return new Result()
                .msg(code.getMsg())
                .code(code.getCode());
    }

    public static Result fail(String msg){
        return new Result()
                .msg(msg)
                .code(ResultStatusCodes.FAIL.getCode());
    }

    public static Result error(){
        return new Result()
                .code(ResultStatusCodes.ERROR.getCode())
                .msg(ResultStatusCodes.ERROR.getMsg());
    }

    public static Result error(ResultStatusCodes code){
        return new Result()
                .code(code.getCode())
                .msg(code.getMsg());
    }

    public static Result error(ResultStatusCodes code,String msg){
        return new Result()
                .code(code.getCode())
                .msg(msg);
    }

    public static Result insert(Boolean suc){
        Result rs = new Result();
        ResultStatusCodes code = null;
        if(suc) code = ResultStatusCodes.SUCCESS;
        else code = ResultStatusCodes.DATA_INSERT_FAIL;
        return rs.code(code.getCode())
                .msg(code.getMsg());
    }
    public static Result update(Boolean suc){
        Result rs = new Result();
        ResultStatusCodes code = null;
        if(suc) code = ResultStatusCodes.SUCCESS;
        else code = ResultStatusCodes.DATA_UPDATE_FAIL;
        return rs.code(code.getCode())
                .msg(code.getMsg());
    }

    public Integer code() {
        return code;
    }

    public Result<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public T data() {
        return data;
    }

    public Result<T> data(T data) {
        this.data = data;
        return this;
    }

    public Long total() {
        return total;
    }

    public Result<T> total(Long total) {
        this.total = total;
        return this;
    }

    public String msg() {
        return msg;
    }

    public Result<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public Integer page() {
        return page;
    }

    public Result<T> page(Integer page) {
        this.page = page;
        return this;
    }

    public Integer pageSize() {
        return pageSize;
    }

    public Result<T> pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public boolean tokenRefresh(boolean refresh) {
        return token_refresh = refresh;
    }

    public Result<T> tokenRefresh() {
        this.token_refresh = true;
        return this;
    }

    public String accessToken() {
        return access_token;
    }

    public Result<T> accessToken(String access_token) {
        this.access_token = access_token;
        return this;
    }

    public Long expiredIn() {
        return expired_in;
    }

    public Result<T> expiredIn(Long expired_in) {
        this.expired_in = expired_in;
        return this;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean getToken_refresh() {
        return token_refresh;
    }

    public void setToken_refresh(boolean token_refresh) {
        this.token_refresh = token_refresh;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getExpired_in() {
        return expired_in;
    }

    public void setExpired_in(Long expired_in) {
        this.expired_in = expired_in;
    }
}
