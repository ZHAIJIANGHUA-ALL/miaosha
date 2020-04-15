package com.miaosha.service;

import com.miaosha.service.model.PromoModel;

/**
 * @date: 2019\12\4 0004
 * @author: zhaijh
 * @description:
 */
public interface PromoService {
    //根据itemId获取即将进行或正在进行的秒杀活动
    PromoModel getPromoByItemId(Integer itemId);
}
