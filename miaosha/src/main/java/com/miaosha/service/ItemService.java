package com.miaosha.service;

import com.miaosha.error.BusinessException;
import com.miaosha.service.model.ItemModel;

import java.util.List;

/**
 * @date: 2019\11\29 0029
 * @author: zhaijh
 * @description:
 */
public interface ItemService {
    //创建商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;
    //商品列表浏览
    List<ItemModel> listItem();
    //商品详情浏览
    ItemModel getItemById(Integer id);
    //修改库存数量
    boolean decreaseItemStock(Integer itemId,Integer amount);

    void increaseItemSales(Integer itemId,Integer amount);
}
