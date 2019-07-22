package com.xw.miaosha.demo.controller;

import com.xw.miaosha.demo.error.BusinessException;
import com.xw.miaosha.demo.error.EmBusinessError;
import com.xw.miaosha.demo.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    public static final String CONTENT_TYPE_FORMED = "application/x-www-form-urlencoded";
    //处理不能被controller处理的异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request,Exception ex){
        Map<String,Object> responseData = new HashMap<> ();
        ex.printStackTrace ();
        if(ex instanceof BusinessException){
            responseData.put ("errCode",((BusinessException) ex).getErrCode ());
            responseData.put ("errMsg",((BusinessException) ex).getErrMsg ());
        }else {
            responseData.put ("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode ());
            responseData.put ("errcode",EmBusinessError.UNKNOWN_ERROR.getErrMsg ());
        }
        return CommonReturnType.create (responseData,"fail");
    }
}
