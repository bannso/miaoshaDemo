package com.xw.miaosha.demo.error;

public class BusinessException extends Exception implements CommonError{
    private CommonError commonError;
    @Override
    public int getErrCode() {
        return this.commonError.getErrCode ();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg ();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.setErrMsg (errMsg);
        return this;
    }
    public BusinessException(CommonError commonError){
        /*为什么需要super()*/
//        System.out.println ("为什么需要super()");
        this.commonError = commonError;
    }
    public BusinessException(CommonError commonError, String errMsg){
        this.commonError = commonError;
        this.commonError.setErrMsg (errMsg);
    }
}
