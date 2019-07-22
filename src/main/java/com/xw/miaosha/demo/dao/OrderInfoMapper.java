package com.xw.miaosha.demo.dao;

import com.xw.miaosha.demo.model.OrderInfo;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);
}