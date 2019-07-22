package com.xw.miaosha.demo.model;

public class ItemStock {
    private Integer id;

    private Integer stock;

    private Integer itemId;

    public ItemStock(Integer id, Integer stock, Integer itemId) {
        this.id = id;
        this.stock = stock;
        this.itemId = itemId;
    }

    public ItemStock() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}