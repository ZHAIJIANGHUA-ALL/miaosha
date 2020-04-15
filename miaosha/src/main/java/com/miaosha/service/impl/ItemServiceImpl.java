package com.miaosha.service.impl;

import com.miaosha.dao.ItemDOMapper;
import com.miaosha.dao.ItemStockDOMapper;
import com.miaosha.entity.ItemDO;
import com.miaosha.entity.ItemStockDO;
import com.miaosha.error.BusinessException;
import com.miaosha.error.EmBusinessError;
import com.miaosha.service.ItemService;
import com.miaosha.service.PromoService;
import com.miaosha.service.model.ItemModel;
import com.miaosha.service.model.PromoModel;
import com.miaosha.validator.ValidationResult;
import com.miaosha.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date: 2019\12\2 0002
 * @author: zhaijh
 * @description: 商品详情页
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDOMapper itemDOMapper;
    @Autowired
    private ItemStockDOMapper itemStockDOMapper;
    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private PromoService promoService;

    @Override
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        ValidationResult validate = validator.validate(itemModel);
        if (validate.isHasError()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, validate.getErrMsg());
        }
        ItemDO itemDO = convertModelFromDataObject(itemModel);
        itemDOMapper.insertSelective(itemDO);
        ItemStockDO itemStockDO = new ItemStockDO();
        if (itemDO != null) {
            itemStockDO.setItemId(itemDO.getId());
        }
        itemStockDO.setStock(itemModel.getStock());
        itemStockDOMapper.insert(itemStockDO);
        return itemModel;
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOS = itemDOMapper.itemList();
        if (itemDOS == null) {
            return null;
        }
        List<ItemModel> itemModels = itemDOS.stream().map(itemDO -> {
            ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
            ItemModel itemModel = this.convertModelFromDataObject(itemDO, itemStockDO);
            return itemModel;
        }).collect(Collectors.toList());

        return itemModels;
    }

    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if (itemDO == null) {
            return null;
        }
        //操作获得库存数量
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
        //将dataObject->model
        ItemModel itemModel = convertModelFromDataObject(itemDO, itemStockDO);
        //获取活动商品信息
        assert itemModel != null;
        PromoModel promoModel = promoService.getPromoByItemId(itemModel.getId());
        if (promoModel != null && promoModel.getStatus() != 3) {
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    @Override
    public boolean decreaseItemStock(Integer itemId, Integer amount) {
        int resultLine = itemStockDOMapper.decreaseItemStock(itemId, amount);
        if (resultLine > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public void increaseItemSales(Integer itemId, Integer amount) {
        itemDOMapper.increaseItemSales(itemId, amount);
    }

    private ItemDO convertModelFromDataObject(ItemModel itemModel) {
        ItemDO itemDO = new ItemDO();
        if (itemModel == null) {
            return null;
        }
        BeanUtils.copyProperties(itemModel, itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }

    private ItemModel convertModelFromDataObject(ItemDO itemDO, ItemStockDO itemStockDO) {
        ItemModel itemModel = new ItemModel();
        if (itemStockDO == null) {
            return null;
        }
        BeanUtils.copyProperties(itemDO, itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(itemStockDO.getStock());
        return itemModel;
    }
}
