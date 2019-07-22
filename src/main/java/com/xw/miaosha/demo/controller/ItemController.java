package com.xw.miaosha.demo.controller;


import com.xw.miaosha.demo.error.BusinessException;
import com.xw.miaosha.demo.model.Item;
import com.xw.miaosha.demo.response.CommonReturnType;
import com.xw.miaosha.demo.service.ItemService;
import com.xw.miaosha.demo.service.model.ItemModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "/item")
public class ItemController extends BaseController{
    @Autowired
    ItemService itemService;
    /*添加新商品*/
    @RequestMapping(value = "/create",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType createItem(@RequestParam(name = "title")String title,
                                       @RequestParam(name = "price")BigDecimal price,
                                       @RequestParam(name = "stock")Integer stock,
                                       @RequestParam(name = "imgUrl")String imgUrl,
                                       @RequestParam(name = "description")String description) throws BusinessException {
        ItemModel model = new ItemModel ();
        model.setTitle (title);
        model.setPrice (price);
        model.setDescription (description);
        model.setImgUrl (imgUrl);
        model.setStock (stock);
        ItemModel itemModel = itemService.createItem (model);
        return CommonReturnType.create (itemModel);
    }
    /*商品详情*/
    @RequestMapping(value = "/get",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType getItem(@RequestParam(name = "id")Integer id){
        ItemModel model = itemService.getItemById (id);
        return CommonReturnType.create (model);
    }
    /*商品列表*/
    @RequestMapping(value = "/list",method = {RequestMethod.GET})
    public CommonReturnType getItemList(){
        List<ItemModel> list = itemService.listItem ();
        return CommonReturnType.create (list);
    }
}
