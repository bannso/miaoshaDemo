package com.xw.miaosha.demo.controller;


import com.xw.miaosha.demo.error.BusinessException;
import com.xw.miaosha.demo.error.EmBusinessError;
import com.xw.miaosha.demo.response.CommonReturnType;
import com.xw.miaosha.demo.service.OrderService;
import com.xw.miaosha.demo.service.model.OrderModel;
import com.xw.miaosha.demo.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/order")
public class OrderController extends BaseController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/create",method = RequestMethod.POST,consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType createOrder(@RequestParam(name = "itemId")Integer itemId,
                                        @RequestParam(name = "promoId",required = false)Integer promoId,
                                        @RequestParam(name = "amount")Integer amount) throws Exception {
        Boolean isLogin = (Boolean) this.httpServletRequest.getSession ().getAttribute ("LOGIN_USER");
//        if(isLogin==null||isLogin.booleanValue ()||isLogin==false) throw new BusinessException (EmBusinessError.USER_NOT_LOGIN);
//        UserModel userModel = (UserModel) this.httpServletRequest.getSession ().getAttribute ("LOGIN_USER");
        UserModel userModel = new UserModel ();
        userModel.setId (18);

        OrderModel orderModel = orderService.createOrder (userModel.getId (),itemId,promoId,amount);
        return CommonReturnType.create (orderModel);
    }
}
