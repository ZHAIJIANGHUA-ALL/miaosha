package com.miaosha.controller;

import com.miaosha.controller.viewObject.ItemVO;
import com.miaosha.error.BusinessException;
import com.miaosha.response.CommonReturnType;
import com.miaosha.service.ItemService;
import com.miaosha.service.model.ItemModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @date: 2019\12\2 0002
 * @author: zhaijh
 * @description:
 */
@RestController
@RequestMapping("/item")
@CrossOrigin(allowedHeaders="*",allowCredentials="true")
public class ItemController extends BaseController{

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/createItem", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItemModel(@RequestParam(name="title")String title,
                                            @RequestParam(name="price") BigDecimal price,
                                            @RequestParam(name="stock")Integer stock,
                                            @RequestParam(name="description")String description,
                                            @RequestParam(name="imgUrl")String imgUrl) throws BusinessException {
        ItemModel itemModel=new ItemModel();
        itemModel.setTitle(title);
        itemModel.setStock(stock);
        itemModel.setPrice(price);
        itemModel.setDescription(description);
        itemModel.setImgurl(imgUrl);
        ItemModel item = itemService.createItem(itemModel);
        if(item!=null){
            return CommonReturnType.create(null);
        }
        return CommonReturnType.create("创建商品失败","fail");
    }

    @RequestMapping(value = "/findItemById", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType findItemById(@RequestParam(name="id")Integer id){
        ItemModel itemModel = itemService.getItemById(id);
        ItemVO itemVO = convertFromDataObject(itemModel);
        if(itemModel.getPromoModel()!=null){
            assert itemVO != null;
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        }
        return CommonReturnType.create(itemVO);
    }

    @RequestMapping(value = "/itemList", method = {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType findItenList(){
        List<ItemModel> itemModels = itemService.listItem();
        if(itemModels==null){
            return CommonReturnType.create(null);
        }
        List<ItemVO> itemVOS = itemModels.stream().map(this::convertFromDataObject).collect(Collectors.toList());
        return CommonReturnType.create(itemVOS);
    }
    private ItemVO convertFromDataObject(ItemModel itemModel){
        ItemVO itemVO=new ItemVO();
        if(itemModel==null){
            return null;
        }
        BeanUtils.copyProperties(itemModel,itemVO);
        return itemVO;
    }
}
