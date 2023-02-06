package com.haemeta.common.enums;

/**
 * 系统相关： -300 ~ -399  (空指针，内存溢出，下标越界等)
 *
 * 登陆相关： -400 ~ -499 (未登录，登陆失效，账户异常)
 *
 * 数据相关：-700 ~ -999
 *      · 主键、外键、关联、id缺失
 *      · 数据不存在，未启用，已操作，不匹配，不对称
 *
 * 数据库相关: -1000 ~ - 1099
 *      · 数据源信息错误，数据源不存在
 *
 *
 * 微信： -3000 ~  -4000
 *      · 小程序  -3000 ~ -3199
 *      · 公众号  -3200 ~ -3399
 *      · 企业微信  -3400 ~ -3599
 *      · 开放平台  -3600 ~ -3799
 *      · 微信支付  -3800 ~ -3999
 *
 * 支付宝： -5000 ~ -6000
 *
 *
 * REDIS: -10100 ~ -10200
 *
 */
public enum ResultStatusCodes {

    SUCCESS(0,"接口响应成功"),


    FAIL(-100,"接口响应失败(异常)"),
    ERROR(-100,"接口响应异常(失败)"),

    UNAUTHORIZED(-101,"访问未授权"),
    ACCESS_DENIED(-102,"访问被拒绝"),

    SYSTEM_ERROR(-300,"系统异常"),
    SYSTEM_NULL_DATA(-301,"系统参数缺失(或空指针)"),
    SYSTEM_CIPHERTEXT_ERROR(-302,"密文(加密/解密)异常"),
    TOKEN_CREATE_ERROR(-303,"Token 创建异常"),


    LOGIN_FAIL(-400,"登陆失败"),
    LOGIN_PASSWORD_ERROR(-401, "密码错误"),
    LOGIN_ACCOUNT_NOT_EXIST(-402,"账户不存在"),
    LOGIN_CAPTCHA_ERROR(-403, "验证码错误"),

    NO_LOGIN(-404,"未登录"),

    LOGIN_INFO_LOSS_EFFICACY(-405,"登陆信息已失效(已过期)"),
    LOGIN_INFO_ERROR(-406,"登陆信息错误(不匹配)"),
    LOGIN_INFO_DISABLE(-407,"登陆信息被禁用(未启用)"),

    ACCOUNT_LOCKED(-408,"账户被锁定"),
    ACCOUNT_UN_ENABLE(-409,"账户未启用"),
    ACCOUNT_NOT_EXIST(-410,"账户不存在"),
    ACCOUNT_UN_BIND(-411,"账户未绑定"),
    ACCOUNT_BIND_FAIL(-412,"账户绑定失败"),

    DELETE_PRIMARY_KEY_NULL(-700,"删除时，缺少主键"),
    UPDATE_PRIMARY_KEY_NULL(-701,"更新时，缺少主键"),

    CREATE_USER_PRIMARY_KEY_NULL(-720,"插入数据时，缺少操作人主键"),
    UPDATE_USER_PRIMARY_KEY_NULL(-721,"更新数据时，缺少操作人主键"),

    DATA_NOT_EXIST(-800,"数据不存在"),
    DATA_NOT_ENABLE(-801,"数据未启用"),
    DATA_NOT_MATCH(-802,"数据已变更，与数据库不匹配(不对称)"),
    DATA_DUPLICATION(-803,"数据重复"),
    DATA_LACK(-804,"数据缺失"),
    DATA_ERROR(-805,"数据异常"),
    DATA_UPDATE_FAIL(-806,"数据更新失败"),
    DATA_INSERT_FAIL(-807,"数据新增失败"),

    DATA_SOURCE_INFO_ERROR(-1000,"数据源信息错误"),


    WX_UN_BIND(-3000,"未绑定微信"),
    WX_BIND_FAIL(-3001,"微信绑定失败"),
    WX_OPENID_GET_FAIL(-3002,"微信获取 openid 失败"),


    REDIS_EXEC_FAIL(-10100,"exec 指令执行失败"),
    REDIS_MULTI_EXEC_FAIL(-10101,"multi 事务 exec 指令执行失败"),
    REDIS_RENAME_FAIL(-10102,"redis 执行指令 rename 失败")


    ;

    private ResultStatusCodes(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private final Integer code;

    private final String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String toJsonString(){
        StringBuffer buffer = new StringBuffer("{");
        for (ResultStatusCodes value : ResultStatusCodes.values()) {
//            buffer.append("\"");
            buffer.append(value.toString());
//            buffer.append("\"");
            buffer.append(":");
            buffer.append(value.getCode());
            buffer.append(",");
        }

        buffer.append("}");
        return buffer.replace(buffer.length() - 2,buffer.length() - 1,"").toString();
    }

}
