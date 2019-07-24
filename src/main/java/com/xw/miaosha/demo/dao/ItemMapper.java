package com.xw.miaosha.demo.dao;

import com.xw.miaosha.demo.model.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Item record);

    int insertSelective(Item record);

    Item selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Item record);

    int updateByPrimaryKey(Item record);

    List<Item> listItem ();

    int increaseSales(@Param ("id")Integer id,@Param ("amount")Integer amount);
}