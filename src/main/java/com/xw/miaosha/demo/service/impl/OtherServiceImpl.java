package com.xw.miaosha.demo.service.impl;

import com.xw.miaosha.demo.dao.SequenceInfoMapper;
import com.xw.miaosha.demo.error.BusinessException;
import com.xw.miaosha.demo.model.SequenceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class OtherServiceImpl {
    @Autowired(required = false)
    SequenceInfoMapper sequenceInfoMapper;
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {BusinessException.class})
    public String generateOrderNo() {
        /*生成16位订单编号，8位时间信息+6位自增序列+分库分表信息*/
        /*8位时间信息*/
        StringBuilder sb = new StringBuilder ();
        LocalDateTime now = LocalDateTime.now ();
        String nowTime = now.format (DateTimeFormatter.ISO_DATE).replace ("-","");
        sb.append (nowTime);
        /*6位自增序列*/
        int sequence = 0;
        SequenceInfo sequenceInfo = sequenceInfoMapper.selectByPrimaryKey ("order_info");
        sequence = sequenceInfo.getCurrentValue ();
        sequenceInfo.setCurrentValue (sequenceInfo.getStep ()+sequenceInfo.getCurrentValue ());
        sequenceInfoMapper.updateByPrimaryKeySelective (sequenceInfo);
        /*补足6位 暂不考虑溢出6位的情况*/
        for (int i = 0; i < 6 - String.valueOf (sequence).length (); i++) {
            sb.append (0);
        }
        sb.append (sequence);
        /*分库分表暂不考虑*/
        sb.append ("00");
        return sb.toString ();
    }
}
