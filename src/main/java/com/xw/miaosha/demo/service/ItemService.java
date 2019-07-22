package com.xw.miaosha.demo.service;

import com.xw.miaosha.demo.error.BusinessException;
import com.xw.miaosha.demo.service.model.ItemModel;

import java.util.List;

public interface ItemService {
    ItemModel createItem(ItemModel itemModel) throws BusinessException;
    List<ItemModel> listItem();
    ItemModel getItemById(Integer id);
    boolean decreaseStock(Integer itemId,Integer amount);
    void increaseSales(Integer itemId,Integer amount);

}
