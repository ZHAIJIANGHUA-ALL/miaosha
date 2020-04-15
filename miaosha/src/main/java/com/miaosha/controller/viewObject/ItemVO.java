package com.miaosha.controller.viewObject;

import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @date: 2019\12\2 0002
 * @author: zhaijh
 * @description:
 */
public class ItemVO {
    private Integer id;
    //商品名称
    private String title;
    //商品价格
    private BigDecimal price;
    //商品库存
    private Integer stock;
    //商品描述
    private String description;
    //商品销量
    private Integer sales;
    //商品描述图片的url
    private String imgurl;
    //记录商品是否在秒杀活动中,以及对应的的状态0:表示没有秒杀活动,1:表示秒杀活动待开始,2:表示秒杀活动进行中
    private Integer promoStatus;
    //秒杀活动的价格
    private BigDecimal promoPrice;
    //秒杀活动价格
    private Integer promoId;
    //秒杀活动开始时间
    private String startDate;

    public Integer getPromoStatus() {
        return promoStatus;
    }

    public void setPromoStatus(Integer promoStatus) {
        this.promoStatus = promoStatus;
    }

    public BigDecimal getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(BigDecimal promoPrice) {
        this.promoPrice = promoPrice;
    }

    public Integer getPromoId() {
        return promoId;
    }

    public void setPromoId(Integer promoId) {
        this.promoId = promoId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public ItemVO() {
    }

    public ItemVO(Integer id, String title, BigDecimal price, Integer stock, String description, Integer sales, String imgurl) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.sales = sales;
        this.imgurl = imgurl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
