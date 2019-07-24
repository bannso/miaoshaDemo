package com.xw.miaosha.demo.service.impl;

import com.xw.miaosha.demo.dao.OrderInfoMapper;
import com.xw.miaosha.demo.dao.SequenceInfoMapper;
import com.xw.miaosha.demo.error.BusinessException;
import com.xw.miaosha.demo.error.EmBusinessError;
import com.xw.miaosha.demo.model.OrderInfo;
import com.xw.miaosha.demo.model.SequenceInfo;
import com.xw.miaosha.demo.service.ItemService;
import com.xw.miaosha.demo.service.OrderService;
import com.xw.miaosha.demo.service.UserService;
import com.xw.miaosha.demo.service.model.ItemModel;
import com.xw.miaosha.demo.service.model.OrderModel;
import com.xw.miaosha.demo.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    ItemService itemService;
    @Autowired
    UserService userService;
    @Autowired(required = false)
    SequenceInfoMapper sequenceInfoMapper;
    @Autowired(required = false)
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private OtherServiceImpl otherService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {BusinessException.class})
    public OrderModel createOrder(Integer userId, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        ItemModel itemModel = itemService.getItemById (itemId);
        if(itemModel==null) throw new BusinessException (EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品信息不存在");
        UserModel userModel = userService.getUserById (userId);
        if (userModel==null) throw new BusinessException (EmBusinessError.VERIFICATION_OTPCODE_ERROR,"用户信息不存在");
        if(amount <=0||amount>99) throw new BusinessException (EmBusinessError.PARAMETER_VALIDATION_ERROR,"商品数量不符合要求");
        /*活动信息*/
        if(promoId!=null){
            System.out.println (promoId.intValue ());
            if(itemModel.getPromoModel ()==null||promoId.intValue ()!=itemModel.getPromoModel ().getId ()){
                throw new BusinessException (EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动信息不存在");
            }else if(itemModel.getPromoModel ().getStatus ()==1){
                throw new BusinessException (EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动尚未开始");
            }else if(itemModel.getPromoModel ().getStatus ()==3){
                throw new BusinessException (EmBusinessError.PARAMETER_VALIDATION_ERROR,"活动已结束");
            }
        }

        /*下单后锁定相应库存*/
        boolean result = itemService.decreaseStock (itemId,amount);
        if (!result) throw new BusinessException (EmBusinessError.STOCK_NOT_ENOUGH);
        /*创建订单*/
        OrderModel orderModel = new OrderModel ();
        orderModel.setAmount (amount);
        orderModel.setItemId (itemId);
        orderModel.setUserId (userId);
        if(promoId!=null){
            orderModel.setItemPrice (itemModel.getPromoModel ().getPromoItemPrice ());
        }else {
            orderModel.setItemPrice (itemModel.getPrice ());
        }

        orderModel.setItemPrice (itemModel.getPrice ());
        orderModel.setOrderPrice (orderModel.getItemPrice ().multiply (BigDecimal.valueOf (amount)));

        /*创建订单号*/
        orderModel.setId (otherService.generateOrderNo());
        OrderInfo orderInfo = generateOrderInforFromOrderModel(orderModel);
        orderInfoMapper.insertSelective (orderInfo);
        itemService.increaseSales (itemId,amount);

        return orderModel;
    }

    private OrderInfo generateOrderInforFromOrderModel(OrderModel orderModel) {
        if(orderModel==null) return null;
        OrderInfo orderInfo = new OrderInfo ();
        BeanUtils.copyProperties (orderModel,orderInfo);
        return orderInfo;
    }

    /*需要测试！ 同一类中注解是否可以生效*/
    /*
    * 结果：
    * 同一类中，A方法调用B方法，B方法的@Transactional注解不会生效，
    * 另外，方法访问权限修饰符必须是public；
    * 默认情况下，只有RuntimeException（运行时异常）及其子类才会回滚，Exception（受检异常）如果进行回滚，需要添加rollbackFor
    *
    * */
//    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {BusinessException.class})
//    public String generateOrderNo() {
//        /*生成16位订单编号，8位时间信息+6位自增序列+分库分表信息*/
//        /*8位时间信息*/
//        StringBuilder sb = new StringBuilder ();
//        LocalDateTime now = LocalDateTime.now ();
//        String nowTime = now.format (DateTimeFormatter.ISO_DATE).replace ("-","");
//        sb.append (nowTime);
//        /*6位自增序列*/
//        int sequence = 0;
//        SequenceInfo sequenceInfo = sequenceInfoMapper.selectByPrimaryKey ("order_info");
//        sequence = sequenceInfo.getCurrentValue ();
//        sequenceInfo.setCurrentValue (sequenceInfo.getStep ()+sequenceInfo.getCurrentValue ());
//        sequenceInfoMapper.updateByPrimaryKeySelective (sequenceInfo);
//        /*补足6位 暂不考虑溢出6位的情况*/
//        for (int i = 0; i < 6 - String.valueOf (sequence).length (); i++) {
//            sb.append (0);
//        }
//        sb.append (sequence);
//        /*分库分表暂不考虑*/
//        sb.append ("00");
//        return sb.toString ();
//    }
}
