package com.xw.miaosha.demo.dao;

import com.xw.miaosha.demo.model.ItemStock;

public interface ItemStockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemStock record);

    int insertSelective(ItemStock record);

    ItemStock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemStock record);

    int updateByPrimaryKey(ItemStock record);

    ItemStock selectByItemId(Integer id);
}