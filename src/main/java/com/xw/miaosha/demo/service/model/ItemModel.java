package com.xw.miaosha.demo.service.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ItemModel {
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;

    @NotBlank(message = "商品名称不能为空")
    private String title;

    @NotNull(message = "商品价格不能为空")
    @Min (value = 0,message = "商品价格不能小于0")
    private BigDecimal price;

    @NotNull(message = "商品库存不能为空")
    private Integer stock;

    @NotBlank(message = "商品描述不能为空")
    private  String description;

    private String sales;


    @NotBlank(message = "商品图片不能为空")
    private String imgUrl;

    private PromoModel promoModel;
}
