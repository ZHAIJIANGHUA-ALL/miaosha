package com.miaosha.service;

import com.miaosha.error.BusinessException;
import com.miaosha.service.model.OrderModel;

/**
 * @date: 2019\12\3 0003
 * @author: zhaijh
 * @description: 订单
 */
public interface OrderService {
    //通过前端url上传过来秒杀活动id,然后下单接口内校验对应id是否对应商品活动且活动开始
    //直接在下单接口中判断对应商品是否存在秒杀活动,如果存在进行中的则以秒杀价格下单
    OrderModel createOrderModel(Integer userId,Integer itemId,Integer amount,Integer promoId) throws BusinessException;
}
