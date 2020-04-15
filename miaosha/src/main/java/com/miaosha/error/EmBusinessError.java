package com.miaosha.error;

/**
 * @date: 2019\11\25 0025
 * @author: zhaijh
 * @description:
 */
public enum EmBusinessError implements CommonError {

    //通用的错误类型00001
    PARAMETER_VALIDATION_ERROR(10001, "参数不合法"),
    UNKNOWN_ERROR(10002, "未知异常"),

    //10000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001, "用户不存在"),
    USER_LOGIN_FILE(20002, "用户手机号或密码错误"),
    USER_NOT_LOGIN(20003, "用户未登录"),
    //30000揩油为订单相关错误定义
    ORDER_STOCK_ENOUGH(30001, "商品库存不足"),
    ;

    private EmBusinessError(int errorCode, String errMsg) {
        this.errorCode = errorCode;
        this.errMsg = errMsg;
    }

    private int errorCode;
    private String errMsg;

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
