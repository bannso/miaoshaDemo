package com.xw.miaosha.demo.service.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;

@Data
public class PromoModel {
    private Integer id;
    /*状态码：1--未开始  2--进行中  3--已结束*/
    private Integer status;
    private String promoName;
    private DateTime startDate;
    private DateTime endDate;
    private Integer itemId;
    private BigDecimal promoItemPrice;
}
