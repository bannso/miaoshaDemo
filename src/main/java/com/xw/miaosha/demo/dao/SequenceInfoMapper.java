package com.xw.miaosha.demo.dao;

import com.xw.miaosha.demo.model.SequenceInfo;

public interface SequenceInfoMapper {
    int deleteByPrimaryKey(String name);

    int insert(SequenceInfo record);

    int insertSelective(SequenceInfo record);

    SequenceInfo selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(SequenceInfo record);

    int updateByPrimaryKey(SequenceInfo record);
}