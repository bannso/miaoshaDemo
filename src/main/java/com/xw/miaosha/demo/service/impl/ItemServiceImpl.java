package com.xw.miaosha.demo.service.impl;

import com.xw.miaosha.demo.dao.ItemMapper;
import com.xw.miaosha.demo.dao.ItemStockMapper;
import com.xw.miaosha.demo.dao.PromoMapper;
import com.xw.miaosha.demo.error.BusinessException;
import com.xw.miaosha.demo.error.EmBusinessError;
import com.xw.miaosha.demo.model.Item;
import com.xw.miaosha.demo.model.ItemStock;
import com.xw.miaosha.demo.service.ItemService;
import com.xw.miaosha.demo.service.PromoService;
import com.xw.miaosha.demo.service.model.ItemModel;
import com.xw.miaosha.demo.service.model.PromoModel;
import com.xw.miaosha.demo.validator.ValidatorImpl;
import com.xw.miaosha.demo.validator.ValidatorResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ValidatorImpl validator;
    @Autowired(required = false)
    ItemMapper itemMapper;
    @Autowired(required = false)
    ItemStockMapper itemStockMapper;
    @Autowired
    PromoService promoService;


    private Item convertItemFromItemModel(ItemModel itemModel) {
        if (itemModel==null) return null;
        Item item = new Item ();
        BeanUtils.copyProperties (itemModel,item);
        return item;
    }

    private ItemStock convertStockFromItemModel(ItemModel itemModel) {
        if (itemModel==null) return null;
        ItemStock stock = new ItemStock ();
        stock.setItemId (itemModel.getId ());
        stock.setStock (itemModel.getStock ());
        return stock;
    }


    private ItemModel generateModelFromDataObject(Item item, ItemStock stock) {
        if(item==null||stock==null) return null;
        ItemModel itemModel = new ItemModel ();
        BeanUtils.copyProperties (item,itemModel);
        itemModel.setStock (stock.getStock ());
        return itemModel;
    }


    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        ValidatorResult result = validator.validate (itemModel);
        if(result.isHasErrors ()) throw new BusinessException (EmBusinessError.PARAMETER_VALIDATION_ERROR);
        //创建 Item
        Item item = convertItemFromItemModel(itemModel);
        itemMapper.insertSelective (item);
        itemModel.setId (item.getId ());
        //创建 ItemStock
        ItemStock stock = convertStockFromItemModel(itemModel);
        itemStockMapper.insertSelective (stock);
        //返回存入数据库中的对象
        return getItemById (item.getId ());
    }


    @Override
    public List<ItemModel> listItem() {
        List<Item> list = itemMapper.listItem ();
        List<ItemModel> models = list.stream ().map (item -> {
            ItemStock stock = itemStockMapper.selectByItemId (item.getId ());
            ItemModel model = generateModelFromDataObject (item,stock);
            return model;
        }).collect (Collectors.toList ());

        return models;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        Item item = itemMapper.selectByPrimaryKey (id);
        if (item==null) return null;
        ItemStock stock = itemStockMapper.selectByItemId (id);
        ItemModel itemModel = generateModelFromDataObject(item,stock);
        /*注入商品活动信息*/
        PromoModel promoModel = promoService.getPromoById (id);
        if(promoModel==null) return itemModel;
        if(promoModel.getStatus ()!=3){
            itemModel.setPromoModel (promoModel);
        }
        return itemModel;
    }


    @Override
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int affectedRow = itemStockMapper.decreaseStock (itemId,amount);
        if(affectedRow<=0) return false;
        else return true;
    }

    @Override
    public void increaseSales(Integer itemId, Integer amount) {
        itemMapper.increaseSales (itemId,amount);
    }
}
