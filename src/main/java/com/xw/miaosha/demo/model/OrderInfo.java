package com.xw.miaosha.demo.model;

import java.math.BigDecimal;

public class OrderInfo {
    private String id;

    private Integer userId;

    private Integer itemId;

    private BigDecimal itemPrice;

    private Integer amount;

    private BigDecimal orderPrice;

    private Integer promoId;

    public OrderInfo(String id, Integer userId, Integer itemId, BigDecimal itemPrice, Integer amount, BigDecimal orderPrice, Integer promoId) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.itemPrice = itemPrice;
        this.amount = amount;
        this.orderPrice = orderPrice;
        this.promoId = promoId;
    }

    public OrderInfo() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }
}