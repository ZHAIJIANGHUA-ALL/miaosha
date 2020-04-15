package com.miaosha.controller;

import com.miaosha.error.BusinessException;
import com.miaosha.error.EmBusinessError;
import com.miaosha.response.CommonReturnType;
import com.miaosha.service.OrderService;
import com.miaosha.service.model.OrderModel;
import com.miaosha.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @date: 2019\12\3 0003
 * @author: zhaijh
 * @description:
 */
@RestController
@RequestMapping("/order")
@CrossOrigin(allowedHeaders="*",allowCredentials="true")
public class OrderController extends BaseController{
    @Autowired
    private OrderService orderService;
    @Autowired
    private HttpServletRequest servletRequest;

    @RequestMapping(value = "/createOrder", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam("itemId") Integer itemId,@RequestParam("amount")Integer amount,@RequestParam(value = "promoId",required = false)Integer promoId) throws BusinessException {
        if(amount==null){
            amount=1;
        }
        Boolean isLogin= (Boolean) servletRequest.getSession().getAttribute("IS_LOGIN");
        if(isLogin==null||!isLogin){
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }
        UserModel userModel= (UserModel) servletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel orderModel = orderService.createOrderModel(userModel.getId(), itemId, amount,promoId);
        return CommonReturnType.create(orderModel);
    }
}
