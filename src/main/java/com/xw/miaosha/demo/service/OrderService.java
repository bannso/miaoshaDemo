package com.xw.miaosha.demo.service;

import com.xw.miaosha.demo.error.BusinessException;
import com.xw.miaosha.demo.service.model.OrderModel;

public interface OrderService {
    OrderModel createOrder(Integer userId,Integer itemId,Integer promoId,Integer amount) throws BusinessException;
}
