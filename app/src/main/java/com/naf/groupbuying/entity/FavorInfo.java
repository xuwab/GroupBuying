package com.naf.groupbuying.entity;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by naf on 2016/12/4.
 */

public class FavorInfo extends BmobObject{
    private String ivImage;
    private String title;
    private String shortTitle;
    private String price;
    private String value;
    private String goodId;

    private BmobRelation favor;

    public BmobRelation getFavor() {
        return favor;
    }

    public void setFavor(BmobRelation favor) {
        this.favor = favor;
    }

    public String getIvImage() {
        return ivImage;
    }

    public void setIvImage(String ivImage) {
        this.ivImage = ivImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGoodId() {
        return goodId;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }
}
