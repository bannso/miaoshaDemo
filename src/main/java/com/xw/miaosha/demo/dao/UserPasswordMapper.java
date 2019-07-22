package com.xw.miaosha.demo.dao;

import com.xw.miaosha.demo.model.UserInfo;
import com.xw.miaosha.demo.model.UserPassword;

public interface UserPasswordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPassword record);

    int insertSelective(UserPassword record);

    UserPassword selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserPassword record);

    int updateByPrimaryKey(UserPassword record);

    UserPassword selectByUserId(Integer id);
}