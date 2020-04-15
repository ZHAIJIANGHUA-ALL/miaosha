package com.miaosha.response;

/**
 * @date: 2019\11\25 0025
 * @author: zhaijh
 * @description:
 */
public class CommonReturnType {
    private String status;
    private Object data;

    public static CommonReturnType create(Object data){
        return CommonReturnType.create(data,"success");
    }

    public static CommonReturnType create(Object data,String status){
        CommonReturnType type=new CommonReturnType();
        type.setData(data);
        type.setStatus(status);
        return type;
    }

    public CommonReturnType() {
    }

    public CommonReturnType(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
