package com.xw.miaosha.demo.error;

public enum EmBusinessError implements CommonError{
    UNKNOWN_ERROR(10002,"未知错误"),
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    USER_LOGIN_FAILED(20001,"登陆失败，请检查用户名或密码"),
    VERIFICATION_OTPCODE_ERROR(20002,"验证码错误"),
    USER_NOT_LOGIN(20003,"用户未登录"),
    STOCK_NOT_ENOUGH(30001,"库存不足")
    ;

    private int errCode;
    private String errMsg;

    EmBusinessError(int errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
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
