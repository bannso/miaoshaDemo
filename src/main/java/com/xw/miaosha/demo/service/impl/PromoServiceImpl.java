package com.xw.miaosha.demo.service.impl;

import com.xw.miaosha.demo.dao.PromoMapper;
import com.xw.miaosha.demo.model.Promo;
import com.xw.miaosha.demo.service.PromoService;
import com.xw.miaosha.demo.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    PromoMapper promoMapper;

    @Override
    public PromoModel getPromoById(Integer id) {
        Promo promo = promoMapper.selectByItemId (id);
        PromoModel promoModel = generatePromoModelFromPromo(promo);
        if (promoModel==null) return null;
        DateTime now = new DateTime ();
        if(promoModel.getStartDate ().isAfter (now)){
            promoModel.setStatus (1);
        }else if(promoModel.getEndDate ().isBefore (now)){
            promoModel.setStatus (3);
        }else {
            promoModel.setStatus (2);
        }
        return promoModel;
    }

    private PromoModel generatePromoModelFromPromo(Promo promo) {
        if(promo==null) return null;
        PromoModel promoModel = new PromoModel ();
        BeanUtils.copyProperties (promo,promoModel);
        promoModel.setStartDate (new DateTime (promo.getStartDate ()));
        promoModel.setEndDate (new DateTime (promo.getEndDate ()));
        return promoModel;
    }
}
